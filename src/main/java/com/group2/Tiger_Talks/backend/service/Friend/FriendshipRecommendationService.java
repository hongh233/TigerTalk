package com.group2.Tiger_Talks.backend.service.Friend;

import com.group2.Tiger_Talks.backend.model.User.UserProfileDTOPost;

import java.util.List;

public interface FriendshipRecommendationService {

    List<UserProfileDTOPost> recommendFriends(String email, int limit);

}
