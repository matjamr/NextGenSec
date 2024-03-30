package com.sec.gen.next.backend.notification.service;

import com.sec.gen.next.backend.api.external.NotificationModel;

import java.util.List;

public interface NotificationService {

    List<NotificationModel> getAllNotifications();
    void sendAllNotifications(List<NotificationModel> notificationModels);
    void deleteNotification(NotificationModel notificationModel);
}
