package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.UserProfilePostDTO;
import com.group2.Tiger_Talks.backend.service.FriendshipRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.group2.Tiger_Talks.backend.model.Utils.CROSS_ORIGIN_HOST_NAME;

@RestController
@RequestMapping("/friendships")
public class FriendshipRecommendationController {

    @Autowired
    private FriendshipRecommendationService friendRecommendationService;

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @GetMapping("/recommendFriends/{email}/{numOfFriends}")
    public ResponseEntity<List<UserProfilePostDTO>> recommendFriends(@PathVariable String email, @PathVariable int numOfFriends) {
        List<UserProfilePostDTO> recommendedFriends = friendRecommendationService.recommendFriends(email, numOfFriends);
        return ResponseEntity.ok(recommendedFriends);
    }

}
