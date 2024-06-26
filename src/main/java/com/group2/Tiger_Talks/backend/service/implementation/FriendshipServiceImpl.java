package com.group2.Tiger_Talks.backend.service.implementation;

import com.group2.Tiger_Talks.backend.model.Socials.Friendship;
import com.group2.Tiger_Talks.backend.model.Socials.FriendshipRequest;
import com.group2.Tiger_Talks.backend.model.User.UserTemplate;
import com.group2.Tiger_Talks.backend.repsitory.Socials.FriendshipRepository;
import com.group2.Tiger_Talks.backend.repsitory.Socials.FriendshipRequestRepository;
import com.group2.Tiger_Talks.backend.repsitory.User.UserTemplateRepository;
import com.group2.Tiger_Talks.backend.service.Socials.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserTemplateRepository userTemplateRepository;

    @Override
    public List<Friendship> getAllFriends(String email) {
        UserTemplate user = userTemplateRepository.findUserTemplateByEmail(email)
                .orElseThrow(() -> new IllegalStateException("does not found user"));
        return friendshipRepository.findBySenderOrReceiver(user, user);
    }

    @Override
    public Optional<String> deleteFriendshipByEmail(String senderEmail, String receiverEmail) {
        UserTemplate sender = userTemplateRepository.findUserTemplateByEmail(senderEmail)
                .orElseThrow(() -> new IllegalStateException("Sender not found"));
        UserTemplate receiver = userTemplateRepository.findUserTemplateByEmail(receiverEmail)
                .orElseThrow(() -> new IllegalStateException("Receiver not found"));

        Optional<Friendship> friendship = friendshipRepository.
                findBySenderAndReceiverOrReceiverAndSender(sender, receiver, receiver, sender);
        if (friendship.isPresent()) {
            friendshipRepository.delete(friendship.get());
            return Optional.empty();
        } else {
            return Optional.of("No friendship exists between these users.");
        }
    }

    @Override
    public Optional<String> deleteFriendshipById(Integer friendshipId) {
        Optional<Friendship> friendship = friendshipRepository.findById(friendshipId);
        if (friendship.isPresent()) {
            friendshipRepository.delete(friendship.get());
            return Optional.empty();
        } else {
            return Optional.of("Friendship " + friendshipId + "does not exist!");
        }
    }

}
