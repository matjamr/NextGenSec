package com.sec.gen.next.backend.internal.notification.config;

import com.sec.gen.next.backend.internal.notification.mapper.NotificationMapper;
import com.sec.gen.next.backend.internal.notification.repository.NotificationRepository;
import com.sec.gen.next.backend.internal.notification.service.NotificationServiceImpl;
import com.sec.gen.next.backend.internal.user.repository.UserRepository;
import com.sec.gen.next.backend.internal.notification.service.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotifBeansConfig {

    @Bean
    public NotificationService notificationService(
            final UserRepository userRepository,
            final NotificationRepository notificationRepository,
            final NotificationMapper notificationMapper
            ) {
        return new NotificationServiceImpl(userRepository, notificationRepository, notificationMapper);
    }
}
