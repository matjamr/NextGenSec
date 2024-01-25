package com.sec.gen.next.backend.internal.notification.service;

import com.sec.gen.next.backend.internal.api.external.NotificationModel;
import com.sec.gen.next.backend.internal.api.internal.ClaimsUser;

import java.util.List;

public interface NotificationService {

    List<NotificationModel> getAllNotifications(ClaimsUser claimsUser);
    void sendAllNotifications(List<NotificationModel> notificationModels);
    void deleteNotification(NotificationModel notificationModel);
}
