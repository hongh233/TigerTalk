package com.group2.Tiger_Talks.backend.repository;

import com.group2.Tiger_Talks.backend.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
