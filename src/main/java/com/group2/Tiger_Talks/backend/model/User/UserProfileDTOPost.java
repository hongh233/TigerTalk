package com.group2.Tiger_Talks.backend.model.User;

import com.group2.Tiger_Talks.backend.model.Post.PostDTO;

import java.util.List;
import java.util.stream.Collectors;

public record UserProfileDTOPost(
        String email,
        String userName,
        String profilePictureUrl,
        List<PostDTO> postList
) {
    public UserProfileDTOPost(UserProfile userProfile) {
        this(
                userProfile.getEmail(),
                userProfile.getUserName(),
                userProfile.getProfilePictureUrl(),
                userProfile.getPostList().stream().map(PostDTO::new).collect(Collectors.toList())
        );
    }
}
