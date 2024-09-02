package tigertalk.service._implementation.Post;

import tigertalk.model.Notification.Notification;
import tigertalk.model.Post.Post;
import tigertalk.model.Post.PostDTO;
import tigertalk.model.Post.PostLike;
import tigertalk.model.User.UserProfile;
import tigertalk.repository.Friend.FriendshipRepository;
import tigertalk.repository.Post.PostLikeRepository;
import tigertalk.repository.Post.PostRepository;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.Notification.NotificationService;
import tigertalk.service.Post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostLikeRepository postLikeRepository;
    @Autowired
    FriendshipRepository friendshipRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private NotificationService notificationService;

    @Override
    public List<PostDTO> getPostsForUserAndFriends(String email) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(email);

        if (userProfileOptional.isPresent()) {
            UserProfile userProfile = userProfileOptional.get();
            List<Post> allPosts = new ArrayList<>(userProfile.getPostList());

            List<UserProfile> friends = friendshipRepository.findAllFriendsByEmail(userProfile.getEmail());
            for (UserProfile friend : friends) {
                allPosts.addAll(friend.getPostList());
            }

            List<PostDTO> postDTOs = new ArrayList<>();
            for (Post post : allPosts) {
                postDTOs.add(post.toDto());
            }

            postDTOs.sort(Comparator.comparing(
                    PostDTO::timestamp,
                    Comparator.nullsLast(Comparator.naturalOrder())
            ).reversed());
            return postDTOs;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<PostDTO> getPostsForUser(String email) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(email);

        if (userProfileOptional.isPresent()) {
            UserProfile userProfile = userProfileOptional.get();
            List<Post> userPosts = userProfile.getPostList();

            List<PostDTO> postDTOs = new LinkedList<>();
            for (Post post : userPosts) {
                postDTOs.add(post.toDto());
            }

            postDTOs.sort(Comparator.comparing(
                    PostDTO::timestamp,
                    Comparator.nullsLast(Comparator.naturalOrder())
            ).reversed());
            return postDTOs;
        } else {
            return new LinkedList<>();
        }
    }

    @Override
    public Optional<String> createPost(Post post) {
        if (!userProfileRepository.existsById(post.getUserProfile().getEmail())) {
            return Optional.of("Does not find user, fail to create post.");
        }
        postRepository.save(post);

        UserProfile user = post.getUserProfile();
        List<UserProfile> friendList = friendshipRepository.findAllFriendsByEmail(user.getEmail());

        // content of the notification
        String content = user.getEmail() + " has created a new post.";

        // send notification to all friends
        for (UserProfile friend : friendList) {
            Optional<String> error = notificationService.createNotification(
                    new Notification(friend, content, "NewPost"));
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
    public Post editPost(Integer postId, String newContent) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();
            existingPost.setContent(newContent);
            existingPost.setEdited(true);
            return postRepository.save(existingPost);
        } else {
            throw new RuntimeException("Post not found with id: " + postId);
        }
    }

    @Override
    public Optional<String> updatePostById(Integer postId, Post post) {
        Optional<Post> existingPostOptional = postRepository.findById(postId);

        if (existingPostOptional.isPresent()) {
            Post existingPost = existingPostOptional.get();

            existingPost.setUserProfile(post.getUserProfile());
            existingPost.setComments(post.getComments());
            existingPost.setAssociatedImageURL(post.getAssociatedImageURL());
            existingPost.setLikes(post.getLikes());
            existingPost.setNumOfLike(post.getNumOfLike());
            existingPost.setTimestamp(post.getTimestamp());
            existingPost.setContent(post.getContent());

            postRepository.save(existingPost);
            return Optional.empty();
        } else {
            return Optional.of("Post with ID " + postId + " was not found");
        }
    }

    @Override
    public Post likePost(Integer postId, String userEmail) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new RuntimeException("Post not found");
        }
        Post post = postOptional.get();

        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userEmail);
        if (userProfileOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        UserProfile userProfile = userProfileOptional.get();

        Optional<PostLike> existingLike = postLikeRepository.findByPostAndUserProfile(post, userProfile);

        boolean liked;
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

        if (liked) {
            String content = userProfile.getEmail() + " liked your post.";
            notificationService.createNotification(
                    new Notification(
                            post.getUserProfile(),
                            content,
                            "PostLiked"
                    )
            );
        }
        return post;
    }


}
