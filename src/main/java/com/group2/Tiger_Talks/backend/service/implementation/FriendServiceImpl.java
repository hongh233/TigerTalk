package com.group2.Tiger_Talks.backend.service.implementation;

import com.group2.Tiger_Talks.backend.model.Socials.Friendship;
import com.group2.Tiger_Talks.backend.model.Socials.FriendshipRequest;
import com.group2.Tiger_Talks.backend.repsitory.Socials.FriendshipRepository;
import com.group2.Tiger_Talks.backend.repsitory.Socials.FriendshipRequestRepository;
import com.group2.Tiger_Talks.backend.service.Socials.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendshipRequestRepository friendshipRequestRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Override
    public void acceptFriendRequest(Integer friendshipRequestId) {
        FriendshipRequest request = friendshipRequestRepository.findById(friendshipRequestId)
                .orElseThrow(() -> new IllegalStateException("No friend request exists for this ID"));
        request.setStatus("approved");
        friendshipRequestRepository.save(request);
        friendshipRepository.save(new Friendship(
                request.getUserFriendshipSender(),
                request.getUserFriendshipReceiver(),
                LocalDate.now()));
    }

    @Override
    public void sendFriendRequest(String senderEmail, String receiverEmail) {
        // check if friendship has already existed
        if (friendshipRepository
                .findBySendersEmailAndReceiversEmailOrReceiversEmailAndSendersEmail(
                        senderEmail,
                        receiverEmail,
                        receiverEmail,
                        senderEmail).isPresent()) {
            throw new IllegalArgumentException("Friendship already exists between these users.");
        }

        // check if friend request is pending
        if (friendshipRequestRepository
                .findByUserFriendshipSenderAndUserFriendshipReceiverAndStatus(
                        senderEmail,
                        receiverEmail,
                        "pending").isPresent()) {
            throw new IllegalArgumentException("Pending friend request already exists between these users.");
        }

        // check if friend request has been approved
        if (friendshipRequestRepository
                .findByUserFriendshipSenderAndUserFriendshipReceiverAndStatus(
                        senderEmail,
                        receiverEmail,
                        "approved").isPresent()) {
            throw new IllegalArgumentException("Approved friend request already exists between these users.");
        }

        // save new friend request
        friendshipRequestRepository.save(new FriendshipRequest(
                senderEmail,
                receiverEmail,
                LocalDate.now())
        );
    }

    @Override
    public Optional<String> rejectFriendRequest(Integer friendshipRequestId) {
        return friendshipRequestRepository.findById(friendshipRequestId)
                .map(friendshipId -> {
                    friendshipId.setStatus("rejected");
                    friendshipRequestRepository.save(friendshipId);
                    return Optional.<String>empty();
                })
                .orElseGet(() -> Optional.of("Cannot send Message as the user does not exist"));
    }

    @Override
    public List<Friendship> getAllFriends(String email) {
        return friendshipRepository.findBySendersEmailOrReceiversEmail(email, email);
    }
}
