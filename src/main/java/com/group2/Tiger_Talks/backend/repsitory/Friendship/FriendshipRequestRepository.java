package com.group2.Tiger_Talks.backend.repsitory.Friendship;

import com.group2.Tiger_Talks.backend.model.FriendshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Integer> {
    List<FriendshipRequest> findByUserFriendshipReceiverAndStatus(String userFriendshipReceiver, String status);
}