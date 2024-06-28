package com.group2.Tiger_Talks.backend.service;

import com.group2.Tiger_Talks.backend.model.UserProfileDTO;

import java.util.List;

public interface FriendshipRecommendationService {

    List<UserProfileDTO> recommendFriends(String email, int limit);

}
