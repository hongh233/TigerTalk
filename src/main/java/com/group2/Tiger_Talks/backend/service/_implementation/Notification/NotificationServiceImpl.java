package com.group2.Tiger_Talks.backend.service._implementation.Notification;

import com.group2.Tiger_Talks.backend.model.Notification;
import com.group2.Tiger_Talks.backend.repository.Notification.NotificationRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public Optional<String> createNotification(Notification notification) {
        try {
            notificationRepository.save(notification);
            return Optional.empty();
        } catch (Exception e) {
            return Optional.of("Failed to create notification: " + e.getMessage());
        }
    }

    @Override
    public List<Notification> getNotificationListByUserEmail(String userEmail) {
        return userProfileRepository.findById(userEmail)
                .map(userProfile -> userProfile.getNotificationList()
                        .stream()
                        .sorted(Comparator.comparing(Notification::getCreateTime).reversed())
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public Optional<String> deleteNotificationById(int notificationId) {
        return notificationRepository.findById(notificationId)
                .map(notification -> {
                    notificationRepository.delete(notification);
                    return Optional.<String>empty();
                })
                .orElse(Optional.of("Notification not found with id: " + notificationId));
    }


}
