package com.group2.Tiger_Talks.backend.service.implementation;

import com.group2.Tiger_Talks.backend.model.Socials.Friendship;
import com.group2.Tiger_Talks.backend.model.Socials.FriendshipDTO;
import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.model.UserProfileFriendshipDTO;
import com.group2.Tiger_Talks.backend.repository.Socials.FriendshipRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.FriendshipService;
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
    public List<UserProfileFriendshipDTO> getAllFriendsDTO(String email) {
        UserProfile user = userProfileRepository.findUserProfileByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        List<Friendship> friendships = friendshipRepository.findBySenderOrReceiver(user, user);
        return friendships.stream()
                .map(friendship -> {
                    UserProfile friend = user.equals(friendship.getSender()) ? friendship.getReceiver() : friendship.getSender();
                    return new UserProfileFriendshipDTO(friend);
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
    public Optional<String> deleteFriendshipById(Integer friendshipId) {
        Optional<Friendship> friendship = friendshipRepository.findById(friendshipId);
        if (friendship.isPresent()) {
            friendshipRepository.delete(friendship.get());
            return Optional.empty();
        } else {
            return Optional.of("Friendship id " + friendshipId + " does not exist!");
        }
    }

    public boolean areFriends(String email1, String email2) {
        return getAllFriendsDTO(email1)
                .stream()
                .anyMatch(userProfileFriendshipDTO -> userProfileFriendshipDTO.getEmail().equals(email2));
    }

}
