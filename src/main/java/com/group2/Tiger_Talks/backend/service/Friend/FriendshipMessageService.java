package com.group2.Tiger_Talks.backend.service.Friend;

import com.group2.Tiger_Talks.backend.model.Friend.FriendshipMessage;
import com.group2.Tiger_Talks.backend.model.Friend.FriendshipMessageDTO;

import java.util.List;
import java.util.Optional;

public interface FriendshipMessageService {

    Optional<String> createMessage(FriendshipMessage message);

    List<FriendshipMessageDTO> getAllMessagesByFriendshipId(int friendshipId);

    Optional<String> markMessageAsRead(int messageId);
}
