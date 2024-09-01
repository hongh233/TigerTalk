package tigertalk.Tiger_Talks.backend.service._implementation.Friend;

import tigertalk.model.Friend.Friendship;
import tigertalk.model.Notification.Notification;
import tigertalk.model.User.UserProfile;
import tigertalk.model.Friend.UserProfileDTOFriendship;
import tigertalk.repository.Friend.FriendshipRepository;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.Notification.NotificationService;
import tigertalk.service._implementation.Friend.FriendshipServiceImpl;
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
    private Friendship friendshipBA;
    private Friendship friendshipAC;
    private Friendship friendshipCA;

    @BeforeEach
    public void setUp() {
        userA = new UserProfile();
        userA.setFirstName("Along");
        userA.setLastName("Aside");
        userA.setAge(22);
        userA.setGender("Male");
        userA.setUserName("userA");
        userA.setEmail("a@dal.ca");
        userA.setPassword("aaaa1A@a");
        userA.setSecurityQuestion("1");
        userA.setSecurityQuestionAnswer("What was your favourite book as a child?");

        userB = new UserProfile();
        userB.setFirstName("Blong");
        userB.setLastName("Bside");
        userB.setAge(23);
        userB.setGender("Female");
        userB.setUserName("userB");
        userB.setEmail("b@dal.ca");
        userB.setPassword("bbbb1B@b");
        userB.setSecurityQuestion("1");
        userB.setSecurityQuestionAnswer("What was your favourite book as a child?");

        userC = new UserProfile();
        userC.setFirstName("Clong");
        userC.setLastName("Cside");
        userC.setAge(24);
        userC.setGender("Male");
        userC.setUserName("userC");
        userC.setEmail("c@dal.ca");
        userC.setPassword("cccc1C@c");
        userC.setSecurityQuestion("1");
        userC.setSecurityQuestionAnswer("What was your favourite book as a child?");

        friendshipAB = new Friendship(userA, userB);
        friendshipAC = new Friendship(userA, userC);
        friendshipBA = new Friendship(userB, userA);
        friendshipCA = new Friendship(userC, userA);
    }

    /**
     * Test case for getAllFriendsDTO
     */
    @Test
    public void getAllFriendsDTO_userNotFound() {
        when(userProfileRepository.findUserProfileByEmail("nonexistent@dal.ca")).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> friendshipService.getAllFriendsDTO("nonexistent@dal.ca")
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

    @Test
    public void getAllFriendsDTO_userAsSender() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(friendshipRepository.findBySenderOrReceiver(userA, userA)).thenReturn(List.of(friendshipAB));
        List<UserProfileDTOFriendship> friends = friendshipService.getAllFriendsDTO("a@dal.ca");
        assertEquals(1, friends.size());
        assertEquals("b@dal.ca", friends.get(0).email());
    }

    @Test
    public void getAllFriendsDTO_userAsReceiver() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(friendshipRepository.findBySenderOrReceiver(userA, userA)).thenReturn(List.of(friendshipBA));
        List<UserProfileDTOFriendship> friends = friendshipService.getAllFriendsDTO("a@dal.ca");
        assertEquals(1, friends.size());
        assertEquals("b@dal.ca", friends.get(0).email());
    }

    @Test
    public void getAllFriendsDTO_userAsBothSenderAndReceiver() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(friendshipRepository.findBySenderOrReceiver(userA, userA)).thenReturn(List.of(friendshipCA));
        List<UserProfileDTOFriendship> friends = friendshipService.getAllFriendsDTO("a@dal.ca");
        assertEquals(1, friends.size());
        assertEquals("c@dal.ca", friends.get(0).email());
    }

    @Test
    public void getAllFriendsDTO_userHasNoFriends() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(friendshipRepository.findBySenderOrReceiver(userA, userA)).thenReturn(List.of());
        List<UserProfileDTOFriendship> friends = friendshipService.getAllFriendsDTO("a@dal.ca");
        assertTrue(friends.isEmpty());
    }

    /**
     * Test case for deleteFriendshipByEmail
     */
    @Test
    public void deleteFriendshipByEmail_senderNotFound() {
        when(userProfileRepository.findUserProfileByEmail("nonexistent@dal.ca")).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> friendshipService.deleteFriendshipByEmail("nonexistent@dal.ca", "b@dal.ca")
        );
        assertEquals("Sender not found", exception.getMessage());
    }

    @Test
    public void deleteFriendshipByEmail_receiverNotFound() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("nonexistent@dal.ca")).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> friendshipService.deleteFriendshipByEmail("a@dal.ca", "nonexistent@dal.ca")
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
