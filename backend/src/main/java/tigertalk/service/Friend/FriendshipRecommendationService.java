package tigertalk.service.Friend;

import tigertalk.model.User.UserProfileDTO;

import java.util.List;

public interface FriendshipRecommendationService {

    /**
     * Recommends friends for a user based on their email.
     *
     * @param email the email of the user
     * @param limit the maximum number of friend recommendations to return
     * @return a list of UserProfileDTOPost objects representing the recommended friends
     */
    List<UserProfileDTO> recommendFriends(String email, int limit);

}
