package com.sec.gen.next.serviceorchestrator.internal.notification.service;

import com.next.gen.api.custom.BetterOptional;
import com.next.gen.sec.model.NotificationModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import com.sec.gen.next.serviceorchestrator.internal.notification.repository.NotificationRepository;
import com.sec.gen.next.serviceorchestrator.internal.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_NOTIFICATION_DATA;
import static com.sec.gen.next.serviceorchestrator.exception.Error.NO_NOTIF_ID;

@RequiredArgsConstructor
public class CrudNotificationService implements CrudService<NotificationModel, NotificationModel, String> {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public NotificationModel delete(NotificationModel notificationModel) {
        return BetterOptional.of(notificationModel)
                .verify(() -> notificationRepository.findById(notificationModel.getId()).isPresent(), NO_NOTIF_ID.getError())
                .map(notificationMapper::mapTo)
                .peek(notificationRepository::delete)
                .map(notificationMapper::map)
                .orElseThrow(INVALID_NOTIFICATION_DATA::getError);
    }

    @Override
    public List<NotificationModel> findAll() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return notificationRepository.findAll()
                .stream()
                .filter(notification -> notification.getUser().getEmail().equals(email))
                .map(notificationMapper::map)
                .toList();
    }

    @Override
    public List<NotificationModel> saveAll(List<NotificationModel> notificationModels) {
        return Optional.of(notificationModels)
                .map(notificationMapper::mapTo)
                .map(notificationRepository::saveAll)
                .map(notificationMapper::mapList)
                .orElseThrow(INVALID_NOTIFICATION_DATA::getError);
    }
}
