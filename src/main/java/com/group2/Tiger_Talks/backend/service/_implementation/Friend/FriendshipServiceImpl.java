package com.group2.Tiger_Talks.backend.service._implementation.Friend;

import com.group2.Tiger_Talks.backend.model.Friend.Friendship;
import com.group2.Tiger_Talks.backend.model.Friend.FriendshipDTO;
import com.group2.Tiger_Talks.backend.model.Notification.Notification;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.model.User.UserProfileDTOFriendship;
import com.group2.Tiger_Talks.backend.repository.Socials.FriendshipRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Friend.FriendshipService;
import com.group2.Tiger_Talks.backend.service.Notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public List<UserProfileDTOFriendship> getAllFriendsDTO(String email) {
        UserProfile user = userProfileRepository.findUserProfileByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        List<Friendship> friendships = friendshipRepository.findBySenderOrReceiver(user, user);
        return friendships.stream()
                .map(friendship -> {
                    UserProfile friend = user.equals(friendship.getSender())
                            ? friendship.getReceiver()
                            : friendship.getSender();
                    return new UserProfileDTOFriendship(friend);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<FriendshipDTO> getAllFriends(String email) {
        UserProfile user = userProfileRepository.findUserProfileByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return friendshipRepository.findBySenderOrReceiver(user, user).stream()
                .map(Friendship::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<String> deleteFriendshipByEmail(String senderEmail, String receiverEmail) {
        UserProfile sender = userProfileRepository.findUserProfileByEmail(senderEmail)
                .orElseThrow(() -> new IllegalStateException("Sender not found"));
        UserProfile receiver = userProfileRepository.findUserProfileByEmail(receiverEmail)
                .orElseThrow(() -> new IllegalStateException("Receiver not found"));

        Optional<Friendship> friendship = friendshipRepository.findBySenderAndReceiver(sender, receiver);
        if (friendship.isEmpty()) {
            return Optional.of("No friendship exists between these users.");
        }
        friendshipRepository.delete(friendship.get());

        // send notification to friendship relationship sender
        Notification senderNotification = new Notification(
                sender,
                "Your friendship with " + receiverEmail + " has been terminated.",
                "FriendshipDelete");
        Optional<String> senderError = notificationService.createNotification(senderNotification);
        if (senderError.isPresent()) {
            return senderError;
        }

        // send notification to friendship relationship receiver
        Notification receiverNotification = new Notification(
                receiver,
                "Your friendship with " + senderEmail + " has been terminated.",
                "FriendshipDelete");
        return notificationService.createNotification(receiverNotification);

    }

    @Override
    public boolean areFriends(String email1, String email2) {
        return getAllFriendsDTO(email1)
                .stream()
                .anyMatch(userProfileDTOFriendship -> userProfileDTOFriendship.email().equals(email2));
    }

}
