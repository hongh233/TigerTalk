package com.group2.Tiger_Talks.backend.service._implementation.Friend;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.model.User.UserProfileDTOPost;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FriendshipRecommendationServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private FriendshipRecommendationServiceImpl friendshipRecommendationService;

    private UserProfile userA;
    private UserProfile userB;
    private UserProfile userC;
    private UserProfile userD;

    @BeforeEach
    void setUp() {
        userA = Mockito.mock(UserProfile.class);
        userB = new UserProfile("Blong", "Bside", 23, "Female", "userB", "b@dal.ca", "bbbb1B@b", new String[]{"1", "2", "3"}, new String[]{"What was your favourite book as a child?", "In what city were you born?", "What is the name of the hospital where you were born?"});
        userC = new UserProfile("Clong", "Cside", 24, "Male", "userC", "c@dal.ca", "cccc1C@c", new String[]{"1", "2", "3"}, new String[]{"What was your favourite book as a child?", "In what city were you born?", "What is the name of the hospital where you were born?"});
        userD = new UserProfile("Dlong", "Dside", 25, "Female", "userD", "d@dal.ca", "dddd1D@d", new String[]{"1", "2", "3"}, new String[]{"What was your favourite book as a child?", "In what city were you born?", "What is the name of the hospital where you were born?"});

        // 设置 userA 的邮箱和好友列表
        lenient().when(userA.getEmail()).thenReturn("a@dal.ca");
        lenient().when(userA.getAllFriends()).thenReturn(List.of(userB));
    }

    @Test
    void recommendFriends_userNotFound() {
        when(userProfileRepository.findById("nonexistent@example.com")).thenReturn(Optional.empty());

        List<UserProfileDTOPost> recommendations = friendshipRecommendationService.recommendFriends("nonexistent@example.com", 3);

        assertEquals(0, recommendations.size());
    }

    @Test
    void recommendFriends_noPotentialFriends() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findAll()).thenReturn(List.of(userA, userB));

        List<UserProfileDTOPost> recommendations = friendshipRecommendationService.recommendFriends("a@dal.ca", 3);

        assertEquals(0, recommendations.size());
    }

    @Test
    void recommendFriends_limitedPotentialFriends() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findAll()).thenReturn(List.of(userA, userB, userC));

        List<UserProfileDTOPost> recommendations = friendshipRecommendationService.recommendFriends("a@dal.ca", 3);

        assertEquals(1, recommendations.size());
        assertEquals("c@dal.ca", recommendations.get(0).getEmail());
    }

    @Test
    void recommendFriends_multiplePotentialFriends() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findAll()).thenReturn(List.of(userA, userB, userC, userD));

        List<UserProfileDTOPost> recommendations = friendshipRecommendationService.recommendFriends("a@dal.ca", 2);

        assertEquals(2, recommendations.size());
        List<String> recommendedEmails = List.of(recommendations.get(0).getEmail(), recommendations.get(1).getEmail());
        assert(recommendedEmails.contains("c@dal.ca"));
        assert(recommendedEmails.contains("d@dal.ca"));
    }
}
