package com.group2.Tiger_Talks.backend.service.implementation;

import com.group2.Tiger_Talks.backend.model.Friendship;
import com.group2.Tiger_Talks.backend.model.FriendshipRequest;
import com.group2.Tiger_Talks.backend.repsitory.Friendship.FriendshipRepository;
import com.group2.Tiger_Talks.backend.repsitory.Friendship.FriendshipRequestRepository;
import com.group2.Tiger_Talks.backend.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendshipRequestRepository friendshipRequestRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Override
    public void acceptFriendRequest(Integer friendshipRequestId) {
        FriendshipRequest request = friendshipRequestRepository.findById(friendshipRequestId).orElseThrow();
        request.setStatus("approved");
        friendshipRequestRepository.save(request);
        friendshipRepository.save(new Friendship(
                request.getUserFriendshipSender(),
                request.getUserFriendshipReceiver(),
                "2024/06/19/00:00:00"));
    }

    @Override
    public void sendFriendRequest(String senderEmail, String receiverEmail) {
        if (friendshipRequestRepository
                .findByUserFriendshipSenderAndUserFriendshipReceiver(senderEmail, receiverEmail)
                .isPresent()) {
            throw new IllegalArgumentException("Friend request has already existed!");
        }
        friendshipRequestRepository.save(new FriendshipRequest(senderEmail, receiverEmail, "2024/06/19/00:00:00"));
    }

    @Override
    public void rejectFriendRequest(Integer friendshipRequestId) {
        friendshipRequestRepository.findById(friendshipRequestId).orElseThrow().setStatus("rejected");
        friendshipRequestRepository.save(friendshipRequestRepository.findById(friendshipRequestId).orElseThrow());
    }

    @Override
    public List<Friendship> getAllFriends(String email) {
        return friendshipRepository.findByUserFriendshipSenderOrUserFriendshipReceiver(email, email);
    }
}
