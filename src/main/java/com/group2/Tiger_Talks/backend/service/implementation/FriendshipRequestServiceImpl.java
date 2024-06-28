package com.group2.Tiger_Talks.backend.service.implementation;

import com.group2.Tiger_Talks.backend.model.Socials.Friendship;
import com.group2.Tiger_Talks.backend.model.Socials.FriendshipRequest;
import com.group2.Tiger_Talks.backend.model.Socials.FriendshipRequestDTO;
import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Socials.FriendshipRepository;
import com.group2.Tiger_Talks.backend.repository.Socials.FriendshipRequestRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.FriendshipRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    // We have E->A, D->A, get(A) will get E, D
    @Override
    public List<FriendshipRequestDTO> getAllFriendRequests(String email) {
        UserProfile user = userProfileRepository.findUserProfileByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return friendshipRequestRepository.findByReceiver(user).stream()
                .map(FriendshipRequestDTO::new)
                .collect(Collectors.toList());
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
        return Optional.empty();
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
        return Optional.empty();
    }

    @Override
    public Optional<String> rejectFriendshipRequest(Integer friendshipRequestId) {
        FriendshipRequest friendshipRequest = friendshipRequestRepository.findById(friendshipRequestId)
                .orElseThrow(() -> new IllegalStateException("friendship request ID does not exist!"));
        friendshipRequestRepository.delete(friendshipRequest);
        return Optional.empty();
    }


}
