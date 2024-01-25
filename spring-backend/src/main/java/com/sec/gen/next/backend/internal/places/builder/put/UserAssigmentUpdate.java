package com.sec.gen.next.backend.internal.places.builder.put;

import com.sec.gen.next.backend.internal.api.external.PlacesModel;
import com.sec.gen.next.backend.internal.api.external.UserPlaceAssignmentModel;
import com.sec.gen.next.backend.internal.api.internal.Places;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

import static java.util.Objects.nonNull;

public class UserAssigmentUpdate implements BiConsumer<Places, PlacesModel> {

    @Override
    public void accept(Places places, PlacesModel placesModel) {
        Optional.ofNullable(placesModel.getAuthorizedUsers())
                .orElse(Collections.emptyList())
                .stream()
                .map(UserPlaceAssignmentModel::getUserModify)
                .filter(Objects::nonNull)
                .forEach(userModify -> places.getAuthorizedUsers().stream()
                        .filter(userPlaceAssignment -> nonNull(userPlaceAssignment.getUser()))
                        .filter(userPlaceAssignment -> userPlaceAssignment.getUser().getId().equals(userModify.user().getId()))
                        .forEach(userPlaceAssignment -> userPlaceAssignment.setAssignmentRole(userModify.assignmentRole()))
                );
    }

}
