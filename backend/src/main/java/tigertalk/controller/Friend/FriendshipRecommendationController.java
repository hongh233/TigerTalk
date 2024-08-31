package tigertalk.controller.Friend;
import tigertalk.model.User.UserProfileDTOPost;
import tigertalk.service.Friend.FriendshipRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.status(200).body(recommendedFriends);
    }
}
