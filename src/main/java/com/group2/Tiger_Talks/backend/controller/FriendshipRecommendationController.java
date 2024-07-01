package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.UserProfilePostDTO;
import com.group2.Tiger_Talks.backend.service.FriendshipRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.group2.Tiger_Talks.backend.model.Utils.CROSS_ORIGIN_HOST_NAME;

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
    public ResponseEntity<List<UserProfilePostDTO>> recommendFriends(@PathVariable String email, @PathVariable int numOfFriends) {
        List<UserProfilePostDTO> recommendedFriends = friendRecommendationService.recommendFriends(email, numOfFriends);
        return ResponseEntity.ok(recommendedFriends);
    }
}
