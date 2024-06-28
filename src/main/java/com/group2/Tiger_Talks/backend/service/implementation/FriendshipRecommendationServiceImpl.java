package com.group2.Tiger_Talks.backend.service.implementation;

import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.model.UserProfileDTO;
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

    public List<UserProfileDTO> recommendFriends(String email, int numOfFriends) {

        if (userProfileRepository.findById(email).isPresent()) {
            // get all potential friends
            List<UserProfile> potentialFriends = userProfileRepository.findAll().stream()
                    .filter(user -> !(userProfileRepository.findById(email).get().getAllFriends())
                            .contains(user) && !user.getEmail().equals(email))
                    .collect(Collectors.toList());

            // make it random
            Collections.shuffle(potentialFriends);

            // choose the first n friends
            return potentialFriends.stream()
                    .limit(numOfFriends)
                    .map(UserProfileDTO::new)
                    .collect(Collectors.toList());
        }
        return new LinkedList<>();
    }
}
