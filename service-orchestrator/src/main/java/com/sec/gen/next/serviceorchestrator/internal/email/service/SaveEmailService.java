package com.sec.gen.next.serviceorchestrator.internal.email.service;

import com.next.gen.api.Email;
import com.next.gen.api.Places;
import com.next.gen.api.User;
import com.next.gen.api.UserPlaceAssignment;
import com.next.gen.sec.model.MailModel;
import com.next.gen.sec.model.Role;
import com.sec.gen.next.serviceorchestrator.api.CustomAuthentication;
import com.sec.gen.next.serviceorchestrator.internal.email.mapper.EmailMapper;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.EmailRepository;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.UserRepository;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class SaveEmailService implements Consumer<MailModel> {

    private final EmailMapper emailMapper;
    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    private final PlacesRepository placesRepository;

    @Override
    public void accept(MailModel mailModel) {
        Email email = emailMapper.map(mailModel);
        CustomAuthentication user = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();

        email.setFrom(new User()
                .setEmail(user.getEmail())
                .setId(user.getId()));

        mailModel.getTo()
                .stream()
                .map(this::findSendToEntity)
                .flatMap(Collection::stream)
                .forEach(user_ -> emailRepository.save(email
                            .setDate(OffsetDateTime.now())
                            .setToUser(user_)));
    }

    private List<User> findSendToEntity(String sendToName) {
        if(Objects.equals(sendToName, "SYSTEM")) {
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
