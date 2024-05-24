package com.sec.gen.next.serviceorchestrator.internal.device.service;

import com.next.gen.api.AssignmentRole;
import com.next.gen.api.Places;
import com.next.gen.api.UserPlaceAssignment;
import com.next.gen.sec.model.KafkaChatServiceModel;
import com.next.gen.sec.model.ModifyUserToPlaceModel;
import com.next.gen.sec.model.PlacesModel;
import com.next.gen.sec.model.Topic;
import com.sec.gen.next.serviceorchestrator.common.templates.UpdateService;
import com.sec.gen.next.serviceorchestrator.external.kafka.KafkaProducer;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.PlacesMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.UserPlaceAssignmentMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_PLACE_DATA;

@RequiredArgsConstructor
public class AddUserToPlaceService implements UpdateService<ModifyUserToPlaceModel, PlacesModel> {

    private final PlacesRepository placesRepository;
    private final PlacesMapper placesMapper;
    private final UserPlaceAssignmentMapper userPlaceAssignmentMapper;
    private final KafkaProducer<KafkaChatServiceModel> kafkaChatServiceProducer;

    @Override
    public PlacesModel update(ModifyUserToPlaceModel modifyUserToPlaceModel) {
        return placesRepository.findByPlaceName(modifyUserToPlaceModel.getPlaceName()).stream()
                .peek(place -> addUser(modifyUserToPlaceModel, place))
                .map(placesRepository::save)
                .peek(places -> sendNotifications(places, modifyUserToPlaceModel))
                .map(placesMapper::map)
                .findFirst()
                .orElseThrow(INVALID_PLACE_DATA::getError);
    }

    private void sendNotifications(Places places, ModifyUserToPlaceModel modifyUserToPlaceModel) {
        kafkaChatServiceProducer.sendMessage(new KafkaChatServiceModel()
                .commonRecipients(List.of(modifyUserToPlaceModel.getUserPlaceAssignment().getUser().getEmail()))
                .message("You have been added to place " + places.getPlaceName())
                .topic(Topic.NOTIFICATION));
    }

    private void addUser(ModifyUserToPlaceModel modifyUserToPlaceModel, Places places) {
        places.getAuthorizedUsers().add(userPlaceAssignmentMapper.map(modifyUserToPlaceModel.getUserPlaceAssignment()));
    }
}
