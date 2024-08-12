package tigertalk.service.Notification;

import tigertalk.model.Notification.Notification;
import tigertalk.model.Notification.NotificationDTO;

import java.util.List;
import java.util.Optional;

public interface NotificationService {

    /**
     * Creates a notification and saves it into the database.
     *
     * @param notification the notification to create and save
     * @return an Optional containing an error message if the notification creation fails, or empty if successful
     */
    Optional<String> createNotification(Notification notification);

    /**
     * Retrieves a list of notifications for a specific user, identified by their email address.
     *
     * @param userEmail the email address of the user whose notifications are to be retrieved
     * @return a list of notifications for the user
     */
    List<NotificationDTO> getNotificationListByUserEmail(String userEmail);

    /**
     * Deletes a notification based on its unique ID.
     *
     * @param notificationId the unique ID of the notification to delete
     * @return an Optional containing an error message if the deletion fails, or empty if the deletion is successful
     */
    Optional<String> deleteNotificationById(int notificationId);
}
