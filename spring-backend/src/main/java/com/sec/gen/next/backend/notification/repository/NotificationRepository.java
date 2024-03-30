package com.sec.gen.next.backend.notification.repository;

import com.sec.gen.next.backend.api.internal.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
