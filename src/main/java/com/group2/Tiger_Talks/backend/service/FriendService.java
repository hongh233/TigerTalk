package com.group2.Tiger_Talks.backend.service;

import com.group2.Tiger_Talks.backend.model.Friendship;

import java.util.List;

public interface FriendService {
    public void sendFriendRequest(String senderEmail, String receiverEmail);
    public void acceptFriendRequest(Integer friendshipRequestId);
    public void rejectFriendRequest(Integer friendshipRequestId);
    public List<Friendship> getAllFriends(String email);
}
