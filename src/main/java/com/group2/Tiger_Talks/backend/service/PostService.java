package com.group2.Tiger_Talks.backend.service;

import com.group2.Tiger_Talks.backend.model.Post;
import com.group2.Tiger_Talks.backend.model.PostDTO;
import com.group2.Tiger_Talks.backend.model.UserProfile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PostService {

    List<PostDTO> getPostsForUserAndFriends(String email);
    List<PostDTO> getPostsForUserAndFriends(UserProfile userProfile);

    List<PostDTO> getPostsForUser(String email);
    List<PostDTO> getPostsForUser(UserProfile userProfile);

    Optional<String> createPost(Post post);

    Optional<String> deletePostById(Integer postId);

    Optional<String> deletePost(Post post);

}
