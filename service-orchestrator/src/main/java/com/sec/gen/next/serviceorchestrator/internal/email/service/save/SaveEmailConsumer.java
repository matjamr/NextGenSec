package com.sec.gen.next.serviceorchestrator.internal.email.service.save;

import com.next.gen.api.Email;
import com.next.gen.api.Places;
import com.next.gen.api.User;
import com.next.gen.api.UserPlaceAssignment;
import com.next.gen.sec.model.MailModel;
import com.next.gen.sec.model.Role;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.EmailRepository;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.UserRepository;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

@Component
@RequiredArgsConstructor
public class SaveEmailConsumer implements BiConsumer<Email, MailModel> {

    private final UserRepository userRepository;
    private final PlacesRepository placesRepository;
    private final EmailRepository emailRepository;

    @Override
    public void accept(Email email, MailModel mailModel) {
        mailModel.getTo()
                .stream()
                .map(this::findSendToEntity)
                .flatMap(Collection::stream)
                .forEach(user -> emailRepository.save(email
                        .setDate(OffsetDateTime.now())
                        .setToUser(user)));
    }

    private List<User> findSendToEntity(String sendToName) {
        if (Objects.equals(sendToName, "SYSTEM")) {
            return userRepository.findAllByRole(Role.SYSTEM);
        }

        return userRepository.findByEmail(sendToName)
                .map(List::of)
                .filter(list -> list.size() > 0)
                .orElse(placesRepository.findByPlaceName(sendToName)
                        .map(Places::getAuthorizedUsers)
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(UserPlaceAssignment::getUser)
                        .filter(user -> user.getRole().equals(Role.ADMIN))
                        .toList());
    }
}
