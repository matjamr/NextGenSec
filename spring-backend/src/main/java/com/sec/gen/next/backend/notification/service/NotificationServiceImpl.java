package com.sec.gen.next.backend.notification.service;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.exception.ServiceException;
import com.sec.gen.next.backend.api.external.NotificationModel;
import com.sec.gen.next.backend.api.internal.Notification;
import com.sec.gen.next.backend.api.internal.User;
import com.sec.gen.next.backend.notification.repository.NotificationRepository;
import com.sec.gen.next.backend.user.repository.UserRepository;
import com.sec.gen.next.backend.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public List<NotificationModel> getAllNotifications() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return notificationRepository.findAll()
                .stream()
                .filter(notification -> notification.getUser().getEmail().equals(email))
                .map(notificationMapper::map)
                .toList();
    }

    @Override
    public void sendAllNotifications(List<NotificationModel> notificationModels) {
        var ret = notificationModels.stream()
                .map(notificationMapper::mapTo)
                .map(notification -> notification.setUser(getUser(notification)))
                .toList();

        notificationRepository.saveAll(ret);
    }

    private User getUser(Notification notification) {
        return userRepository.findUserByEmail(notification.getUser().getEmail())
                .orElseThrow(() -> new ServiceException(Error.INVALID_USER_DATA));
    }

    @Override
    public void deleteNotification(NotificationModel notificationModel) {
        notificationRepository.deleteById(notificationModel.getId());
    }
}
