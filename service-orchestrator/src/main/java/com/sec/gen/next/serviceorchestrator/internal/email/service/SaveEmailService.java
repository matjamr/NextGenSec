package com.sec.gen.next.serviceorchestrator.internal.email.service;

import com.next.gen.api.Email;
import com.next.gen.api.Places;
import com.next.gen.api.User;
import com.next.gen.api.UserPlaceAssignment;
import com.next.gen.api.security.CustomAuthentication;
import com.next.gen.sec.model.MailModel;
import com.next.gen.sec.model.NotificationModel;
import com.next.gen.sec.model.OutboundEmailModel;
import com.next.gen.sec.model.Role;
import com.sec.gen.next.serviceorchestrator.common.templates.SaveService;
import com.sec.gen.next.serviceorchestrator.external.kafka.KafkaProducer;
import com.sec.gen.next.serviceorchestrator.internal.email.mapper.EmailMapper;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.EmailRepository;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.UserRepository;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.UserMapper;
import com.sec.gen.next.serviceorchestrator.internal.places.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class SaveEmailService implements Consumer<MailModel> {

    private final EmailMapper emailMapper;
    private final UserMapper userMapper;
    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    private final PlacesRepository placesRepository;
    private final KafkaProducer<OutboundEmailModel> outboundEmailModelKafkaProducer;
    private final SaveService<NotificationModel, NotificationModel> sendNotificationsService;

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
                .forEach(user_ -> {
                    emailRepository.save(email
                            .setDate(OffsetDateTime.now())
                            .setToUser(user_));

                    outboundEmailModelKafkaProducer.sendMessage(new OutboundEmailModel()
                            .email(user_.getEmail())
                            .strategy("EMAIL_RECEIVED")
                            .subject(mailModel.getSubject())
                            .params(Map.of(
                                    "emailFrom", user.getEmail(),
                                    "content", mailModel.getContent(),
                                    "subject", mailModel.getSubject()
                            )));


                    sendNotificationsService.save(new NotificationModel()
                            .content("New email from [" + user.getEmail() + "]")
                            .date(OffsetDateTime.now())
                            .user(userMapper.map(user_)));
                });
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
