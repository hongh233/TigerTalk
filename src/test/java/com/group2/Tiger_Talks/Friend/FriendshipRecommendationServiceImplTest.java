package com.group2.Tiger_Talks.backend.service._implementation.Friend;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.model.User.UserProfileDTOPost;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service._implementation.Friend.FriendshipRecommendationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class FriendshipRecommendationServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private FriendshipRecommendationServiceImpl recommendationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void recommendFriends() {
        UserProfile currentUser = new UserProfile(
                "Test", "User", 25, "Male", "testuser",
                "test@dal.ca", "password123",
                new String[]{"Answer1", "Answer2"},
                new String[]{"Question1", "Question2"}
        );

        List<UserProfile> allUsers = Arrays.asList(
                new UserProfile("User1", "One", 30, "Female", "user1",
                        "user1@dal.ca", "password1",
                        new String[]{"Answer1", "Answer2"},
                        new String[]{"Question1", "Question2"}),
                new UserProfile("User2", "Two", 28, "Male", "user2",
                        "user2@dal.ca", "password2",
                        new String[]{"Answer1", "Answer2"},
                        new String[]{"Question1", "Question2"}),
                new UserProfile("User3", "Three", 22, "Female", "user3",
                        "user3@dal.ca", "password3",
                        new String[]{"Answer1", "Answer2"},
                        new String[]{"Question1", "Question2"})
        );

        when(userProfileRepository.findById("test@dal.ca")).thenReturn(Optional.of(currentUser));
        when(userProfileRepository.findAll()).thenReturn(allUsers);

        List<UserProfileDTOPost> recommendedFriends = recommendationService.recommendFriends("test@dal.ca", 2);

        assertEquals(2, recommendedFriends.size(), "The number of recommended friends should be 2");
    }

    @Test
    public void recommendFriends_ReturnsEmptyList() {
        when(userProfileRepository.findById("notfound@dal.ca")).thenReturn(Optional.empty());

        List<UserProfileDTOPost> recommendedFriends = recommendationService.recommendFriends("notfound@dal.ca", 2);

        assertEquals(0, recommendedFriends.size(), "The number of recommended friends should be 0");
    }
}