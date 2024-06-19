package com.group2.Tiger_Talks.backend.repsitory.Friendship;

import com.group2.Tiger_Talks.backend.model.FriendshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Integer> {
    List<FriendshipRequest> findByUserFriendshipReceiverAndStatus(String userFriendshipReceiver, String status);


    // find an exact friend request depend on sender and receiver
    Optional<FriendshipRequest> findByUserFriendshipSenderAndUserFriendshipReceiver(
            String userFriendshipSender,
            String userFriendshipReceiver);

    Optional<FriendshipRequest> findByUserFriendshipSenderAndUserFriendshipReceiverAndStatus(
            String userFriendshipSender,
            String userFriendshipReceiver,
            String status);
}