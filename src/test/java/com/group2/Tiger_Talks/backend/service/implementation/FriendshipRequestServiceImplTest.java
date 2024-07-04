package com.group2.Tiger_Talks.backend.service.implementation;

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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class FriendshipRequestServiceImplTest {

    @Mock
    private FriendshipRequestRepository friendshipRequestRepository;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private FriendshipRequestServiceImpl friendshipRequestService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllFriendRequestsSuccess() {
        String userEmail = "user@dal.ca";
        UserProfile user = new UserProfile();
        user.setEmail(userEmail);

        List<FriendshipRequest> requests = new ArrayList<>();
        FriendshipRequest request1 = new FriendshipRequest();
        request1.setFriendshipRequestId(1);
        request1.setSender(new UserProfile());
        request1.setReceiver(user);
        requests.add(request1);

        when(userProfileRepository.findUserProfileByEmail(userEmail)).thenReturn(Optional.of(user));
        when(friendshipRequestRepository.findByReceiver(user)).thenReturn(requests);

        List<FriendshipRequestDTO> result = friendshipRequestService.getAllFriendRequests(userEmail);

        assertEquals(1, result.size());
        assertEquals(request1.getFriendshipRequestId(), result.get(0).getId());
    }

    @Test
    public void testGetAllFriendRequestsUserNotFound() {
        String userEmail = "notfound@dal.ca";

        when(userProfileRepository.findUserProfileByEmail(userEmail)).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            friendshipRequestService.getAllFriendRequests(userEmail);
        });
        assertEquals("User not found", exception.getMessage());
        verify(friendshipRequestRepository, never()).findByReceiver(any());
    }

    @Test
    public void testSendFriendshipRequestSuccess() {
        String senderEmail = "sender@dal.ca";
        String receiverEmail = "receiver@dal.ca";

        UserProfile sender = new UserProfile();
        sender.setEmail(senderEmail);
        UserProfile receiver = new UserProfile();
        receiver.setEmail(receiverEmail);

        when(userProfileRepository.findUserProfileByEmail(senderEmail)).thenReturn(Optional.of(sender));
        when(userProfileRepository.findUserProfileByEmail(receiverEmail)).thenReturn(Optional.of(receiver));
        when(friendshipRepository.findBySenderAndReceiver(sender, receiver)).thenReturn(Optional.empty());
        when(friendshipRepository.findBySenderAndReceiver(receiver, sender)).thenReturn(Optional.empty());
        when(friendshipRequestRepository.findBySenderAndReceiver(sender, receiver)).thenReturn(Optional.empty());
        when(friendshipRequestRepository.findBySenderAndReceiver(receiver, sender)).thenReturn(Optional.empty());


        Optional<String> result = friendshipRequestService.sendFriendshipRequest(senderEmail, receiverEmail);

        assertEquals(Optional.empty(), result);
        verify(friendshipRequestRepository, times(1)).save(any(FriendshipRequest.class));
    }


    @Test
    public void testSendFriendshipRequestSenderNotFound() {
        String senderEmail = "sender@dal.ca";
        String receiverEmail = "receiver@dal.ca";

        when(userProfileRepository.findUserProfileByEmail(senderEmail)).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            friendshipRequestService.sendFriendshipRequest(senderEmail, receiverEmail);
        });

        assertEquals("Sender not found", exception.getMessage());
        verify(userProfileRepository, times(1)).findUserProfileByEmail(senderEmail);
        verify(userProfileRepository, never()).findUserProfileByEmail(receiverEmail);
        verify(friendshipRepository, never()).findBySenderAndReceiver(any(), any());
        verify(friendshipRequestRepository, never()).findBySenderAndReceiver(any(), any());
        verify(friendshipRequestRepository, never()).save(any());
    }

    @Test
    public void testSendFriendshipRequestReceiverNotFound() {
        String senderEmail = "sender@dal.ca";
        String receiverEmail = "receiver@dal.ca";

        when(userProfileRepository.findUserProfileByEmail(senderEmail)).thenReturn(Optional.of(new UserProfile()));
        when(userProfileRepository.findUserProfileByEmail(receiverEmail)).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            friendshipRequestService.sendFriendshipRequest(senderEmail, receiverEmail);
        });

        assertEquals("Receiver not found", exception.getMessage());
        verify(userProfileRepository, times(1)).findUserProfileByEmail(senderEmail);
        verify(userProfileRepository, times(1)).findUserProfileByEmail(receiverEmail);
        verify(friendshipRepository, never()).findBySenderAndReceiver(any(), any());
        verify(friendshipRequestRepository, never()).findBySenderAndReceiver(any(), any());
        verify(friendshipRequestRepository, never()).save(any());
    }

    @Test
    public void testSendFriendshipRequest_FriendshipAlreadyExists() {
        String senderEmail = "sender@dal.ca";
        String receiverEmail = "receiver@dal.ca";

        UserProfile sender = new UserProfile();
        sender.setEmail(senderEmail);
        UserProfile receiver = new UserProfile();
        receiver.setEmail(receiverEmail);

        when(userProfileRepository.findUserProfileByEmail(senderEmail)).thenReturn(Optional.of(sender));
        when(userProfileRepository.findUserProfileByEmail(receiverEmail)).thenReturn(Optional.of(receiver));
        when(friendshipRepository.findBySenderAndReceiver(sender, receiver)).thenReturn(Optional.of(new Friendship()));

        Optional<String> result = friendshipRequestService.sendFriendshipRequest(senderEmail, receiverEmail);

        assertTrue(result.isPresent());
        assertEquals("Friendship has already existed between these users.", result.get());
        verify(friendshipRequestRepository, never()).save(any());
    }

    @Test
    public void testSendFriendshipRequestFriendshipRequestAlreadyExists() {
        String senderEmail = "sender@dal.ca";
        String receiverEmail = "receiver@dal.ca";

        UserProfile sender = new UserProfile();
        sender.setEmail(senderEmail);
        UserProfile receiver = new UserProfile();
        receiver.setEmail(receiverEmail);

        when(userProfileRepository.findUserProfileByEmail(senderEmail)).thenReturn(Optional.of(sender));
        when(userProfileRepository.findUserProfileByEmail(receiverEmail)).thenReturn(Optional.of(receiver));
        when(friendshipRepository.findBySenderAndReceiver(sender, receiver)).thenReturn(Optional.empty());
        when(friendshipRepository.findBySenderAndReceiver(receiver, sender)).thenReturn(Optional.empty());
        when(friendshipRequestRepository.findBySenderAndReceiver(sender, receiver)).thenReturn(Optional.of(new FriendshipRequest()));

        Optional<String> result = friendshipRequestService.sendFriendshipRequest(senderEmail, receiverEmail);

        assertTrue(result.isPresent());
        assertEquals("Friendship request has already existed between these users.", result.get());
        verify(friendshipRequestRepository, never()).save(any());
    }

    @Test
    public void testAcceptFriendshipRequestSuccess() {
        Integer friendshipRequestId = 1;
        FriendshipRequest friendshipRequest = new FriendshipRequest();
        friendshipRequest.setFriendshipRequestId(friendshipRequestId);
        friendshipRequest.setSender(new UserProfile());
        friendshipRequest.setReceiver(new UserProfile());

        when(friendshipRequestRepository.findById(friendshipRequestId)).thenReturn(Optional.of(friendshipRequest));

        Optional<String> result = friendshipRequestService.acceptFriendshipRequest(friendshipRequestId);

        assertEquals(Optional.empty(), result);
        verify(friendshipRepository, times(1)).save(any(Friendship.class));
        verify(friendshipRequestRepository, times(1)).delete(friendshipRequest);
    }

    @Test
    public void testAcceptFriendshipRequestFriendshipRequestNotFound() {
        Integer friendshipRequestId = 1;
        when(friendshipRequestRepository.findById(friendshipRequestId)).thenReturn(Optional.empty());
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            friendshipRequestService.acceptFriendshipRequest(friendshipRequestId);
        });
        assertEquals("friendship request ID does not exist!", exception.getMessage());
        verify(friendshipRepository, never()).save(any());
        verify(friendshipRequestRepository, never()).delete(any());
    }

    @Test
    public void testRejectFriendshipRequestSuccess() {
        Integer friendshipRequestId = 1;
        FriendshipRequest friendshipRequest = new FriendshipRequest();
        friendshipRequest.setFriendshipRequestId(friendshipRequestId);
        friendshipRequest.setSender(new UserProfile());
        friendshipRequest.setReceiver(new UserProfile());

        when(friendshipRequestRepository.findById(friendshipRequestId)).thenReturn(Optional.of(friendshipRequest));
        Optional<String> result = friendshipRequestService.rejectFriendshipRequest(friendshipRequestId);

        assertEquals(Optional.empty(), result);
        verify(friendshipRequestRepository, times(1)).delete(friendshipRequest);
    }

    @Test
    public void testRejectFriendshipRequestFriendshipRequestNotFound() {
        Integer friendshipRequestId = 1;

        when(friendshipRequestRepository.findById(friendshipRequestId)).thenReturn(Optional.empty());
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            friendshipRequestService.rejectFriendshipRequest(friendshipRequestId);
        });

        assertEquals("friendship request ID does not exist!", exception.getMessage());
        verify(friendshipRequestRepository, never()).delete(any());
    }

}