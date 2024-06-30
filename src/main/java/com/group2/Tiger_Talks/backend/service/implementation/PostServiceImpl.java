package com.group2.Tiger_Talks.backend.service.implementation;

import com.group2.Tiger_Talks.backend.model.Post;
import com.group2.Tiger_Talks.backend.model.PostDTO;
import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.repository.PostRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public List<PostDTO> getPostsForUserAndFriends(String email) {
        return userProfileRepository.findById(email)
                .map(userProfile -> Stream.concat(
                                        userProfile.getPostList().stream(),
                                        userProfile.getAllFriends().stream()
                                                .flatMap(friend -> friend.getPostList().stream())
                                ).map(PostDTO::new)
                                .sorted(
                                        Comparator.comparing(PostDTO::getTimestamp, Comparator.nullsLast(Comparator.naturalOrder()))
                                                .reversed()
                                ).toList()
                ).orElseGet(Collections::emptyList);
    }

    @Override
    public List<PostDTO> getPostsForUser(String email) {
        return userProfileRepository.findById(email)
                .map(userProfile -> userProfile.getPostList().stream()
                        .map(PostDTO::new)
                        .sorted(Comparator.comparing(PostDTO::getTimestamp, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                        .collect(Collectors.toCollection(LinkedList::new))
                )
                .orElseGet(LinkedList::new);
    }
    
    @Override
    public Optional<String> createPost(Post post) {
        if (userProfileRepository.existsById(post.getUserProfile().getEmail())) {
            postRepository.save(post);
            return Optional.empty();
        } else {
            return Optional.of("Does not find user, fail to create post.");
        }
    }

    @Override
    public Optional<String> deletePostById(Integer postId) {
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
            return Optional.empty();
        } else {
            return Optional.of("Does not find post id, fail to delete post.");
        }
    }

    @Override
    public Optional<String> deletePost(Post post) {
        if (postRepository.existsById(post.getPostId())) {
            postRepository.delete(post);
            return Optional.empty();
        } else {
            return Optional.of("Does not find post id, fail to delete post.");
        }
    }

    @Override
    public Optional<String> updatePostById(Integer postId, Post post) {
        return postRepository.findById(postId)
                .map(existingPost -> {
                    existingPost.setUserProfile(post.getUserProfile());
                    existingPost.setComments(post.getComments());
                    existingPost.setLikes(post.getLikes());
                    existingPost.setTimestamp(post.getTimestamp());
                    existingPost.setContent(post.getContent());
                    postRepository.save(existingPost);
                    return Optional.<String>empty();
                })
                .orElse(Optional.of("Post with ID " + postId + " was not found"));
    }

}
