package com.group2.Tiger_Talks.backend.service._implementation.Friend;

import com.group2.Tiger_Talks.backend.model.Friend.Friendship;
import com.group2.Tiger_Talks.backend.model.Friend.FriendshipDTO;
import com.group2.Tiger_Talks.backend.model.Notification.Notification;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.model.User.UserProfileDTOFriendship;
import com.group2.Tiger_Talks.backend.repository.Socials.FriendshipRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Notification.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FriendshipServiceImplTest {

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private FriendshipServiceImpl friendshipService;

    private UserProfile userA;
    private UserProfile userB;
    private UserProfile userC;
    private Friendship friendshipAB;
    private Friendship friendshipAC;

    @BeforeEach
    public void setUp() {
        userA = new UserProfile("Along", "Aside", 22, "Male", "userA", "a@dal.ca", "aaaa1A@a", new String[]{"1", "2", "3"}, new String[]{"What was your favourite book as a child?", "In what city were you born?", "What is the name of the hospital where you were born?"});
        userB = new UserProfile("Blong", "Bside", 23, "Female", "userB", "b@dal.ca", "bbbb1B@b", new String[]{"1", "2", "3"}, new String[]{"What was your favourite book as a child?", "In what city were you born?", "What is the name of the hospital where you were born?"});
        userC = new UserProfile("Clong", "Cside", 24, "Male", "userC", "c@dal.ca", "cccc1C@c", new String[]{"1", "2", "3"}, new String[]{"What was your favourite book as a child?", "In what city were you born?", "What is the name of the hospital where you were born?"});
        friendshipAB = new Friendship(userA, userB);
        friendshipAC = new Friendship(userA, userC);
    }

    /**
     * Test case for getAllFriendsDTO
     */
    @Test
    public void getAllFriendsDTO_userNotFound() {
        when(userProfileRepository.findUserProfileByEmail("nonexistent@example.com")).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> friendshipService.getAllFriendsDTO("nonexistent@example.com")
        );
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void getAllFriendsDTO_success() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(friendshipRepository.findBySenderOrReceiver(userA, userA)).thenReturn(List.of(friendshipAB, friendshipAC));
        List<UserProfileDTOFriendship> friends = friendshipService.getAllFriendsDTO("a@dal.ca");
        assertEquals(2, friends.size());
        assertTrue(friends.stream().anyMatch(friend -> friend.email().equals("b@dal.ca")));
        assertTrue(friends.stream().anyMatch(friend -> friend.email().equals("c@dal.ca")));
    }

    /**
     * Test case for getAllFriends
     */
    @Test
    public void getAllFriends_userNotFound() {
        when(userProfileRepository.findUserProfileByEmail("nonexistent@example.com")).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> friendshipService.getAllFriends("nonexistent@example.com")
        );
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void getAllFriends_success() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(friendshipRepository.findBySenderOrReceiver(userA, userA)).thenReturn(List.of(friendshipAB, friendshipAC));
        List<FriendshipDTO> friends = friendshipService.getAllFriends("a@dal.ca");
        assertEquals(2, friends.size());
    }

    /**
     * Test case for deleteFriendshipByEmail
     */
    @Test
    public void deleteFriendshipByEmail_senderNotFound() {
        when(userProfileRepository.findUserProfileByEmail("nonexistent@example.com")).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> friendshipService.deleteFriendshipByEmail("nonexistent@example.com", "b@dal.ca")
        );
        assertEquals("Sender not found", exception.getMessage());
    }

    @Test
    public void deleteFriendshipByEmail_receiverNotFound() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("nonexistent@example.com")).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> friendshipService.deleteFriendshipByEmail("a@dal.ca", "nonexistent@example.com")
        );
        assertEquals("Receiver not found", exception.getMessage());
    }

    @Test
    public void deleteFriendshipByEmail_noFriendship() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.empty());
        Optional<String> result = friendshipService.deleteFriendshipByEmail("a@dal.ca", "b@dal.ca");
        assertTrue(result.isPresent());
        assertEquals("No friendship exists between these users.", result.get());
    }

    @Test
    public void deleteFriendshipByEmail_success() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.of(friendshipAB));
        Optional<String> result = friendshipService.deleteFriendshipByEmail("a@dal.ca", "b@dal.ca");
        assertFalse(result.isPresent());
    }

    @Test
    public void deleteFriendshipByEmail_successWithNotifications() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.of(friendshipAB));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());

        Optional<String> result = friendshipService.deleteFriendshipByEmail("a@dal.ca", "b@dal.ca");
        assertFalse(result.isPresent());
        verify(notificationService, times(2)).createNotification(any(Notification.class));
    }

    @Test
    public void deleteFriendshipByEmail_failureSendingNotification() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.of(friendshipAB));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.of("Notification service failed"));

        Optional<String> result = friendshipService.deleteFriendshipByEmail("a@dal.ca", "b@dal.ca");
        assertTrue(result.isPresent());
        assertEquals("Notification service failed", result.get());
        verify(notificationService, times(1)).createNotification(any(Notification.class)); // Called once then fails
    }

    @Test
    public void deleteFriendshipByEmail_verifyNotificationDetails() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.of(friendshipAB));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());

        friendshipService.deleteFriendshipByEmail("a@dal.ca", "b@dal.ca");
        verify(notificationService).createNotification(argThat(notification ->
                notification.getContent().equals("Your friendship with b@dal.ca has been terminated.")
                        && notification.getUserProfile().equals(userA)));
        verify(notificationService).createNotification(argThat(notification ->
                notification.getContent().equals("Your friendship with a@dal.ca has been terminated.")
                        && notification.getUserProfile().equals(userB)));
    }

    /**
     * Test case for areFriends
     */
    @Test
    public void areFriends_success() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(friendshipRepository.findBySenderOrReceiver(userA, userA)).thenReturn(List.of(friendshipAB));
        boolean result = friendshipService.areFriends("a@dal.ca", "b@dal.ca");
        assertTrue(result);
    }

    @Test
    public void areFriends_notFriends() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(friendshipRepository.findBySenderOrReceiver(userA, userA)).thenReturn(List.of(friendshipAC));
        boolean result = friendshipService.areFriends("a@dal.ca", "b@dal.ca");
        assertFalse(result);
    }
}
