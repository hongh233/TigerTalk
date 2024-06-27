package com.group2.Tiger_Talks.backend.model;

import jakarta.persistence.*;
import org.apache.catalina.User;

import java.time.LocalDate;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId;

    private String notification;

    private LocalDate createTime;

    @ManyToOne
    @JoinColumn(name = "user_profile_id", referencedColumnName = "email")
    private UserProfile userProfile;

    public Notification() {}

    public Notification(UserProfile userProfile, String notification, LocalDate createTime) {
        this.userProfile = userProfile;
        this.notification = notification;
        this.createTime = createTime;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }


}
