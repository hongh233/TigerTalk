package tigertalk.Tiger_Talks.backend.service._implementation.Friend;

import tigertalk.model.Friend.Friendship;
import tigertalk.model.Friend.FriendshipRequest;
import tigertalk.model.Friend.FriendshipRequestDTO;
import tigertalk.model.Notification.Notification;
import tigertalk.model.User.UserProfile;
import tigertalk.repository.Friend.FriendshipRepository;
import tigertalk.repository.Friend.FriendshipRequestRepository;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.Notification.NotificationService;
import tigertalk.service._implementation.Friend.FriendshipRequestServiceImpl;
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
import static org.mockito.Mockito.*;

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
        userA = new UserProfile();
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
        userB.setPassword("aaaa1A@a");
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
    }

    /**
     * Test case for getAllFriendRequests
     */
    @Test
    public void getAllFriendRequests_userNotFound() {
        when(userProfileRepository.findById("nonexistent@dal.ca")).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> friendshipRequestService.getAllFriendRequests("nonexistent@dal.ca")
        );
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void getAllFriendRequests_success() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(friendshipRequestRepository.findByReceiver(userA)).thenReturn(List.of(new FriendshipRequest(userB, userA), new FriendshipRequest(userC, userA)));
        List<FriendshipRequestDTO> requests = friendshipRequestService.getAllFriendRequests("a@dal.ca");
        assertEquals(2, requests.size());
    }

    /**
     * Test case for sendFriendshipRequest
     */
    @Test
    public void sendFriendshipRequest_senderNotFound() {
        when(userProfileRepository.findById("nonexistent@dal.ca")).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> friendshipRequestService.sendFriendshipRequest("nonexistent@dal.ca", "b@dal.ca")
        );
        assertEquals("Sender not found", exception.getMessage());
    }

    @Test
    public void sendFriendshipRequest_receiverNotFound() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findById("nonexistent@dal.ca")).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> friendshipRequestService.sendFriendshipRequest("a@dal.ca", "nonexistent@dal.ca")
        );
        assertEquals("Receiver not found", exception.getMessage());
    }

    @Test
    public void sendFriendshipRequest_existingFriendship_A_to_B() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.of(new Friendship()));
        Optional<String> result = friendshipRequestService.sendFriendshipRequest("a@dal.ca", "b@dal.ca");
        assertTrue(result.isPresent());
        assertEquals("Friendship has already existed between these users.", result.get());
    }

    @Test
    public void sendFriendshipRequest_existingFriendship_B_to_A() {
        lenient().when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        lenient().when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
        lenient().when(friendshipRepository.findBySenderAndReceiver(userB, userA)).thenReturn(Optional.of(new Friendship()));
        Optional<String> result = friendshipRequestService.sendFriendshipRequest("a@dal.ca", "b@dal.ca");
        assertTrue(result.isPresent());
        assertEquals("Friendship has already existed between these users.", result.get());
    }

    @Test
    public void sendFriendshipRequest_existingRequest_A_to_B() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.empty());
        when(friendshipRequestRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.of(new FriendshipRequest(userA, userB)));
        Optional<String> result = friendshipRequestService.sendFriendshipRequest("a@dal.ca", "b@dal.ca");
        assertTrue(result.isPresent());
        assertEquals("Friendship request has already existed between these users.", result.get());
    }

    @Test
    public void sendFriendshipRequest_existingRequest_B_to_A() {
        lenient().when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        lenient().when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
        lenient().when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.empty());
        lenient().when(friendshipRequestRepository.findBySenderAndReceiver(userB, userA)).thenReturn(Optional.of(new FriendshipRequest(userB, userA)));
        Optional<String> result = friendshipRequestService.sendFriendshipRequest("a@dal.ca", "b@dal.ca");
        assertTrue(result.isPresent());
        assertEquals("Friendship request has already existed between these users.", result.get());
    }

    @Test
    public void sendFriendshipRequest_success() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.empty());
        when(friendshipRequestRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.empty());
        Optional<String> result = friendshipRequestService.sendFriendshipRequest("a@dal.ca", "b@dal.ca");
        assertTrue(result.isEmpty());
    }

    @Test
    void sendFriendshipRequest_successWithNotification() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
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
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
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
        assertEquals("Friendship request ID does not exist!", exception.getMessage());
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
        when(userProfileRepository.findById("nonexistent@dal.ca")).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> friendshipRequestService.areFriendshipRequestExist("nonexistent@dal.ca", "a@dal.ca")
        );
        assertEquals("Sender not found", exception.getMessage());
    }

    @Test
    public void areFriendshipRequestExist_areFriends_returnsTrue() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRequestRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.of(new FriendshipRequest()));

        boolean result = friendshipRequestService.areFriendshipRequestExist("a@dal.ca", "b@dal.ca");
        assertTrue(result, "Expected true as a and b are friends");
    }

    @Test
    public void areFriendshipRequestExist_notFriends_returnsFalse() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRequestRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.empty());
        when(friendshipRequestRepository.findBySenderAndReceiver(userB, userA)).thenReturn(Optional.empty());

        boolean result = friendshipRequestService.areFriendshipRequestExist("a@dal.ca", "b@dal.ca");
        assertFalse(result, "Expected false as there is no friendship between a and b");
    }

    /**
     * Test case for findNumOfTotalRequests
     */
    @Test
    public void findNumOfTotalRequests_success() {
        when(friendshipRequestRepository.findAll()).thenReturn(List.of(new FriendshipRequest(userA, userB), new FriendshipRequest(userB, userC)));
        int result = friendshipRequestService.findNumOfTotalRequests();
        assertEquals(2, result);
    }

    @Test
    public void findNumOfTotalRequests_noRequests() {
        when(friendshipRequestRepository.findAll()).thenReturn(List.of());
        int result = friendshipRequestService.findNumOfTotalRequests();
        assertEquals(0, result);
    }


}
