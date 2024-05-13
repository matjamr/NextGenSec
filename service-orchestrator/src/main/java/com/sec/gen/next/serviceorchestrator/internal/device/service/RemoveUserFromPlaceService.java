package com.sec.gen.next.serviceorchestrator.internal.device.service;

import com.next.gen.api.Places;
import com.next.gen.sec.model.ModifyUserToPlaceModel;
import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.UpdateService;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.PlacesMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_PLACE_DATA;

@RequiredArgsConstructor
public class RemoveUserFromPlaceService implements UpdateService<ModifyUserToPlaceModel, PlacesModel> {

    private final PlacesRepository placesRepository;
    private final PlacesMapper placesMapper;

    @Override
    public PlacesModel update(ModifyUserToPlaceModel modifyUserToPlaceModel) {
        return placesRepository.findByPlaceName(modifyUserToPlaceModel.getPlaceName()).stream()
                .peek(place -> removeUser(modifyUserToPlaceModel, place))
                .map(placesRepository::save)
                .map(placesMapper::map)
                .findFirst()
                .orElseThrow(INVALID_PLACE_DATA::getError);
    }

    private void removeUser(ModifyUserToPlaceModel modifyUserToPlaceModel, Places places) {
        places.getAuthorizedUsers().removeAll(places.getAuthorizedUsers()
                .stream()
                .filter(user -> user.getUser().getEmail().equals(modifyUserToPlaceModel.getUserPlaceAssignment().getUser().getEmail()))
                .toList());
    }
}
