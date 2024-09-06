package tigertalk.service._implementation.Friend;

import tigertalk.model.User.UserProfile;
import tigertalk.model.User.UserProfileDTO;
import tigertalk.repository.Friend.FriendshipRepository;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.Friend.FriendshipRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendshipRecommendationServiceImpl implements FriendshipRecommendationService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    public List<UserProfileDTO> recommendFriends(String email, int numOfFriends) {
        Optional<UserProfile> myProfileOptional = userProfileRepository.findById(email);

        if (myProfileOptional.isPresent()) {
            UserProfile myProfile = myProfileOptional.get();
            List<UserProfile> allMyFriends = friendshipRepository.findAllFriendsByEmail(myProfile.getEmail());
            List<UserProfile> potentialFriends = new LinkedList<>();
            List<UserProfile> allUsers = userProfileRepository.findAll();

            for (UserProfile userProfile : allUsers) {
                if (!allMyFriends.contains(userProfile) && !userProfile.getEmail().equals(email)) {
                    potentialFriends.add(userProfile);
                }
            }

            Collections.shuffle(potentialFriends);
            List<UserProfileDTO> recommendedFriends = new LinkedList<>();

            int count = 0;
            for (UserProfile userProfile : potentialFriends) {
                if (count >= numOfFriends) {
                    break;
                }
                recommendedFriends.add(userProfile.toDto());
                count++;
            }
            return recommendedFriends;
        } else {
            return new LinkedList<>();
        }
    }
}
