package com.group2.Tiger_Talks.backend.service.implementation.Friend;
import com.group2.Tiger_Talks.backend.model.FriendshipRequest;
import com.group2.Tiger_Talks.backend.repsitory.Friendship.FriendshipRequestRepository;
import com.group2.Tiger_Talks.backend.service.Friend.AddFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddFriendServiceImpl implements AddFriendService {
    @Autowired
    private FriendshipRequestRepository friendshipRequestRepository;

    public void sendFriendRequest(String senderEmail, String receiverEmail) {
        friendshipRequestRepository.save(new FriendshipRequest(senderEmail, receiverEmail, "2024/06/19/00:00:00"));
    }
}