package com.group2.Tiger_Talks.backend.service._implementation.Friend;

import com.group2.Tiger_Talks.backend.model.Friend.Friendship;
import com.group2.Tiger_Talks.backend.model.Friend.FriendshipRequest;
import com.group2.Tiger_Talks.backend.model.Friend.FriendshipRequestDTO;
import com.group2.Tiger_Talks.backend.model.Notification.Notification;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Friend.FriendshipRepository;
import com.group2.Tiger_Talks.backend.repository.Friend.FriendshipRequestRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Friend.FriendshipRequestService;
import com.group2.Tiger_Talks.backend.service.Notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendshipRequestServiceImpl implements FriendshipRequestService {

    @Autowired
    private FriendshipRequestRepository friendshipRequestRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private NotificationService notificationService;


    // We have E->A, D->A, get(A) will get E, D
    @Override
    public List<FriendshipRequestDTO> getAllFriendRequests(String email) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findUserProfileByEmail(email);
        if (optionalUserProfile.isEmpty()) {
            throw new IllegalStateException("User not found");
        }
        UserProfile user = optionalUserProfile.get();

        List<FriendshipRequestDTO> list = new ArrayList<>();
        for (FriendshipRequest friendshipRequest : friendshipRequestRepository.findByReceiver(user)) {
            FriendshipRequestDTO dto = friendshipRequest.toDto();
            list.add(dto);
        }
        return list;
    }

    @Override
    public Optional<String> sendFriendshipRequest(String senderEmail, String receiverEmail) {
        UserProfile sender = userProfileRepository.findUserProfileByEmail(senderEmail)
                .orElseThrow(() -> new IllegalStateException("Sender not found"));
        UserProfile receiver = userProfileRepository.findUserProfileByEmail(receiverEmail)
                .orElseThrow(() -> new IllegalStateException("Receiver not found"));

        if (friendshipRepository.findBySenderAndReceiver(sender, receiver).isPresent()) {
            return Optional.of("Friendship has already existed between these users.");
        }
        if (friendshipRepository.findBySenderAndReceiver(receiver, sender).isPresent()) {
            return Optional.of("Friendship has already existed between these users.");
        }

        if (friendshipRequestRepository.findBySenderAndReceiver(sender, receiver).isPresent()) {
            return Optional.of("Friendship request has already existed between these users.");
        }
        if (friendshipRequestRepository.findBySenderAndReceiver(receiver, sender).isPresent()) {
            return Optional.of("Friendship request has already existed between these users.");
        }

        friendshipRequestRepository.save(new FriendshipRequest(sender, receiver));


        // send notification
        return notificationService.createNotification(new Notification(
                receiver,
                "You have a new friend request from " + senderEmail,
                "FriendshipRequestSend"));
    }

    @Override
    public Optional<String> acceptFriendshipRequest(Integer friendshipRequestId) {
        FriendshipRequest friendshipRequest = friendshipRequestRepository.findById(friendshipRequestId)
                .orElseThrow(() -> new IllegalStateException("friendship request ID does not exist!"));
        friendshipRepository.save(
                new Friendship(
                        friendshipRequest.getSender(),
                        friendshipRequest.getReceiver())
        );
        friendshipRequestRepository.delete(friendshipRequest);


        // send notification
        return notificationService.createNotification(new Notification(
                friendshipRequest.getSender(),
                "Your friend request to " + friendshipRequest.getReceiver().email() + " has been accepted.",
                "FriendshipRequestAccept"
        ));
    }

    @Override
    public Optional<String> rejectFriendshipRequest(Integer friendshipRequestId) {
        FriendshipRequest friendshipRequest = friendshipRequestRepository.findById(friendshipRequestId)
                .orElseThrow(() -> new IllegalStateException("friendship request ID does not exist!"));
        friendshipRequestRepository.delete(friendshipRequest);


        // send notification
        return notificationService.createNotification(new Notification(
                friendshipRequest.getSender(),
                "Your friend request to " + friendshipRequest.getReceiver().email() + " has been rejected.",
                "FriendshipRequestReject"
        ));
    }

    @Override
    public int findNumOfTotalRequests() {
        return friendshipRequestRepository.findAll().size();
    }

    @Override
    public boolean areFriendshipRequestExist(String email1, String email2) {
        UserProfile sender = userProfileRepository.findUserProfileByEmail(email1)
                .orElseThrow(() -> new IllegalStateException("Sender not found"));
        UserProfile receiver = userProfileRepository.findUserProfileByEmail(email2)
                .orElseThrow(() -> new IllegalStateException("Receiver not found"));
        return friendshipRequestRepository.findBySenderAndReceiver(sender, receiver).isPresent() ||
                friendshipRequestRepository.findBySenderAndReceiver(receiver, sender).isPresent();
    }


}
