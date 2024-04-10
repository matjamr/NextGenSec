package com.sec.gen.next.serviceorchestrator.notification.config;

import com.next.gen.sec.model.NotificationModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.notification.mapper.NotificationMapper;
import com.sec.gen.next.serviceorchestrator.notification.repository.NotificationRepository;
import com.sec.gen.next.serviceorchestrator.notification.service.CrudNotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotifBeansConfig {

    @Bean
    public CrudService<NotificationModel, NotificationModel, String> notificationService(NotificationRepository notificationRepository, NotificationMapper notificationMapper) {
        return new CrudNotificationService(notificationRepository, notificationMapper);
    }
}
