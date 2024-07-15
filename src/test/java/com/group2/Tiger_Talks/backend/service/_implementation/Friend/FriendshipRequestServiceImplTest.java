package com.group2.Tiger_Talks.backend.service._implementation.Friend;

import com.group2.Tiger_Talks.backend.model.Friend.Friendship;
import com.group2.Tiger_Talks.backend.model.Friend.FriendshipRequest;
import com.group2.Tiger_Talks.backend.model.Friend.FriendshipRequestDTO;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Socials.FriendshipRepository;
import com.group2.Tiger_Talks.backend.repository.Socials.FriendshipRequestRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class FriendshipRequestServiceImplTest {

    @Mock
    private FriendshipRequestRepository friendshipRequestRepository;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private FriendshipRequestServiceImpl friendshipRequestService;

    private UserProfile userA;
    private UserProfile userB;
    private UserProfile userC;

    @BeforeEach
    void setUp() {
        userA = new UserProfile("Along", "Aside", 22, "Male", "userA", "a@dal.ca", "aaaa1A@a", new String[]{"1", "2", "3"}, new String[]{"What was your favourite book as a child?", "In what city were you born?", "What is the name of the hospital where you were born?"});
        userB = new UserProfile("Blong", "Bside", 23, "Female", "userB", "b@dal.ca", "bbbb1B@b", new String[]{"1", "2", "3"}, new String[]{"What was your favourite book as a child?", "In what city were you born?", "What is the name of the hospital where you were born?"});
        userC = new UserProfile("Clong", "Cside", 24, "Male", "userC", "c@dal.ca", "cccc1C@c", new String[]{"1", "2", "3"}, new String[]{"What was your favourite book as a child?", "In what city were you born?", "What is the name of the hospital where you were born?"});
    }

    @Test
    void getAllFriendRequests_userNotFound() {
        when(userProfileRepository.findUserProfileByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            friendshipRequestService.getAllFriendRequests("nonexistent@example.com");
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void getAllFriendRequests_success() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(friendshipRequestRepository.findByReceiver(userA)).thenReturn(List.of(new FriendshipRequest(userB, userA), new FriendshipRequest(userC, userA)));

        List<FriendshipRequestDTO> requests = friendshipRequestService.getAllFriendRequests("a@dal.ca");

        assertEquals(2, requests.size());
    }

    @Test
    void sendFriendshipRequest_senderNotFound() {
        when(userProfileRepository.findUserProfileByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            friendshipRequestService.sendFriendshipRequest("nonexistent@example.com", "b@dal.ca");
        });

        assertEquals("Sender not found", exception.getMessage());
    }

    @Test
    void sendFriendshipRequest_receiverNotFound() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            friendshipRequestService.sendFriendshipRequest("a@dal.ca", "nonexistent@example.com");
        });

        assertEquals("Receiver not found", exception.getMessage());
    }

    @Test
    void sendFriendshipRequest_existingFriendship() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.of(new Friendship()));

        Optional<String> result = friendshipRequestService.sendFriendshipRequest("a@dal.ca", "b@dal.ca");

        assertTrue(result.isPresent());
        assertEquals("Friendship has already existed between these users.", result.get());
    }

    @Test
    void sendFriendshipRequest_existingRequest() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.empty());
        when(friendshipRequestRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.of(new FriendshipRequest(userA, userB)));

        Optional<String> result = friendshipRequestService.sendFriendshipRequest("a@dal.ca", "b@dal.ca");

        assertTrue(result.isPresent());
        assertEquals("Friendship request has already existed between these users.", result.get());
    }

    @Test
    void sendFriendshipRequest_success() {
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("b@dal.ca")).thenReturn(Optional.of(userB));
        when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.empty());
        when(friendshipRequestRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.empty());

        Optional<String> result = friendshipRequestService.sendFriendshipRequest("a@dal.ca", "b@dal.ca");

        assertFalse(result.isPresent());
        verify(friendshipRequestRepository, times(1)).save(any(FriendshipRequest.class));
    }

    @Test
    void acceptFriendshipRequest_requestNotFound() {
        when(friendshipRequestRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            friendshipRequestService.acceptFriendshipRequest(1);
        });

        assertEquals("friendship request ID does not exist!", exception.getMessage());
    }

    @Test
    void acceptFriendshipRequest_success() {
        FriendshipRequest request = new FriendshipRequest(userA, userB);
        when(friendshipRequestRepository.findById(1)).thenReturn(Optional.of(request));

        Optional<String> result = friendshipRequestService.acceptFriendshipRequest(1);

        assertFalse(result.isPresent());
        verify(friendshipRepository, times(1)).save(any(Friendship.class));
        verify(friendshipRequestRepository, times(1)).delete(request);
    }

    @Test
    void rejectFriendshipRequest_requestNotFound() {
        when(friendshipRequestRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            friendshipRequestService.rejectFriendshipRequest(1);
        });

        assertEquals("friendship request ID does not exist!", exception.getMessage());
    }

    @Test
    void rejectFriendshipRequest_success() {
        FriendshipRequest request = new FriendshipRequest(userA, userB);
        when(friendshipRequestRepository.findById(1)).thenReturn(Optional.of(request));

        Optional<String> result = friendshipRequestService.rejectFriendshipRequest(1);

        assertFalse(result.isPresent());
        verify(friendshipRequestRepository, times(1)).delete(request);
    }

    @Test
    void findNumOfTotalRequests() {
        when(friendshipRequestRepository.findAll()).thenReturn(List.of(new FriendshipRequest(userA, userB), new FriendshipRequest(userB, userC)));

        int result = friendshipRequestService.findNumOfTotalRequests();

        assertEquals(2, result);
    }

    @Test
    void areFriendshipRequestExist_userNotFound() {
        when(userProfileRepository.findUserProfileByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            friendshipRequestService.areFriendshipRequestExist("nonexistent@example.com", "a@dal.ca");
        });

        assertEquals("Sender not found", exception.getMessage());
    }

    @Test
    void areFriendshipRequestExist_success() {
        /*
        // Set up mock return values for the user profiles
        when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findUserProfileByEmail("b@dal.ca")).thenReturn(Optional.of(userB));

        // Set up mock return values for the friendships (none exist)
        when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.of(new Friendship()));
        when(friendshipRepository.findBySenderAndReceiver(userB, userA)).thenReturn(Optional.empty());
        */

        // Set up mock return values for the user profiles
        lenient().when(userProfileRepository.findUserProfileByEmail("a@dal.ca")).thenReturn(Optional.of(userA));
        lenient().when(userProfileRepository.findUserProfileByEmail("b@dal.ca")).thenReturn(Optional.of(userB));

        // Set up mock return values for the friendships (exist)
        lenient().when(friendshipRepository.findBySenderAndReceiver(userA, userB)).thenReturn(Optional.of(new Friendship()));
        lenient().when(friendshipRepository.findBySenderAndReceiver(userB, userA)).thenReturn(Optional.empty());


        // Call the method under test
        boolean result = friendshipRequestService.areFriendshipRequestExist("a@dal.ca", "b@dal.ca");
        /*
        // Debug information
        System.out.println("User A: " + userA);
        System.out.println("User B: " + userB);
        System.out.println("Friendship Repository (A->B): " + friendshipRepository.findBySenderAndReceiver(userA, userB));
        System.out.println("Friendship Repository (B->A): " + friendshipRepository.findBySenderAndReceiver(userB, userA));
        */
        // Assert the result
        assertTrue(result);
    }
}
