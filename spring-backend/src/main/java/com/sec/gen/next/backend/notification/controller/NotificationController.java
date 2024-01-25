package com.sec.gen.next.backend.notification.controller;

import com.sec.gen.next.backend.api.external.NotificationModel;
import com.sec.gen.next.backend.notification.service.NotificationService;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    public void sendNotifications(@RequestBody List<NotificationModel> notificationModelList) {
        notificationService.sendAllNotifications(notificationModelList);
    }

    @GetMapping
    public List<NotificationModel> getNotifications() {
        return notificationService.getAllNotifications();
    }

    @DeleteMapping
    public void deleteNotification(@RequestBody NotificationModel notificationModel) {
        notificationService.deleteNotification(notificationModel);
    }
}
