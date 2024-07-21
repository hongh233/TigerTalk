package com.group2.Tiger_Talks.backend.repository.Friend;

import com.group2.Tiger_Talks.backend.model.Friend.FriendshipMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipMessageRepository extends JpaRepository<FriendshipMessage, Integer> {
    List<FriendshipMessage> findByFriendship_FriendshipId(int friendshipId);
}