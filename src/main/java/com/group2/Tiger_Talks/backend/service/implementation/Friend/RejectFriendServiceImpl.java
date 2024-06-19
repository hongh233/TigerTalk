package com.group2.Tiger_Talks.backend.service.implementation.Friend;

import com.group2.Tiger_Talks.backend.model.FriendshipRequest;
import com.group2.Tiger_Talks.backend.repsitory.Friendship.FriendshipRequestRepository;
import com.group2.Tiger_Talks.backend.service.Friend.RejectFriendService;
import org.springframework.beans.factory.annotation.Autowired;

public class RejectFriendServiceImpl implements RejectFriendService {
    @Autowired
    private FriendshipRequestRepository friendshipRequestRepository;

    public void rejectFriendRequest(Integer friendshipRequestId) {
        friendshipRequestRepository.findById(friendshipRequestId).orElseThrow().setStatus("rejected");
        friendshipRequestRepository.save(friendshipRequestRepository.findById(friendshipRequestId).orElseThrow());
    }
}
