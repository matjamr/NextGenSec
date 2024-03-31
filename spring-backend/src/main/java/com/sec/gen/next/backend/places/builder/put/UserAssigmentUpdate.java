package com.sec.gen.next.backend.places.builder.put;

import com.sec.gen.next.backend.api.external.NotificationModel;
import com.sec.gen.next.backend.api.external.PlacesModel;
import com.sec.gen.next.backend.api.external.UserModel;
import com.sec.gen.next.backend.api.external.UserPlaceAssignmentModel;
import com.next.gen.api.Notification;
import com.next.gen.api.Places;
import com.sec.gen.next.backend.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class UserAssigmentUpdate implements BiConsumer<Places, PlacesModel> {

    private final NotificationService notificationService;

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
                        .forEach(userPlaceAssignment ->  {
                            userPlaceAssignment.setAssignmentRole(userModify.assignmentRole());
                            notificationService.sendAllNotifications(Collections.singletonList(
                                    NotificationModel.builder()
                                            .user(UserModel.builder().email(userPlaceAssignment.getUser().getEmail()).build())
                                            .title("Your role has been updated")
                                            .content("Your role in place " + places.getPlaceName() + " has been updated to " + userModify.assignmentRole()).build()));
                        })
                );
    }

}
