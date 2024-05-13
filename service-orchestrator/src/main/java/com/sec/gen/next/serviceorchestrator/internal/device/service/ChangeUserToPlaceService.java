package com.sec.gen.next.serviceorchestrator.internal.device.service;

import com.next.gen.api.AssignmentRole;
import com.next.gen.api.Places;
import com.next.gen.api.UserPlaceAssignment;
import com.next.gen.sec.model.ModifyUserToPlaceModel;
import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.UpdateService;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.PlacesMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_PLACE_DATA;

@RequiredArgsConstructor
public class ChangeUserToPlaceService implements UpdateService<ModifyUserToPlaceModel, PlacesModel> {

    private final PlacesRepository placesRepository;
    private final PlacesMapper placesMapper;

    @Override
    public PlacesModel update(ModifyUserToPlaceModel modifyUserToPlaceModel) {
        return placesRepository.findByPlaceName(modifyUserToPlaceModel.getPlaceName()).stream()
                .peek(place -> changeUser(modifyUserToPlaceModel, place))
                .map(placesRepository::save)
                .map(placesMapper::map)
                .findFirst()
                .orElseThrow(INVALID_PLACE_DATA::getError);
    }

    private void changeUser(ModifyUserToPlaceModel modifyUserToPlaceModel, Places places) {
        places.getAuthorizedUsers().stream()
                .filter(user -> user.getUser().getEmail().equals(modifyUserToPlaceModel.getUserPlaceAssignment().getUser().getEmail()))
                .forEach(user -> update(user, modifyUserToPlaceModel));
    }

    private void update(UserPlaceAssignment user, ModifyUserToPlaceModel modifyUserToPlaceModel) {
        Optional.ofNullable(modifyUserToPlaceModel.getUserPlaceAssignment().getAssignmentRole())
                .map(AssignmentRole::valueOf)
                .ifPresent(user::setAssignmentRole);
    }
}
