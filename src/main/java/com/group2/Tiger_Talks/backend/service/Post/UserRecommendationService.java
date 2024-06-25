package com.group2.Tiger_Talks.backend.service.Post;

import com.group2.Tiger_Talks.backend.model.Socials.Friendship;
import com.group2.Tiger_Talks.backend.repsitory.Socials.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserRecommendationService {
    @Autowired
    private FriendshipRepository friendshipRepository;

    public List<String> recommendFriends(String userEmail) {
        List<Friendship> friendships = friendshipRepository.findBySendersEmailOrReceiversEmail(userEmail, userEmail);

        List<String> friendsEmails = friendships.stream()
                .flatMap(f -> Stream.of(f.getSendersEmail(), f.getReceiversEmail()))
                .distinct()
                .filter(email -> !email.equals(userEmail))
                .toList();

        // This is a simple example. In real life, you would have more complex logic here.
        return friendsEmails.stream().limit(5).collect(Collectors.toList());
    }
}
