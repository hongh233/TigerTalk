package tigertalk.controller.Notification;

import tigertalk.model.Notification.NotificationDTO;
import tigertalk.service.Notification.NotificationService;
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

    /**
     * Retrieves all notifications for a specific user by their email.
     *
     * @param email the email of the user
     * @return ResponseEntity with a list of notification DTOs
     */
    @GetMapping("/get/{email}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserEmail(@PathVariable String email) {
        List<NotificationDTO> notifications = notificationService.getNotificationListByUserEmail(email);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Deletes a notification by its ID.
     *
     * @param notificationId the ID of the notification to be deleted
     * @return ResponseEntity with a success message or a not found status
     */
    @DeleteMapping("/delete/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable int notificationId) {
        Optional<String> result = notificationService.deleteNotificationById(notificationId);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
