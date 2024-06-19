package com.group2.Tiger_Talks.backend.repsitory.Friendship;

import com.group2.Tiger_Talks.backend.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {
    List<Friendship> findByUserFriendshipSenderOrUserFriendshipReceiver(String userFriendshipSender, String userFriendshipReceiver);
}