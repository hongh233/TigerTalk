package com.group2.Tiger_Talks.backend.controller.Notification;

import com.group2.Tiger_Talks.backend.model.Notification.NotificationDTO;
import com.group2.Tiger_Talks.backend.service.Notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/get/{email}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserEmail(@PathVariable String email) {
        List<NotificationDTO> notifications = notificationService.getNotificationListByUserEmail(email);
        return ResponseEntity.ok(notifications);
    }

    @DeleteMapping("/delete/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable int notificationId) {
        Optional<String> result = notificationService.deleteNotificationById(notificationId);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
