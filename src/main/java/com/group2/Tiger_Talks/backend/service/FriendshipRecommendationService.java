package com.group2.Tiger_Talks.backend.service;

import com.group2.Tiger_Talks.backend.model.UserProfilePostDTO;

import java.util.List;

public interface FriendshipRecommendationService {

    List<UserProfilePostDTO> recommendFriends(String email, int limit);

}
