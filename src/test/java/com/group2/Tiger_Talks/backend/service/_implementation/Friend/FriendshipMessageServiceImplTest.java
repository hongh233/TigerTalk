package com.group2.Tiger_Talks.backend.service._implementation.Friend;

import com.group2.Tiger_Talks.backend.model.Friend.Friendship;
import com.group2.Tiger_Talks.backend.model.Friend.FriendshipMessage;
import com.group2.Tiger_Talks.backend.model.Friend.FriendshipMessageDTO;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Friend.FriendshipMessageRepository;
import com.group2.Tiger_Talks.backend.repository.Friend.FriendshipRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FriendshipMessageServiceImplTest {

    @Mock
    private FriendshipMessageRepository friendshipMessageRepository;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private FriendshipMessageServiceImpl friendshipMessageService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for createMessage
     */
    @Test
    public void createMessage_friendshipNotFound() {
        FriendshipMessage message = new FriendshipMessage();
        Friendship friendship = new Friendship();
        friendship.setFriendshipId(1);
        message.setFriendship(friendship);
        when(friendshipRepository.findById(1)).thenReturn(Optional.empty());

        Optional<String> result = friendshipMessageService.createMessage(message);
        assertEquals("Friendship not found", result.get());
    }

    @Test
    public void createMessage_senderNotFound() {
        FriendshipMessage message = new FriendshipMessage();
        Friendship friendship = new Friendship();
        friendship.setFriendshipId(1);
        message.setFriendship(friendship);
        UserProfile sender = new UserProfile();
        sender.setEmail("sender@example.com");
        message.setSender(sender);

        when(friendshipRepository.findById(1)).thenReturn(Optional.of(friendship));
        when(userProfileRepository.findById("sender@example.com")).thenReturn(Optional.empty());

        Optional<String> result = friendshipMessageService.createMessage(message);
        assertEquals("Sender not found", result.get());
    }

    @Test
    public void createMessage_receiverNotFound() {
        FriendshipMessage message = new FriendshipMessage();
        Friendship friendship = new Friendship();
        friendship.setFriendshipId(1);
        message.setFriendship(friendship);
        UserProfile sender = new UserProfile();
        sender.setEmail("sender@example.com");
        message.setSender(sender);
        UserProfile receiver = new UserProfile();
        receiver.setEmail("receiver@example.com");
        message.setReceiver(receiver);

        when(friendshipRepository.findById(1)).thenReturn(Optional.of(friendship));
        when(userProfileRepository.findById("sender@example.com")).thenReturn(Optional.of(sender));
        when(userProfileRepository.findById("receiver@example.com")).thenReturn(Optional.empty());

        Optional<String> result = friendshipMessageService.createMessage(message);
        assertEquals("Receiver not found", result.get());
    }

    @Test
    public void createMessage_success() {
        FriendshipMessage message = new FriendshipMessage();
        Friendship friendship = new Friendship();
        friendship.setFriendshipId(1);
        message.setFriendship(friendship);
        UserProfile sender = new UserProfile();
        sender.setEmail("sender@example.com");
        message.setSender(sender);
        UserProfile receiver = new UserProfile();
        receiver.setEmail("receiver@example.com");
        message.setReceiver(receiver);

        when(friendshipRepository.findById(1)).thenReturn(Optional.of(friendship));
        when(userProfileRepository.findById("sender@example.com")).thenReturn(Optional.of(sender));
        when(userProfileRepository.findById("receiver@example.com")).thenReturn(Optional.of(receiver));

        Optional<String> result = friendshipMessageService.createMessage(message);
        assertEquals(Optional.empty(), result);
    }

    /**
     * Test case for getAllMessagesByFriendshipId
     */
    @Test
    public void getAllMessagesByFriendshipId_noMessages() {
        int friendshipId = 1;
        when(friendshipMessageRepository.findByFriendship_FriendshipId(friendshipId)).thenReturn(new LinkedList<>());
        List<FriendshipMessageDTO> result = friendshipMessageService.getAllMessagesByFriendshipId(friendshipId);
        assertEquals(0, result.size());
    }

    @Test
    public void getAllMessagesByFriendshipId_twoMessages() {
        int friendshipId = 1;
        Friendship friendship = new Friendship();
        friendship.setFriendshipId(friendshipId);

        UserProfile sender = new UserProfile();
        sender.setEmail("sender@example.com");

        UserProfile receiver = new UserProfile();
        receiver.setEmail("receiver@example.com");

        FriendshipMessage message1 = new FriendshipMessage();
        message1.setMessageId(1);
        message1.setMessageContent("Hello");
        message1.setFriendship(friendship);
        message1.setSender(sender);
        message1.setReceiver(receiver);

        FriendshipMessage message2 = new FriendshipMessage();
        message2.setMessageId(2);
        message2.setMessageContent("Hi");
        message2.setFriendship(friendship);
        message2.setSender(sender);
        message2.setReceiver(receiver);

        List<FriendshipMessage> messages = List.of(message1, message2);

        when(friendshipMessageRepository.findByFriendship_FriendshipId(friendshipId)).thenReturn(messages);

        List<FriendshipMessageDTO> expected = messages.stream()
                .map(FriendshipMessage::toDto)
                .collect(Collectors.toList());

        List<FriendshipMessageDTO> result = friendshipMessageService.getAllMessagesByFriendshipId(friendshipId);

        assertEquals(expected, result);
    }

    @Test
    public void getAllMessagesByFriendshipId_singleMessage() {
        int friendshipId = 1;
        Friendship friendship = new Friendship();
        friendship.setFriendshipId(friendshipId);

        UserProfile sender = new UserProfile();
        sender.setEmail("sender@example.com");

        UserProfile receiver = new UserProfile();
        receiver.setEmail("receiver@example.com");

        FriendshipMessage message = new FriendshipMessage();
        message.setMessageId(1);
        message.setMessageContent("Hello");
        message.setFriendship(friendship);
        message.setSender(sender);
        message.setReceiver(receiver);

        List<FriendshipMessage> messages = List.of(message);

        when(friendshipMessageRepository.findByFriendship_FriendshipId(friendshipId)).thenReturn(messages);

        List<FriendshipMessageDTO> expected = messages.stream()
                .map(FriendshipMessage::toDto)
                .collect(Collectors.toList());

        List<FriendshipMessageDTO> result = friendshipMessageService.getAllMessagesByFriendshipId(friendshipId);

        assertEquals(expected, result);
    }

    /**
     * Test case for markMessageAsRead
     */
    @Test
    public void testMarkMessageAsRead_MessageNotFound() {
        int messageId = 1;
        when(friendshipMessageRepository.findById(messageId)).thenReturn(Optional.empty());
        Optional<String> result = friendshipMessageService.markMessageAsRead(messageId);
        assertEquals("Message not found!", result.get());
    }

    @Test
    public void testMarkMessageAsRead_MessageAlreadyRead() {
        int messageId = 1;
        FriendshipMessage message = new FriendshipMessage();
        message.setMessageId(messageId);
        message.setRead(true);
        when(friendshipMessageRepository.findById(messageId)).thenReturn(Optional.of(message));
        Optional<String> result = friendshipMessageService.markMessageAsRead(messageId);
        assertEquals("Message has already been read!", result.get());
    }

    @Test
    public void testMarkMessageAsRead_Success() {
        int messageId = 1;
        FriendshipMessage message = new FriendshipMessage();
        message.setMessageId(messageId);
        message.setRead(false);
        when(friendshipMessageRepository.findById(messageId)).thenReturn(Optional.of(message));
        Optional<String> result = friendshipMessageService.markMessageAsRead(messageId);
        assertEquals(Optional.empty(), result);
        assertTrue(message.isRead());
    }
}