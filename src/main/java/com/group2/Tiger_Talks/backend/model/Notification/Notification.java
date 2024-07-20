package com.group2.Tiger_Talks.backend.model.Notification;

import com.group2.Tiger_Talks.backend.model.DtoConvertible;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Notification implements DtoConvertible<NotificationDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId;

    private String content;

    private LocalDateTime createTime;

    // FriendshipRequestSend,
    // FriendshipRequestAccept,
    // FriendshipRequestReject,
    // FriendshipDelete,
    // NewPost,
    // PostLiked
    private String notificationType;

    @ManyToOne
    @JoinColumn(name = "user_profile_id", referencedColumnName = "email")
    private UserProfile userProfile;

    public Notification() {
    }

    public Notification(UserProfile userProfile, String content, String type) {
        this.userProfile = userProfile;
        this.content = content;
        this.notificationType = type;
        this.createTime = LocalDateTime.now();
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    @Override
    public NotificationDTO toDto() {
        return new NotificationDTO(
                this.getNotificationId(),
                this.getContent(),
                this.getCreateTime(),
                this.getNotificationType(),
                this.getUserProfile().getEmail()
        );
    }

    @Override
    public void updateFromDto(NotificationDTO notificationDTO) {
        // Not Updatable
    }
}
