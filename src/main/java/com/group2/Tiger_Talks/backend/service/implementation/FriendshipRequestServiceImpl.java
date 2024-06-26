package com.group2.Tiger_Talks.backend.service.implementation;

import com.group2.Tiger_Talks.backend.model.Socials.Friendship;
import com.group2.Tiger_Talks.backend.model.Socials.FriendshipRequest;
import com.group2.Tiger_Talks.backend.model.User.UserTemplate;
import com.group2.Tiger_Talks.backend.repsitory.Socials.FriendshipRepository;
import com.group2.Tiger_Talks.backend.repsitory.Socials.FriendshipRequestRepository;
import com.group2.Tiger_Talks.backend.repsitory.User.UserTemplateRepository;
import com.group2.Tiger_Talks.backend.service.Socials.FriendshipRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class FriendshipRequestServiceImpl implements FriendshipRequestService {

    @Autowired
    private FriendshipRequestRepository friendshipRequestRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserTemplateRepository userTemplateRepository;

    @Override
    public Optional<String> sendFriendshipRequest(String senderEmail, String receiverEmail) {
        UserTemplate sender = userTemplateRepository.findUserTemplateByEmail(senderEmail)
                .orElseThrow(() -> new IllegalStateException("Sender not found"));
        UserTemplate receiver = userTemplateRepository.findUserTemplateByEmail(receiverEmail)
                .orElseThrow(() -> new IllegalStateException("Receiver not found"));

        if (friendshipRepository.findBySenderAndReceiverOrReceiverAndSender(
                sender, receiver, receiver, sender).isPresent()) {
            return Optional.of("Friendship has already existed between these users.");
        }

        if (friendshipRequestRepository.findBySenderAndReceiverOrReceiverAndSender(
                sender, receiver, receiver, sender).isPresent()) {
            return Optional.of("Friendship request has already existed between these users.");
        }

        friendshipRequestRepository.save(new FriendshipRequest(sender, receiver, LocalDate.now()));
        return Optional.empty();
    }

    @Override
    public Optional<String> acceptFriendshipRequest(Integer friendshipRequestId) {
        FriendshipRequest friendshipRequest = friendshipRequestRepository.findById(friendshipRequestId)
                .orElseThrow(() -> new IllegalStateException("friendship request ID does not exist!"));
        friendshipRepository.save(
                new Friendship(
                friendshipRequest.getSender(),
                friendshipRequest.getReceiver(),
                LocalDate.now())
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
