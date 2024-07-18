package com.group2.Tiger_Talks.backend.service._implementation;

import com.group2.Tiger_Talks.backend.repository.Notification.NotificationRepository;
import com.group2.Tiger_Talks.backend.service.Notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

}
