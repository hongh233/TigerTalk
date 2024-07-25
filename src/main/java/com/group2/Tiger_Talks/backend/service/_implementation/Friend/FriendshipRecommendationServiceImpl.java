package com.group2.Tiger_Talks.backend.service._implementation.Friend;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.model.User.UserProfileDTOPost;
import com.group2.Tiger_Talks.backend.repository.Friend.FriendshipRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Friend.FriendshipRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendshipRecommendationServiceImpl implements FriendshipRecommendationService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    public List<UserProfileDTOPost> recommendFriends(String email, int numOfFriends) {
        // Retrieve the user's profile and their list of friends
        return userProfileRepository.findById(email)
                .map(myProfile -> {
                    // Assumes getAllFriends() returns a List<UserProfile>
                    List<UserProfile> allMyFriends = friendshipRepository.findAllFriendsByEmail(myProfile.email());

                    // Get all potential friends, except for current friends and the user themselves
                    List<UserProfile> potentialFriends = new LinkedList<>();
                    List<UserProfile> allUsers = userProfileRepository.findAll();
                    for (UserProfile userProfile : allUsers) {
                        if (!allMyFriends.contains(userProfile) && !userProfile.email().equals(email)) {
                            potentialFriends.add(userProfile);
                        }
                    }

                    // make it random
                    Collections.shuffle(potentialFriends);

                    // Return the first 'numOfFriends' users as recommendations, mapped to DTOs
                    return potentialFriends.stream()
                            .limit(numOfFriends)
                            .map(UserProfileDTOPost::new)
                            .collect(Collectors.toList());
                })
                .orElseGet(LinkedList::new); // Return an empty list if user profile is not found
    }
}
