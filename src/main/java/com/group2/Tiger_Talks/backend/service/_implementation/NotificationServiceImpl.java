package com.group2.Tiger_Talks.backend.service._implementation;

import com.group2.Tiger_Talks.backend.repository.NotificationRepository;
import com.group2.Tiger_Talks.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

}
