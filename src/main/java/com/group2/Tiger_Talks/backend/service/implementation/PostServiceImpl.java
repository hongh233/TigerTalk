package com.group2.Tiger_Talks.backend.service.implementation;

import com.group2.Tiger_Talks.backend.model.Post;
import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.repository.PostRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public List<Post> getPostsForUserAndFriends(String email) {
        if (userProfileRepository.findById(email).isPresent()) {
            // add user posts from itself
            List<Post> postList = new LinkedList<>(userProfileRepository.findById(email).get().getPostList());
            // add user's friend posts
            for (UserProfile friend : userProfileRepository.findById(email).get().getAllFriends()) {
                postList.addAll(friend.getPostList());
            }
            // sorted by time
            postList.sort(Comparator.comparing(Post::getTimestamp, Comparator.nullsLast(Comparator.naturalOrder())).reversed());
            return postList;
        }
        return new LinkedList<>();
    }

    @Override
    public List<Post> getPostsForUserAndFriends(UserProfile userProfile) {
        // add user posts from itself
        List<Post> postList = new LinkedList<>(userProfile.getPostList());
        // add user's friend posts
        for (UserProfile friend : userProfile.getAllFriends()) {
            postList.addAll(friend.getPostList());
        }
        // sorted by time
        postList.sort(Comparator.comparing(Post::getTimestamp, Comparator.nullsLast(Comparator.naturalOrder())).reversed());
        return postList;
    }

    @Override
    public List<Post> getPostsForUser(String email) {
        if (userProfileRepository.findById(email).isPresent()) {
            // add empty
            List<Post> postList = new LinkedList<>();
            // add user's friend posts
            for (UserProfile friend : userProfileRepository.findById(email).get().getAllFriends()) {
                postList.addAll(friend.getPostList());
            }
            // sorted by time
            postList.sort(Comparator.comparing(Post::getTimestamp, Comparator.nullsLast(Comparator.naturalOrder())).reversed());
            return postList;
        }
        return new LinkedList<>();
    }

    @Override
    public List<Post> getPostsForUser(UserProfile userProfile) {
        // add empty
        List<Post> postList = new LinkedList<>();
        // add user's friend posts
        for (UserProfile friend : userProfile.getAllFriends()) {
            postList.addAll(friend.getPostList());
        }
        // sorted by time
        postList.sort(Comparator.comparing(Post::getTimestamp, Comparator.nullsLast(Comparator.naturalOrder())).reversed());
        return postList;
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

}
