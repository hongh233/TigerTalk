package com.group2.Tiger_Talks.backend.service._implementation.Friend;

import com.group2.Tiger_Talks.backend.model.Friend.FriendshipMessage;
import com.group2.Tiger_Talks.backend.model.Friend.FriendshipMessageDTO;
import com.group2.Tiger_Talks.backend.repository.Friend.FriendshipMessageRepository;
import com.group2.Tiger_Talks.backend.repository.Friend.FriendshipRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Friend.FriendshipMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendshipMessageServiceImpl implements FriendshipMessageService {

    @Autowired
    private FriendshipMessageRepository friendshipMessageRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public Optional<String> createMessage(FriendshipMessage message) {
        if (friendshipRepository.findById(message.getFriendship().getFriendshipId()).isEmpty()) {
            return Optional.of("Friendship not found");
        }
        if (userProfileRepository.findById(message.getSender().email()).isEmpty()) {
            return Optional.of("Sender not found");
        }
        if (userProfileRepository.findById(message.getReceiver().email()).isEmpty()) {
            return Optional.of("Receiver not found");
        }
        friendshipMessageRepository.save(message);
        return Optional.empty();
    }

    @Override
    public List<FriendshipMessageDTO> getAllMessagesByFriendshipId(int friendshipId) {
        return friendshipMessageRepository.findByFriendship_FriendshipId(friendshipId)
                .stream()
                .map(FriendshipMessage::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<String> markMessageAsRead(int messageId) {
        Optional<FriendshipMessage> messageOpt = friendshipMessageRepository.findById(messageId);
        if (messageOpt.isEmpty()) {
            return Optional.of("Message not found!");
        }

        FriendshipMessage message = messageOpt.get();
        if (message.isRead()) {
            return Optional.of("Message has already been read!");
        }

        message.setRead(true);
        friendshipMessageRepository.save(message);
        return Optional.empty();
    }

    @Override
    public FriendshipMessageDTO getFriendshipMessageDTOById(Integer messageId) {
        FriendshipMessage message = friendshipMessageRepository.findById(messageId).orElse(null);
        if (message != null) {
            return message.toDto();
        } else {
            return null;
        }
    }

}
