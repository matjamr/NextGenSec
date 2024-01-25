package com.sec.gen.next.backend.internal.places.builder.put;

import com.sec.gen.next.backend.internal.api.exception.ServiceException;
import com.sec.gen.next.backend.internal.api.external.PlacesModel;
import com.sec.gen.next.backend.internal.api.external.UserPlaceAssignmentModel;
import com.sec.gen.next.backend.internal.api.internal.Places;
import com.sec.gen.next.backend.internal.api.internal.UserPlaceAssignment;
import com.sec.gen.next.backend.internal.api.internal.VerificationStage;
import com.sec.gen.next.backend.internal.user.repository.UserPlaceAssignmentRepository;
import com.sec.gen.next.backend.internal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

import static com.sec.gen.next.backend.internal.api.exception.Error.NO_USER_ID;

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
                .verificationStage(Optional.ofNullable(userAdd.verificationStage()).orElse(VerificationStage.APPLIED))
                .build();
    }
}
