package com.sec.gen.next.serviceorchestrator.internal.email.service.save;

import com.next.gen.api.Email;
import com.next.gen.api.User;
import com.next.gen.api.security.CustomAuthentication;
import com.next.gen.sec.model.MailModel;
import com.next.gen.sec.model.NotificationModel;
import com.sec.gen.next.serviceorchestrator.common.templates.SaveService;
import com.sec.gen.next.serviceorchestrator.internal.email.repository.UserRepository;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.function.BiConsumer;

@Component
@RequiredArgsConstructor
public class NotificationConsumer implements BiConsumer<Email, MailModel> {
    private final SaveService<NotificationModel, NotificationModel> sendNotificationsService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public void accept(Email email, MailModel mailModel) {
        mailModel.getTo().forEach(receiverEmail -> sendNotification(receiverEmail, email.getToUser()));
    }

    private void sendNotification(String receiverEmail, User user) {
        sendNotificationsService.save(new NotificationModel()
                .content("New email from [" + user.getEmail() + "]")
                .date(OffsetDateTime.now())
                .user(userMapper.map(
                        userRepository.findByEmail(receiverEmail)
                        .orElseThrow()
                        )
                ));
    }
}
