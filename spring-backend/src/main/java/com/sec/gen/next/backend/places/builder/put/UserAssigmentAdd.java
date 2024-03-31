package com.sec.gen.next.backend.places.builder.put;

import com.sec.gen.next.backend.api.exception.ServiceException;
import com.sec.gen.next.backend.api.external.PlacesModel;
import com.sec.gen.next.backend.api.external.UserPlaceAssignmentModel;
import com.next.gen.api.Places;
import com.next.gen.api.UserPlaceAssignment;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

import static com.sec.gen.next.backend.api.exception.Error.NO_USER_ID;

@RequiredArgsConstructor
public class UserAssigmentAdd implements BiConsumer<Places, PlacesModel> {

    private final UserRepository userRepository;
    private final UserPlaceAssignmentRepository userPlaceAssignmentRepository;

    @Override
    public void accept(Places places, PlacesModel placesModel) {
        Optional.ofNullable(placesModel.getAuthorizedUsers())
                .orElse(Collections.emptyList())
                .stream()
                .map(UserPlaceAssignmentModel::getUserAdd)
                .filter(Objects::nonNull)
                .forEach(userAdd -> {
                    var a = buildUserPlaceAssigment(userAdd);
                    userPlaceAssignmentRepository.save(a);
                    places.getAuthorizedUsers().add(a);
                });
    }

    private UserPlaceAssignment buildUserPlaceAssigment(UserPlaceAssignmentModel.UserAdd userAdd) {
        return UserPlaceAssignment.builder()
                .user(userRepository.findUserByEmail(userAdd.user().getEmail())
                        .orElseThrow(() -> new ServiceException(NO_USER_ID)))
                .assignmentRole(userAdd.assignmentRole())
                .build();
    }
}
