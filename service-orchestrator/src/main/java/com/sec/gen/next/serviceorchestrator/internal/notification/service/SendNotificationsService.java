package com.sec.gen.next.serviceorchestrator.internal.notification.service;

import com.next.gen.api.Places;
import com.next.gen.sec.model.KafkaChatServiceModel;
import com.next.gen.sec.model.ModifyUserToPlaceModel;
import com.next.gen.sec.model.NotificationModel;
import com.next.gen.sec.model.Topic;
import com.sec.gen.next.serviceorchestrator.common.templates.SaveService;
import com.sec.gen.next.serviceorchestrator.external.kafka.KafkaProducer;
import com.sec.gen.next.serviceorchestrator.internal.notification.mapper.NotificationMapper;
import com.sec.gen.next.serviceorchestrator.internal.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SendNotificationsService implements SaveService<NotificationModel, NotificationModel> {
    private final KafkaProducer<KafkaChatServiceModel> kafkaChatServiceProducer;
    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;

    @Override
    public NotificationModel save(NotificationModel notificationModel) {

        return Optional.of(notificationModel)
                .map(notificationMapper::mapTo)
                .map(notificationRepository::save)
                .map(notificationMapper::map)
                .map(this::sendNotifications)
                .orElseThrow();
    }

    private NotificationModel sendNotifications(NotificationModel notificationModel) {
        kafkaChatServiceProducer.sendMessage(new KafkaChatServiceModel()
                .id(notificationModel.getId())
                .commonRecipients(List.of(notificationModel.getUser().getEmail()))
                .message(notificationModel.getContent())
                .topic(Topic.NOTIFICATION));
        return notificationModel;
    }
}
