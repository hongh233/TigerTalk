package com.group2.Tiger_Talks.backend.repository.Friend;

import com.group2.Tiger_Talks.backend.model.Friend.Friendship;
import com.group2.Tiger_Talks.backend.model.Friend.FriendshipMessage;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendshipMessageRepository extends JpaRepository<FriendshipMessage, Integer> {
    List<FriendshipMessage> findByFriendship_FriendshipId(int friendshipId);
}