package com.group2.Tiger_Talks.backend.service._implementation.Friend;

import com.group2.Tiger_Talks.backend.model.Friend.Friendship;
import com.group2.Tiger_Talks.backend.model.Friend.FriendshipDTO;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.model.User.UserProfileDTOFriendship;
import com.group2.Tiger_Talks.backend.repository.Socials.FriendshipRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Friend.FriendshipService;
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

    @Override
    public List<UserProfileDTOFriendship> getAllFriendsDTO(String email) {
        UserProfile user = userProfileRepository.findUserProfileByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        List<Friendship> friendships = friendshipRepository.findBySenderOrReceiver(user, user);
        return friendships.stream()
                .map(friendship -> {
                    UserProfile friend = user.equals(friendship.getSender()) ? friendship.getReceiver() : friendship.getSender();
                    return new UserProfileDTOFriendship(friend);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<FriendshipDTO> getAllFriends(String email) {
        UserProfile user = userProfileRepository.findUserProfileByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return friendshipRepository.findBySenderOrReceiver(user, user).stream()
                .map(FriendshipDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<String> deleteFriendshipByEmail(String senderEmail, String receiverEmail) {
        UserProfile sender = userProfileRepository.findUserProfileByEmail(senderEmail)
                .orElseThrow(() -> new IllegalStateException("Sender not found"));
        UserProfile receiver = userProfileRepository.findUserProfileByEmail(receiverEmail)
                .orElseThrow(() -> new IllegalStateException("Receiver not found"));

        Optional<Friendship> friendship = friendshipRepository.
                findBySenderAndReceiver(sender, receiver);
        if (friendship.isPresent()) {
            friendshipRepository.delete(friendship.get());
            return Optional.empty();
        } else {
            return Optional.of("No friendship exists between these users.");
        }
    }

    @Override
    public boolean areFriends(String email1, String email2) {
        return getAllFriendsDTO(email1)
                .stream()
                .anyMatch(userProfileDTOFriendship -> userProfileDTOFriendship.getEmail().equals(email2));
    }

}
