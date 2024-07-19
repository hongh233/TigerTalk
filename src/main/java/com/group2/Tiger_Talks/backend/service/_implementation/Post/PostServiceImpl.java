package com.group2.Tiger_Talks.backend.service._implementation.Post;

import com.group2.Tiger_Talks.backend.model.Notification.Notification;
import com.group2.Tiger_Talks.backend.model.Post.Post;
import com.group2.Tiger_Talks.backend.model.Post.PostDTO;
import com.group2.Tiger_Talks.backend.model.Post.PostLike;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Post.PostLikeRepository;
import com.group2.Tiger_Talks.backend.repository.Post.PostRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Notification.NotificationService;
import com.group2.Tiger_Talks.backend.service.Post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostLikeRepository postLikeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private NotificationService notificationService;

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
        if (!userProfileRepository.existsById(post.getUserProfile().getEmail())) {
            return Optional.of("Does not find user, fail to create post.");
        }
        postRepository.save(post);

        UserProfile user = post.getUserProfile();
        List<UserProfile> friendList = user.getAllFriends();

        // content of the notification
        String content = user.getUserName() + " has created a new post.";

        // send notification to all friends
        for (UserProfile friend : friendList) {
            Notification notification = new Notification(friend, content, "NewPost");
            Optional<String> error = notificationService.createNotification(notification);
            if (error.isPresent()) {
                return error;
            }
        }

        return Optional.empty();
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
                    existingPost.setNumOfLike(post.getNumOfLike());
                    existingPost.setTimestamp(post.getTimestamp());
                    existingPost.setContent(post.getContent());
                    postRepository.save(existingPost);
                    return Optional.<String>empty();
                })
                .orElse(Optional.of("Post with ID " + postId + " was not found"));
    }

    // This method is about like and unlike
    @Override
    public Post likePost(Integer postId, String userEmail) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        UserProfile userProfile = userProfileRepository.findUserProfileByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<PostLike> existingLike = postLikeRepository.findByPostAndUserProfile(post, userProfile);

        boolean liked; // Track if it is a like or unlike
        if (existingLike.isPresent()) {
            postLikeRepository.delete(existingLike.get());
            post.setNumOfLike(post.getNumOfLike() - 1);
            post.getLikes().remove(existingLike.get());
            liked = false;
        } else {
            PostLike newPostLike = new PostLike(post, userProfile);
            postLikeRepository.save(newPostLike);
            post.setNumOfLike(post.getNumOfLike() + 1);
            post.getLikes().add(newPostLike);
            liked = true;
        }
        postRepository.save(post);

        // Send notification only on like, not on unlike
        // Send notification to the post owner
        if (liked) {
            String content = userProfile.getEmail() + " liked your post.";
            notificationService.createNotification(
                    new Notification(
                            post.getUserProfile(),
                            content,
                            "PostLiked"));
        }

        return post;
    }


}
