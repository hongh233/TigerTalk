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
    public List<NotificationDTO> getNotificationsByUserEmail(@PathVariable String email) {
        return notificationService.getNotificationListByUserEmail(email);
    }

    /**
     * Deletes a notification by its ID.
     *
     * @param notificationId the ID of the notification to be deleted
     * @return ResponseEntity with a success message or a not found status
     */
    @DeleteMapping("/delete/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable int notificationId) {
        Optional<String> error = notificationService.deleteNotificationById(notificationId);
        if (error.isPresent()) {
            return ResponseEntity.status(404).body(error.get());
        } else {
            return ResponseEntity.status(200).body("Notification deleted successfully.");
        }
    }

}
