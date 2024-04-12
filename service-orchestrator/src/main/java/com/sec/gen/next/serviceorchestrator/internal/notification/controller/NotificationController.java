package com.sec.gen.next.serviceorchestrator.internal.notification.controller;

import com.next.gen.sec.model.NotificationModel;
import com.sec.gen.next.serviceorchestrator.common.templates.CrudService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final CrudService<NotificationModel, NotificationModel, String> notificationService;

    @PostMapping
    public void sendNotifications(@RequestBody List<NotificationModel> notificationModelList) {
        notificationService.saveAll(notificationModelList);
    }

    @GetMapping
    public List<NotificationModel> getNotifications() {
        return notificationService.findAll();
    }

    @DeleteMapping
    public void deleteNotification(@RequestBody NotificationModel notificationModel) {
        notificationService.delete(notificationModel);
    }
}
