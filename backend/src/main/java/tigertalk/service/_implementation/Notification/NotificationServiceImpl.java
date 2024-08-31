package tigertalk.service._implementation.Notification;

import tigertalk.model.Notification.Notification;
import tigertalk.model.Notification.NotificationDTO;
import tigertalk.model.User.UserProfile;
import tigertalk.repository.Notification.NotificationRepository;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.Notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
    public List<NotificationDTO> getNotificationListByUserEmail(String userEmail) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userEmail);

        if (userProfileOptional.isPresent()) {
            UserProfile userProfile = userProfileOptional.get();
            List<Notification> notifications = userProfile.getNotificationList();

            List<NotificationDTO> notificationDTOs = new ArrayList<>();
            for (Notification notification : notifications) {
                notificationDTOs.add(notification.toDto());
            }

            notificationDTOs.sort(Comparator.comparing(NotificationDTO::createTime).reversed());
            return notificationDTOs;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<String> deleteNotificationById(int notificationId) {
        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            notificationRepository.delete(notification);
            return Optional.empty();
        } else {
            return Optional.of("Notification not found with id: " + notificationId);
        }
    }
}
