package com.sec.gen.next.backend.internal.notification.service;

import com.sec.gen.next.backend.internal.api.exception.Error;
import com.sec.gen.next.backend.internal.api.exception.ServiceException;
import com.sec.gen.next.backend.internal.api.external.NotificationModel;
import com.sec.gen.next.backend.internal.api.internal.ClaimsUser;
import com.sec.gen.next.backend.internal.api.internal.Notification;
import com.sec.gen.next.backend.internal.api.internal.User;
import com.sec.gen.next.backend.internal.notification.repository.NotificationRepository;
import com.sec.gen.next.backend.internal.user.repository.UserRepository;
import com.sec.gen.next.backend.internal.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public List<NotificationModel> getAllNotifications(ClaimsUser claimsUser) {
        return notificationRepository.findAll()
                .stream()
                .filter(notification -> notification.getUser().getEmail().equals(claimsUser.getEmail()))
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
