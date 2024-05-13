package com.sec.gen.next.serviceorchestrator.internal.device.service;

import com.next.gen.api.AssignmentRole;
import com.next.gen.api.Places;
import com.next.gen.api.UserPlaceAssignment;
import com.next.gen.sec.model.ModifyUserToPlaceModel;
import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.UpdateService;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.PlacesMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.UserPlaceAssignmentMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_PLACE_DATA;

@RequiredArgsConstructor
public class AddUserToPlaceService implements UpdateService<ModifyUserToPlaceModel, PlacesModel> {

    private final PlacesRepository placesRepository;
    private final PlacesMapper placesMapper;
    private final UserPlaceAssignmentMapper userPlaceAssignmentMapper;

    @Override
    public PlacesModel update(ModifyUserToPlaceModel modifyUserToPlaceModel) {
        return placesRepository.findByPlaceName(modifyUserToPlaceModel.getPlaceName()).stream()
                .peek(place -> addUser(modifyUserToPlaceModel, place))
                .map(placesRepository::save)
                .map(placesMapper::map)
                .findFirst()
                .orElseThrow(INVALID_PLACE_DATA::getError);
    }

    private void addUser(ModifyUserToPlaceModel modifyUserToPlaceModel, Places places) {
        places.getAuthorizedUsers().add(userPlaceAssignmentMapper.map(modifyUserToPlaceModel.getUserPlaceAssignment()));
    }
}
