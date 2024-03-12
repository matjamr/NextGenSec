package com.sec.gen.next.backend.places.builder.put;

import com.sec.gen.next.backend.api.external.PlacesModel;
import com.sec.gen.next.backend.api.external.UserPlaceAssignmentModel;
import com.sec.gen.next.backend.api.internal.Places;
import com.sec.gen.next.backend.user.repository.UserPlaceAssignmentRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.function.BiConsumer;

@RequiredArgsConstructor
public class UserAssigmentDelete implements BiConsumer<Places, PlacesModel> {

    private final UserPlaceAssignmentRepository userPlaceAssignmentRepository;

    @Override
    public void accept(Places places, PlacesModel placesModel) {
        placesModel.getAuthorizedUsers()
                .stream()
                .map(UserPlaceAssignmentModel::getUserDelete)
                .filter(Objects::nonNull)
                .forEach(userDelete -> {
                    userPlaceAssignmentRepository.findAll().stream()
                            .filter(userPlaceAssignment -> userPlaceAssignment.getUser().getId().equals(userDelete.id()))
                            .findFirst()
                            .ifPresent(userPlace -> {
                                userPlaceAssignmentRepository.delete(userPlace);
                                places.getAuthorizedUsers().remove(userPlace);
                            });
                });
    }
}
