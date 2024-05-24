package com.sec.gen.next.serviceorchestrator.internal.device.service;

import com.next.gen.api.Places;
import com.next.gen.sec.model.KafkaChatServiceModel;
import com.next.gen.sec.model.ModifyUserToPlaceModel;
import com.next.gen.sec.model.PlacesModel;
import com.next.gen.sec.model.Topic;
import com.sec.gen.next.serviceorchestrator.common.templates.UpdateService;
import com.sec.gen.next.serviceorchestrator.external.kafka.KafkaProducer;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.PlacesMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_PLACE_DATA;

@RequiredArgsConstructor
public class RemoveUserFromPlaceService implements UpdateService<ModifyUserToPlaceModel, PlacesModel> {

    private final PlacesRepository placesRepository;
    private final PlacesMapper placesMapper;
    private final KafkaProducer<KafkaChatServiceModel> kafkaChatServiceProducer;

    @Override
    public PlacesModel update(ModifyUserToPlaceModel modifyUserToPlaceModel) {
        return placesRepository.findByPlaceName(modifyUserToPlaceModel.getPlaceName()).stream()
                .peek(place -> removeUser(modifyUserToPlaceModel, place))
                .map(placesRepository::save)
                .peek(places -> sendNotifications(places, modifyUserToPlaceModel))
                .map(placesMapper::map)
                .findFirst()
                .orElseThrow(INVALID_PLACE_DATA::getError);
    }

    private void sendNotifications(Places places, ModifyUserToPlaceModel modifyUserToPlaceModel) {
        kafkaChatServiceProducer.sendMessage(new KafkaChatServiceModel()
                .commonRecipients(List.of(modifyUserToPlaceModel.getUserPlaceAssignment().getUser().getEmail()))
                .message("You have been removed from place " + places.getPlaceName())
                .topic(Topic.NOTIFICATION));
    }

    private void removeUser(ModifyUserToPlaceModel modifyUserToPlaceModel, Places places) {
        places.getAuthorizedUsers().removeAll(places.getAuthorizedUsers()
                .stream()
                .filter(user -> user.getUser().getEmail().equals(modifyUserToPlaceModel.getUserPlaceAssignment().getUser().getEmail()))
                .toList());
    }
}
