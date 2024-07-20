package com.group2.Tiger_Talks.backend.service._implementation.Notification;

import com.group2.Tiger_Talks.backend.model.Notification.Notification;
import com.group2.Tiger_Talks.backend.model.Notification.NotificationDTO;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Notification.NotificationRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
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
        Notification notification3 = mock(Notification.class);
        Notification notification4 = mock(Notification.class);
        LocalDateTime now = LocalDateTime.now();
        lenient().when(notification3.getCreateTime()).thenReturn(now.minusHours(1));
        lenient().when(notification4.getCreateTime()).thenReturn(now);
        lenient().when(notification3.getUserProfile()).thenReturn(userProfile);
        lenient().when(notification4.getUserProfile()).thenReturn(userProfile);
        lenient().when(userProfile.getNotificationList()).thenReturn(Arrays.asList(notification3, notification4));
        lenient().when(userProfileRepository.findById("user@example.com")).thenReturn(Optional.of(userProfile));

        NotificationDTO dto3 = mock(NotificationDTO.class);
        NotificationDTO dto4 = mock(NotificationDTO.class);
        lenient().when(dto3.createTime()).thenReturn(now.minusHours(1));
        lenient().when(dto4.createTime()).thenReturn(now);

        lenient().when(notification3.toDto()).thenReturn(dto3);
        lenient().when(notification4.toDto()).thenReturn(dto4);

        List<NotificationDTO> results = notificationService.getNotificationListByUserEmail("user@example.com");
        assertFalse(results.isEmpty());
        assertEquals(2, results.size());

        assertEquals(dto4.createTime(), results.get(0).createTime());
        assertEquals(dto3.createTime(), results.get(1).createTime());
    }

    @Test
    public void getNotificationListByUserEmail_userFound_oneNotification() {
        UserProfile userProfile = mock(UserProfile.class);
        NotificationDTO notification = mock(NotificationDTO.class);
        Notification notification1 = mock(Notification.class);
        LocalDateTime now = LocalDateTime.now();

        lenient().when(notification.createTime()).thenReturn(now);
        lenient().when(userProfile.getNotificationList()).thenReturn(List.of(notification1));
        lenient().when(notification1.getUserProfile()).thenReturn(userProfile);
        lenient().when(userProfileRepository.findById("user@example.com")).thenReturn(Optional.of(userProfile));

        List<NotificationDTO> results = notificationService.getNotificationListByUserEmail("user@example.com");
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    public void getNotificationListByUserEmail_userNotFound() {
        when(userProfileRepository.findById("user@example.com")).thenReturn(Optional.empty());
        List<NotificationDTO> results = notificationService.getNotificationListByUserEmail("user@example.com");
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