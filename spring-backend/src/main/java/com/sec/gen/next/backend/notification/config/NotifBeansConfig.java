package com.sec.gen.next.backend.notification.config;

import com.sec.gen.next.backend.notification.mapper.NotificationMapper;
import com.sec.gen.next.backend.notification.repository.NotificationRepository;
import com.sec.gen.next.backend.notification.service.NotificationServiceImpl;
import com.sec.gen.next.backend.user.repository.UserRepository;
import com.sec.gen.next.backend.notification.service.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotifBeansConfig {

    @Bean
    public NotificationService notificationService(
            final NotificationRepository notificationRepository,
            final NotificationMapper notificationMapper
            ) {
        return new NotificationServiceImpl(notificationRepository, notificationMapper);
    }
}
