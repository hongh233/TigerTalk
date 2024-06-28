package com.group2.Tiger_Talks.backend.service.implementation;

import com.group2.Tiger_Talks.backend.model.Socials.Friendship;
import com.group2.Tiger_Talks.backend.model.Socials.FriendshipDTO;
import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Socials.FriendshipRepository;
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
public class FriendshipServiceImplTest {

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private FriendshipServiceImpl friendshipService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllFriends_UserNotFound() {
        String email = "user@example.com";
        when(userProfileRepository.findUserProfileByEmail(email)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            friendshipService.getAllFriends(email);
        });
        assertEquals("User not found", exception.getMessage(), "Expected exception message not found");
    }

    @Test
    public void testGetAllFriends_Success() {
        String email = "user@example.com";
        UserProfile user = new UserProfile();
        user.setEmail(email);
        when(userProfileRepository.findUserProfileByEmail(email)).thenReturn(Optional.of(user));

        List<Friendship> friendships = new ArrayList<>();
        Friendship friendship = new Friendship();
        friendship.setSender(user);
        friendship.setReceiver(user);
        friendships.add(friendship);
        when(friendshipRepository.findBySenderOrReceiver(user, user)).thenReturn(friendships);

        List<FriendshipDTO> result = friendshipService.getAllFriends(email);

        assertEquals(1, result.size(), "Expected one friendship");
        verify(friendshipRepository, times(1)).findBySenderOrReceiver(user, user);
    }

    @Test
    public void testDeleteFriendshipByEmail_Success() {
        String senderEmail = "sender@example.com";
        String receiverEmail = "receiver@example.com";
        UserProfile sender = new UserProfile();
        sender.setEmail(senderEmail);
        UserProfile receiver = new UserProfile();
        receiver.setEmail(receiverEmail);

        when(userProfileRepository.findUserProfileByEmail(senderEmail)).thenReturn(Optional.of(sender));
        when(userProfileRepository.findUserProfileByEmail(receiverEmail)).thenReturn(Optional.of(receiver));

        Friendship friendship = new Friendship();
        friendship.setSender(sender);
        friendship.setReceiver(receiver);
        when(friendshipRepository.findBySenderAndReceiverOrReceiverAndSender(sender, receiver, receiver, sender))
                .thenReturn(Optional.of(friendship));

        Optional<String> result = friendshipService.deleteFriendshipByEmail(senderEmail, receiverEmail);

        assertFalse(result.isPresent(), "Expected no error message");
        verify(friendshipRepository, times(1)).delete(friendship);
    }

    @Test
    public void testDeleteFriendshipByEmail_NoFriendship() {
        String senderEmail = "sender@example.com";
        String receiverEmail = "receiver@example.com";
        UserProfile sender = new UserProfile();
        sender.setEmail(senderEmail);
        UserProfile receiver = new UserProfile();
        receiver.setEmail(receiverEmail);

        when(userProfileRepository.findUserProfileByEmail(senderEmail)).thenReturn(Optional.of(sender));
        when(userProfileRepository.findUserProfileByEmail(receiverEmail)).thenReturn(Optional.of(receiver));

        when(friendshipRepository.findBySenderAndReceiverOrReceiverAndSender(sender, receiver, receiver, sender))
                .thenReturn(Optional.empty());

        Optional<String> result = friendshipService.deleteFriendshipByEmail(senderEmail, receiverEmail);

        assertTrue(result.isPresent(), "Expected an error message");
        assertEquals("No friendship exists between these users.", result.get(), "Unexpected error message");
        verify(friendshipRepository, never()).delete(any(Friendship.class));
    }

    @Test
    public void testDeleteFriendshipById_Success() {
        int friendshipId = 1;
        Friendship friendship = new Friendship();
        friendship.setFriendshipId(friendshipId);

        when(friendshipRepository.findById(friendshipId)).thenReturn(Optional.of(friendship));

        Optional<String> result = friendshipService.deleteFriendshipById(friendshipId);

        assertFalse(result.isPresent(), "Expected no error message");
        verify(friendshipRepository, times(1)).delete(friendship);
    }

    @Test
    public void testDeleteFriendshipById_NotFound() {
        int friendshipId = 1;

        when(friendshipRepository.findById(friendshipId)).thenReturn(Optional.empty());

        Optional<String> result = friendshipService.deleteFriendshipById(friendshipId);

        assertTrue(result.isPresent(), "Expected an error message");
        assertEquals("Friendship id " + friendshipId + " does not exist!", result.get(), "Unexpected error message");
        verify(friendshipRepository, never()).delete(any(Friendship.class));
    }
}
