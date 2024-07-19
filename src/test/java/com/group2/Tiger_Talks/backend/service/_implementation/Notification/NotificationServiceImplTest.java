package com.group2.Tiger_Talks.backend.service._implementation.Notification;

import com.group2.Tiger_Talks.backend.model.Notification.Notification;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Notification.NotificationRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     *  Test case for createNotification
     */
    @Test
    public void createNotification_success() {
        Notification notification = new Notification();
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
        Optional<String> result = notificationService.createNotification(notification);
        assertFalse(result.isPresent());
    }

    @Test
    public void createNotification_fail() {
        Notification notification = new Notification();
        when(notificationRepository.save(any(Notification.class))).thenThrow(new RuntimeException("Database error"));
        Optional<String> result = notificationService.createNotification(notification);
        assertTrue(result.isPresent());
        assertEquals("Failed to create notification: Database error", result.get());
    }

    /**
     *  Test case for getNotificationListByUserEmail
     */
    @Test
    public void getNotificationListByUserEmail_userFound_twoNotifications() {
        UserProfile userProfile = mock(UserProfile.class);
        Notification notification1 = mock(Notification.class);
        Notification notification2 = mock(Notification.class);
        LocalDateTime now = LocalDateTime.now();
        when(notification1.getCreateTime()).thenReturn(now.minusHours(1));
        when(notification2.getCreateTime()).thenReturn(now);
        when(userProfile.getNotificationList()).thenReturn(Arrays.asList(notification1, notification2));
        when(userProfileRepository.findById("user@example.com")).thenReturn(Optional.of(userProfile));

        List<Notification> results = notificationService.getNotificationListByUserEmail("user@example.com");
        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        assertEquals(notification2, results.get(0));
        assertEquals(notification1, results.get(1));
    }

    @Test
    public void getNotificationListByUserEmail_userFound_oneNotification() {
        UserProfile userProfile = mock(UserProfile.class);
        Notification notification = mock(Notification.class);
        LocalDateTime now = LocalDateTime.now();
        when(notification.getCreateTime()).thenReturn(now);
        when(userProfile.getNotificationList()).thenReturn(List.of(notification));
        when(userProfileRepository.findById("user@example.com")).thenReturn(Optional.of(userProfile));

        List<Notification> results = notificationService.getNotificationListByUserEmail("user@example.com");
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(notification, results.get(0));
    }

    @Test
    public void getNotificationListByUserEmail_userNotFound() {
        when(userProfileRepository.findById("user@example.com")).thenReturn(Optional.empty());
        List<Notification> results = notificationService.getNotificationListByUserEmail("user@example.com");
        assertTrue(results.isEmpty());
    }

    /**
     *  Test case for deleteNotificationById
     */
    @Test
    public void deleteNotificationById_Success() {
        Notification notification = new Notification();
        when(notificationRepository.findById(1)).thenReturn(Optional.of(notification));
        Optional<String> result = notificationService.deleteNotificationById(1);
        assertFalse(result.isPresent());
    }

    @Test
    public void deleteNotificationById_NotFound() {
        when(notificationRepository.findById(1)).thenReturn(Optional.empty());
        Optional<String> result = notificationService.deleteNotificationById(1);
        assertTrue(result.isPresent());
        assertEquals("Notification not found with id: 1", result.get());
    }
}