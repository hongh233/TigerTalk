package com.group2.Tiger_Talks.backend.service._implementation.Friend;

import com.group2.Tiger_Talks.backend.model.Friend.Friendship;
import com.group2.Tiger_Talks.backend.model.Friend.FriendshipRequest;
import com.group2.Tiger_Talks.backend.model.Friend.FriendshipRequestDTO;
import com.group2.Tiger_Talks.backend.model.Notification.Notification;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Socials.FriendshipRepository;
import com.group2.Tiger_Talks.backend.repository.Socials.FriendshipRequestRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FriendshipRequestServiceImplTest {

    @Mock
    private FriendshipRequestRepository friendshipRequestRepository;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private FriendshipRequestServiceImpl friendshipRequestService;

    private UserProfile userA;
    private UserProfile userB;
    private UserProfile userC;

    @BeforeEach
    public void setUp() {
        userA = new UserProfile("Along", "Aside", 22, "Male",
                "userA", "a@dal.ca", "aaaa1A@a", new String[]{"1", "2", "3"},
                new String[]{"What was your favourite book as a child?", "In what city were you born?",
                        "What is the name of the hospital where you were born?"});
        userB = new UserProfile("Blong", "Bside", 23, "Female",
                "userB", "b@dal.ca", "bbbb1B@b", new String[]{"1", "2", "3"},
                new String[]{"What was your favourite book as a child?", "In what city were you born?",
                        "What is the name of the hospital where you were born?"});
        userC = new UserProfile("Clong", "Cside", 24, "Male",
                "userC", "c@dal.ca", "cccc1C@c", new String[]{"1", "2", "3"},
                new String[]{"What was your favourite book as a child?", "In what city were you born?",
                        "What is the name of the hospital where you were born?"});
    }

    /**
     * Test case for getAllFriendRequests
     */
    @Test
    public void getAllFriendRequests_userNotFound() {
        when(userProfileRepository.findUserProfileByEmail("nonexistent@example.com")).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> friendshipRequestService.getAllFriendRequests("nonexistent@example.com")
        );
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void getAllFriendRequests_success() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(friendshipRequestRepository.findByReceiver(userA)).thenReturn(List.of(new FriendshipRequest(userB, userA), new FriendshipRequest(userC, userA)));
        List<FriendshipRequestDTO> requests = friendshipRequestService.getAllFriendRequests("a@dal.ca");
        assertEquals(2, requests.size());
    }

    /**
     * Test case for sendFriendshipRequest
     */
    @Test
    public void sendFriendshipRequest_senderNotFound() {
        when(userProfileRepository.findUserProfileByEmail("nonexistent@example.com")).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> friendshipRequestService.sendFriendshipRequest("nonexistent@example.com", "b@dal.ca")
        );
        assertEquals("Sender not found", exception.getMessage());
    }

    @Test
    public void sendFriendshipRequest_receiverNotFound() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("nonexistent@example.com")).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> friendshipRequestService.sendFriendshipRequest("a@dal.ca", "nonexistent@example.com")
        );
        assertEquals("Receiver not found", exception.getMessage());
    }

    @Test
    public void sendFriendshipRequest_existingFriendship() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.of(new Friendship()));
        Optional<String> result = friendshipRequestService.sendFriendshipRequest("a@dal.ca", "b@dal.ca");
        assertTrue(result.isPresent());
        assertEquals("Friendship has already existed between these users.", result.get());
    }

    @Test
    public void sendFriendshipRequest_existingRequest() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.empty());
        when(friendshipRequestRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.of(new FriendshipRequest(userA, userB)));
        Optional<String> result = friendshipRequestService.sendFriendshipRequest("a@dal.ca", "b@dal.ca");
        assertTrue(result.isPresent());
        assertEquals("Friendship request has already existed between these users.", result.get());
    }

    @Test
    public void sendFriendshipRequest_success() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.empty());
        when(friendshipRequestRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.empty());
        Optional<String> result = friendshipRequestService.sendFriendshipRequest("a@dal.ca", "b@dal.ca");
        assertTrue(result.isEmpty());
    }

    @Test
    void sendFriendshipRequest_successWithNotification() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(any(), any())).thenReturn(Optional.empty());
        when(friendshipRequestRepository.findBySenderAndReceiver(any(), any())).thenReturn(Optional.empty());
        when(notificationService.createNotification(any())).thenReturn(Optional.empty());

        Optional<String> result = friendshipRequestService.sendFriendshipRequest("a@dal.ca", "b@dal.ca");
        assertTrue(result.isEmpty());
        verify(friendshipRequestRepository).save(any(FriendshipRequest.class));
        verify(notificationService).createNotification(any(Notification.class));
    }

    @Test
    void sendFriendshipRequest_failureNotification() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(any(), any())).thenReturn(Optional.empty());
        when(friendshipRequestRepository.findBySenderAndReceiver(any(), any())).thenReturn(Optional.empty());
        when(notificationService.createNotification(any())).thenReturn(Optional.of("Error sending notification"));

        Optional<String> result = friendshipRequestService.sendFriendshipRequest("a@dal.ca", "b@dal.ca");
        assertTrue(result.isPresent());
        assertEquals("Error sending notification", result.get());
    }

    /**
     * Test case for acceptFriendshipRequest
     */
    @Test
    public void acceptFriendshipRequest_requestNotFound() {
        when(friendshipRequestRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> friendshipRequestService.acceptFriendshipRequest(1)
        );
        assertEquals("friendship request ID does not exist!", exception.getMessage());
    }

    @Test
    public void acceptFriendshipRequest_success() {
        FriendshipRequest request = new FriendshipRequest(userA, userB);
        when(friendshipRequestRepository.findById(1)).thenReturn(Optional.of(request));
        Optional<String> result = friendshipRequestService.acceptFriendshipRequest(1);
        assertTrue(result.isEmpty());
    }

    @Test
    void acceptFriendshipRequest_successWithNotification() {
        FriendshipRequest request = new FriendshipRequest(userA, userB);
        when(friendshipRequestRepository.findById(1)).thenReturn(Optional.of(request));
        when(notificationService.createNotification(any())).thenReturn(Optional.empty());

        Optional<String> result = friendshipRequestService.acceptFriendshipRequest(1);
        assertTrue(result.isEmpty());
        verify(notificationService).createNotification(any(Notification.class));
    }

    @Test
    void acceptFriendshipRequest_failureNotification() {
        FriendshipRequest request = new FriendshipRequest(userA, userB);
        when(friendshipRequestRepository.findById(1)).thenReturn(Optional.of(request));
        when(notificationService.createNotification(any())).thenReturn(Optional.of("Error sending notification"));

        Optional<String> result = friendshipRequestService.acceptFriendshipRequest(1);
        assertTrue(result.isPresent());
        assertEquals("Error sending notification", result.get());
    }

    /**
     * Test case for rejectFriendshipRequest
     */
    @Test
    public void rejectFriendshipRequest_requestNotFound() {
        when(friendshipRequestRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> friendshipRequestService.rejectFriendshipRequest(1)
        );
        assertEquals("friendship request ID does not exist!", exception.getMessage());
    }

    @Test
    public void rejectFriendshipRequest_success() {
        FriendshipRequest request = new FriendshipRequest(userA, userB);
        when(friendshipRequestRepository.findById(1)).thenReturn(Optional.of(request));
        Optional<String> result = friendshipRequestService.rejectFriendshipRequest(1);
        assertFalse(result.isPresent());
    }

    @Test
    void rejectFriendshipRequest_successWithNotification() {
        FriendshipRequest request = new FriendshipRequest(userA, userB);
        when(friendshipRequestRepository.findById(1)).thenReturn(Optional.of(request));
        when(notificationService.createNotification(any())).thenReturn(Optional.empty());

        Optional<String> result = friendshipRequestService.rejectFriendshipRequest(1);
        assertTrue(result.isEmpty());
        verify(notificationService).createNotification(any(Notification.class));
    }

    @Test
    void rejectFriendshipRequest_failureNotification() {
        FriendshipRequest request = new FriendshipRequest(userA, userB);
        when(friendshipRequestRepository.findById(1)).thenReturn(Optional.of(request));
        when(notificationService.createNotification(any())).thenReturn(Optional.of("Error sending notification"));

        Optional<String> result = friendshipRequestService.rejectFriendshipRequest(1);
        assertTrue(result.isPresent());
        assertEquals("Error sending notification", result.get());
    }

    /**
     * Test case for areFriendshipRequestExist
     */
    @Test
    public void areFriendshipRequestExist_userNotFound() {
        when(userProfileRepository.findUserProfileByEmail("nonexistent@example.com")).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> friendshipRequestService.areFriendshipRequestExist("nonexistent@example.com", "a@dal.ca")
        );
        assertEquals("Sender not found", exception.getMessage());
    }

    @Test
    public void areFriendshipRequestExist_areFriends_returnsTrue() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.of(new Friendship()));

        boolean result = friendshipRequestService.areFriendshipRequestExist("a@dal.ca", "b@dal.ca");
        assertTrue(result, "Expected true as a and b are friends");
    }

    @Test
    public void areFriendshipRequestExist_notFriends_returnsFalse() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.empty());
        when(friendshipRepository.findBySenderAndReceiver(userB, userA)).thenReturn(Optional.empty());

        boolean result = friendshipRequestService.areFriendshipRequestExist("a@dal.ca", "b@dal.ca");
        assertFalse(result, "Expected false as there is no friendship between a and b");
    }
}
