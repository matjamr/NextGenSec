package com.sec.gen.next.serviceorchestrator.places.service;

import com.next.gen.sec.model.UserModel;
import com.next.gen.sec.model.PlacesModel;
import com.sec.gen.next.serviceorchestrator.common.templates.SaveService;
import com.sec.gen.next.serviceorchestrator.common.templates.SimpleQueryService;
import com.sec.gen.next.serviceorchestrator.common.templates.UpdateService;
import com.sec.gen.next.serviceorchestrator.external.user.UserServiceClient;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.sec.gen.next.serviceorchestrator.exception.Error.USERS_ALREADY_ADDED;

@RequiredArgsConstructor
public class AddUserPlaceAssignmentService implements UpdateService<PlacesModel, PlacesModel> {

    private final UserServiceClient userServiceClient;
    private final SimpleQueryService<String, PlacesModel> simpleQueryPlacesService;
    private final SaveService<PlacesModel, PlacesModel> placesSaveService;

    @Override
    public PlacesModel update(PlacesModel placesModel) {

        List<UserModel> usersToBeAdded = placesModel.getAuthorizedUsers()
                .stream()
                .map(user -> userServiceClient.getUserByEmail(user.getUser().getEmail()))
                .toList();

        PlacesModel placesFromDb = simpleQueryPlacesService.findBy(placesModel.getId());

        String result = usersToBeAdded
                .stream()
                .map(UserModel::getEmail)
                .filter(email -> placesFromDb.getAuthorizedUsers().stream()
                        .anyMatch(existingUser -> existingUser.getUser().getEmail().equals(email)))
                .collect(Collectors.joining(" && "));

        if(!result.isBlank()) {
            throw USERS_ALREADY_ADDED.getFormattedError(result);
        }

        placesFromDb.getAuthorizedUsers().addAll(placesModel.getAuthorizedUsers());

        return placesSaveService.save(placesFromDb);
    }
}
