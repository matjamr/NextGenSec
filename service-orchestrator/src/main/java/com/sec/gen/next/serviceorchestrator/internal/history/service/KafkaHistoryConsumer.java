package com.sec.gen.next.serviceorchestrator.internal.history.service;

import com.next.gen.api.AssignmentRole;
import com.next.gen.api.security.UserServiceClient;
import com.next.gen.sec.model.*;
import com.sec.gen.next.serviceorchestrator.common.templates.SaveService;
import com.sec.gen.next.serviceorchestrator.common.templates.SimpleQueryService;
import com.sec.gen.next.serviceorchestrator.external.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class KafkaHistoryConsumer implements Consumer<KafkaNotifModel> {

    private final SimpleQueryService<String, PlacesModel> simpleQueryService;
    private final SimpleQueryService<String, DeviceModel> deviceQueryService;
    private final UserServiceClient userServiceClient;
    private final SaveService<HistoryEntranceModel, HistoryEntranceModel> historySaveService;
    private final KafkaProducer<KafkaAsyncHistoryNotif> kafkaAsyncHistoryNotifKafkaProducer;

    @Override
    public void accept(KafkaNotifModel kafkaNotifModel) {
        PlacesModel places = simpleQueryService.findBy(kafkaNotifModel.getPlaceId());
        ProductModel product = deviceQueryService.findBy(kafkaNotifModel.getDeviceId()).getProduct();
        UserModel user = userServiceClient.getUserByEmail(kafkaNotifModel.getEmail());


        HistoryEntranceModel historyEntrance = new HistoryEntranceModel()
                .date(OffsetDateTime.now())
                .user(user)
                .places(places)
                .product(product);

        historySaveService.save(historyEntrance);

        kafkaAsyncHistoryNotifKafkaProducer.sendMessage(new KafkaAsyncHistoryNotif()
                .user(user)
                .adminsEmails(getAdmins(places))
                .product(product));
    }

    private List<String> getAdmins(PlacesModel places) {
        return places.getAuthorizedUsers()
                .stream()
                .filter(authorizedUser -> authorizedUser.getAssignmentRole().equals(AssignmentRole.ADMIN.name()) )
                .map(UserPlaceAssignment::getUser)
                .map(UserModel::getEmail)
                .toList();
    }
}
