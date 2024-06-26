package com.sec.gen.next.serviceorchestrator.internal.notification.repository;

import com.next.gen.api.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
}
