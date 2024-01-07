package com.sec.gen.next.backend.notification.service;

import com.sec.gen.next.backend.api.external.NotificationModel;
import com.sec.gen.next.backend.api.internal.ClaimsUser;
import com.sec.gen.next.backend.api.internal.Notification;

import java.util.List;

public interface NotificationService {

    List<NotificationModel> getAllNotifications(ClaimsUser claimsUser);
    void sendAllNotifications(List<NotificationModel> notificationModels);
    void deleteNotification(NotificationModel notificationModel);
}
