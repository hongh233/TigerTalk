package com.group2.Tiger_Talks.backend.service.implementation;

import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.model.UserProfilePostDTO;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.FriendshipRecommendationService;
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

    public List<UserProfilePostDTO> recommendFriends(String email, int numOfFriends) {
        // Retrieve the user's profile and their list of friends
        return userProfileRepository.findById(email)
                .map(myProfile -> {
                    // Assumes getAllFriends() returns a List<UserProfile>
                    List<UserProfile> allMyFriends = myProfile.getAllFriends();

                    // Get all potential friends, except for current friends and the user themselves
                    List<UserProfile> potentialFriends = userProfileRepository.findAll().stream()
                            .filter(userProfile -> !allMyFriends.contains(userProfile) && !userProfile.getEmail().equals(email))
                            .collect(Collectors.toList());

                    // make it random
                    Collections.shuffle(potentialFriends);

                    // Return the first 'numOfFriends' users as recommendations, mapped to DTOs
                    return potentialFriends.stream()
                            .limit(numOfFriends)
                            .map(UserProfilePostDTO::new)
                            .collect(Collectors.toList());
                })
                .orElseGet(LinkedList::new); // Return an empty list if user profile is not found
    }
}
