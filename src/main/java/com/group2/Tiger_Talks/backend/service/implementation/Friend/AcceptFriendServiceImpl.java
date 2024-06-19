package com.group2.Tiger_Talks.backend.service.implementation.Friend;

import com.group2.Tiger_Talks.backend.model.Friendship;
import com.group2.Tiger_Talks.backend.model.FriendshipRequest;
import com.group2.Tiger_Talks.backend.repsitory.Friendship.FriendshipRepository;
import com.group2.Tiger_Talks.backend.repsitory.Friendship.FriendshipRequestRepository;
import com.group2.Tiger_Talks.backend.service.Friend.AcceptFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcceptFriendServiceImpl implements AcceptFriendService {
    @Autowired
    private FriendshipRequestRepository friendshipRequestRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    public void acceptFriendRequest(Integer friendshipRequestId) {
        FriendshipRequest request = friendshipRequestRepository.findById(friendshipRequestId).orElseThrow();
        request.setStatus("approved");
        friendshipRequestRepository.save(request);
        friendshipRepository.save(new Friendship(
                request.getUserFriendshipSender(),
                request.getUserFriendshipReceiver(),
                "2024/06/19/00:00:00"));
    }
}
