package tigertalk.Tiger_Talks.backend.service._implementation.Friend;

import tigertalk.model.Friend.Friendship;
import tigertalk.model.Friend.FriendshipMessage;
import tigertalk.model.Friend.FriendshipMessageDTO;
import tigertalk.model.User.UserProfile;
import tigertalk.repository.Friend.FriendshipMessageRepository;
import tigertalk.repository.Friend.FriendshipRepository;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service._implementation.Friend.FriendshipMessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
        reset(friendshipMessageRepository, friendshipRepository, userProfileRepository);
    }


    private Friendship mockFriendship(int id) {
        Friendship mockFriendship = mock(Friendship.class);
        when(mockFriendship.getFriendshipId()).thenReturn(id);
        return mockFriendship;
    }

    /**
     * Test case for createMessage
     */
    @Test
    public void createMessage_friendshipNotFound() {
        FriendshipMessage message = new FriendshipMessage();
        message.setFriendship(mockFriendship(1));
        when(friendshipRepository.findById(1)).thenReturn(Optional.empty());

        Optional<String> result = friendshipMessageService.createMessage(message);
        assertEquals("Friendship not found", result.get());
    }

    @Test
    public void createMessage_senderNotFound() {
        FriendshipMessage message = new FriendshipMessage();
        message.setFriendship(mockFriendship(1));
        UserProfile sender = new UserProfile();
        sender.setEmail("a@dal.ca");
        message.setSender(sender);

        when(friendshipRepository.findById(1)).thenReturn(Optional.of(new Friendship()));
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.empty());

        Optional<String> result = friendshipMessageService.createMessage(message);
        assertEquals("Sender not found", result.get());
    }

    @Test
    public void createMessage_receiverNotFound() {
        FriendshipMessage message = new FriendshipMessage();
        message.setFriendship(mockFriendship(1));
        UserProfile sender = new UserProfile();
        sender.setEmail("sender@dal.ca");
        message.setSender(sender);
        UserProfile receiver = new UserProfile();
        receiver.setEmail("receiver@dal.ca");
        message.setReceiver(receiver);

        when(friendshipRepository.findById(1)).thenReturn(Optional.of(new Friendship()));
        when(userProfileRepository.findById("sender@dal.ca")).thenReturn(Optional.of(sender));
        when(userProfileRepository.findById(sender.getEmail())).thenReturn(Optional.of(sender));
        when(userProfileRepository.findById("receiver@dal.ca")).thenReturn(Optional.empty());
        when(userProfileRepository.findById("receiver@dal.ca")).thenReturn(Optional.empty());

        Optional<String> result = friendshipMessageService.createMessage(message);
        assertEquals("Receiver not found", result.get());
    }

    @Test
    public void createMessage_success() {
        FriendshipMessage message = new FriendshipMessage();
        message.setFriendship(mockFriendship(1));
        UserProfile sender = new UserProfile();
        sender.setEmail("sender@dal.ca");
        message.setSender(sender);
        UserProfile receiver = new UserProfile();
        receiver.setEmail("receiver@dal.ca");
        message.setReceiver(receiver);

        when(friendshipRepository.findById(1)).thenReturn(Optional.of(new Friendship()));
        when(userProfileRepository.findById("sender@dal.ca")).thenReturn(Optional.of(sender));
        when(userProfileRepository.findById("receiver@dal.ca")).thenReturn(Optional.of(receiver));
        when(userProfileRepository.findById(sender.getEmail())).thenReturn(Optional.of(sender));
        when(userProfileRepository.findById(receiver.getEmail())).thenReturn(Optional.of(receiver));

        Optional<String> result = friendshipMessageService.createMessage(message);
        assertTrue(result.isEmpty());
    }

    /**
     * Test case for getAllMessagesByFriendshipId
     */
    @Test
    public void getAllMessagesByFriendshipId_noMessages() {
        int friendshipId = 1;
        when(friendshipMessageRepository.findByFriendship_FriendshipId(friendshipId)).thenReturn(new LinkedList<>());
        List<FriendshipMessageDTO> result = friendshipMessageService.getAllMessagesByFriendshipId(friendshipId);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllMessagesByFriendshipId_twoMessages() {
        int friendshipId = 1;
        Friendship mockFriendship = mockFriendship(friendshipId);

        UserProfile sender = new UserProfile();
        sender.setEmail("sender@@dal.ca");
        sender.setUserName("SenderName");
        sender.setProfilePictureUrl("senderPicUrl");

        UserProfile receiver = new UserProfile();
        receiver.setEmail("receiver@@dal.ca");
        receiver.setUserName("ReceiverName");
        receiver.setProfilePictureUrl("receiverPicUrl");

        FriendshipMessage message1 = new FriendshipMessage();
        message1.setMessageId(1);
        message1.setMessageContent("Hello");
        message1.setFriendship(mockFriendship);
        message1.setSender(sender);
        message1.setReceiver(receiver);
        message1.setCreateTime(LocalDateTime.now());
        message1.setRead(false);

        FriendshipMessage message2 = new FriendshipMessage();
        message2.setMessageId(2);
        message2.setMessageContent("Hi");
        message2.setFriendship(mockFriendship);
        message2.setSender(sender);
        message2.setReceiver(receiver);
        message2.setCreateTime(LocalDateTime.now());
        message2.setRead(true);

        List<FriendshipMessage> messages = List.of(message1, message2);

        when(friendshipMessageRepository.findByFriendship_FriendshipId(friendshipId))
                .thenReturn(messages);

        List<FriendshipMessageDTO> actualDTOs = friendshipMessageService.getAllMessagesByFriendshipId(friendshipId);

        List<FriendshipMessageDTO> expectedDTOs = messages.stream()
                .map(FriendshipMessage::toDto)
                .toList();

        assertEquals(expectedDTOs.size(), actualDTOs.size());
        for (int i = 0; i < expectedDTOs.size(); i++) {
            assertEquals(expectedDTOs.get(i).messageId(), actualDTOs.get(i).messageId());
            assertEquals(expectedDTOs.get(i).messageContent(), actualDTOs.get(i).messageContent());
        }
    }

    /**
     * Test case for markMessageAsRead
     */
    @Test
    public void markMessageAsRead_messageNotFound() {
        int messageId = 1;
        when(friendshipMessageRepository.findById(messageId)).thenReturn(Optional.empty());
        Optional<String> result = friendshipMessageService.markMessageAsRead(messageId);
        assertEquals("Message not found!", result.get());
    }

    @Test
    public void markMessageAsRead_messageAlreadyRead() {
        int messageId = 1;
        FriendshipMessage message = new FriendshipMessage();
        message.setMessageId(messageId);
        message.setRead(true);
        when(friendshipMessageRepository.findById(messageId)).thenReturn(Optional.of(message));
        Optional<String> result = friendshipMessageService.markMessageAsRead(messageId);
        assertEquals("Message has already been read!", result.get());
    }

    @Test
    public void markMessageAsRead_success() {
        int messageId = 1;
        FriendshipMessage message = new FriendshipMessage();
        message.setMessageId(messageId);
        message.setRead(false);
        when(friendshipMessageRepository.findById(messageId)).thenReturn(Optional.of(message));
        Optional<String> result = friendshipMessageService.markMessageAsRead(messageId);
        assertTrue(result.isEmpty());
        assertTrue(message.isRead());
    }

    /**
     * Test case for getFriendshipMessageDTOById
     */
    @Test
    public void getFriendshipMessageDTOById_messageNotFound() {
        int messageId = 1;
        when(friendshipMessageRepository.findById(messageId)).thenReturn(Optional.empty());
        FriendshipMessageDTO result = friendshipMessageService.getFriendshipMessageDTOById(messageId);
        assertNull(result);
    }


}
