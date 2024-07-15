package com.group2.Tiger_Talks.backend.controller.Friend;

import com.group2.Tiger_Talks.backend.model.User.UserProfileDTOPost;
import com.group2.Tiger_Talks.backend.service.Friend.FriendshipRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling friendship recommendations.
 */
@RestController
@RequestMapping("/friendships")
public class FriendshipRecommendationController {

    @Autowired
    private FriendshipRecommendationService friendRecommendationService;

    /**
     * Recommends friends for a user based on their email and number of friends to recommend.
     *
     * @param email        the email of the user for whom recommendations are requested
     * @param numOfFriends the number of friends to recommend
     * @return ResponseEntity with a list of recommended friends, wrapped in UserProfilePostDTO
     */
    @GetMapping("/recommendFriends/{email}/{numOfFriends}")
    public ResponseEntity<List<UserProfileDTOPost>> recommendFriends(@PathVariable String email, @PathVariable int numOfFriends) {
        List<UserProfileDTOPost> recommendedFriends = friendRecommendationService.recommendFriends(email, numOfFriends);
        return ResponseEntity.ok(recommendedFriends);
    }
}
