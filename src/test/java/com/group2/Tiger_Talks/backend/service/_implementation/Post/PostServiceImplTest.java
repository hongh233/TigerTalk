package com.group2.Tiger_Talks.backend.service._implementation.Post;

import com.group2.Tiger_Talks.backend.model.Friend.Friendship;
import com.group2.Tiger_Talks.backend.model.Notification;
import com.group2.Tiger_Talks.backend.model.Post.Post;
import com.group2.Tiger_Talks.backend.model.Post.PostDTO;
import com.group2.Tiger_Talks.backend.model.Post.PostLike;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Post.PostLikeRepository;
import com.group2.Tiger_Talks.backend.repository.Post.PostRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Notification.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private PostLikeRepository postLikeRepository;

    @InjectMocks
    private PostServiceImpl postServiceImpl;

    @Mock
    private NotificationService notificationService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for getPostsForUserAndFriends
     */
    @Test
    public void getPostsForUserAndFriends_emailFound() {
        String email = "test@dal.ca";
        UserProfile userProfile = mock(UserProfile.class);
        when(userProfileRepository.findById(email)).thenReturn(Optional.of(userProfile));
        when(userProfile.getPostList()).thenReturn(new LinkedList<>());
        when(userProfile.getAllFriends()).thenReturn(new LinkedList<>());
        List<PostDTO> result = postServiceImpl.getPostsForUserAndFriends(email);
        assertNotNull(result, "Expected non-null list of PostDTOs");
    }

    @Test
    public void getPostsForUserAndFriends_emailNotFound() {
        String email = "test@dal.ca";
        when(userProfileRepository.findById(email)).thenReturn(Optional.empty());
        List<PostDTO> result = postServiceImpl.getPostsForUserAndFriends(email);
        assertNotNull(result, "Expected non-null list of PostDTOs");
        assertTrue(result.isEmpty(), "Expected empty list of PostDTOs");
    }

    /**
     * Test case for getPostsForUser
     */
    @Test
    public void getPostsForUser_emailFound() {
        String email = "test@dal.ca";
        UserProfile userProfile = mock(UserProfile.class);
        when(userProfileRepository.findById(email)).thenReturn(Optional.of(userProfile));
        when(userProfile.getPostList()).thenReturn(new LinkedList<>());
        List<PostDTO> result = postServiceImpl.getPostsForUser(email);
        assertNotNull(result, "Expected non-null list of PostDTOs");
    }

    @Test
    public void getPostsForUser_emailNotFound() {
        String email = "test@dal.ca";
        when(userProfileRepository.findById(email)).thenReturn(Optional.empty());
        List<PostDTO> result = postServiceImpl.getPostsForUser(email);
        assertNotNull(result, "Expected non-null list of PostDTOs");
        assertTrue(result.isEmpty(), "Expected empty list of PostDTOs");
    }

    /**
     * Test case for createPost
     */
    @Test
    public void createPost_userExists() {
        Post post = mock(Post.class);
        UserProfile userProfile = mock(UserProfile.class);
        when(post.getUserProfile()).thenReturn(userProfile);
        when(userProfile.getEmail()).thenReturn("test@dal.ca");
        when(userProfileRepository.existsById("test@dal.ca")).thenReturn(true);
        Optional<String> result = postServiceImpl.createPost(post);
        assertFalse(result.isPresent(), "Expected no error message");
    }

    @Test
    public void createPost_userNotExists() {
        Post post = mock(Post.class);
        UserProfile userProfile = mock(UserProfile.class);
        when(post.getUserProfile()).thenReturn(userProfile);
        when(userProfile.getEmail()).thenReturn("test@dal.ca");
        when(userProfileRepository.existsById("test@dal.ca")).thenReturn(false);
        Optional<String> result = postServiceImpl.createPost(post);
        assertTrue(result.isPresent(), "Expected error message");
        assertEquals("Does not find user, fail to create post.", result.get(),
                "Expected specific error message");
    }

    @Test
    public void createPost_notificationsSentToAllFriends_twoFriends() {
        UserProfile user = new UserProfile();
        user.setEmail("a@dal.ca");
        user.setUserName("User K");

        List<Friendship> friendships = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            UserProfile friend = new UserProfile();
            friend.setEmail("friend" + i + "@dal.ca");
            Friendship friendship = new Friendship();
            friendship.setReceiver(friend);
            friendships.add(friendship);
        }
        user.setReceiverFriendshipList(friendships);
        Post post = new Post();
        post.setUserProfile(user);

        when(userProfileRepository.existsById(user.getEmail())).thenReturn(true);
        lenient().when(postRepository.save(post)).thenReturn(post);
        when(notificationService.createNotification(any())).thenReturn(Optional.empty());

        Optional<String> result = postServiceImpl.createPost(post);
        assertFalse(result.isPresent(), "No error expected");
        verify(notificationService, times(2)).createNotification(any(Notification.class));
    }

    @Test
    public void createPost_NotificationSentToAllFriends_oneFriend() {
        UserProfile user = new UserProfile();
        user.setEmail("a@dal.ca");
        user.setUserName("User K");

        List<Friendship> friendships = new LinkedList<>();
        UserProfile friend = new UserProfile();
        friend.setEmail("friend@dal.ca");
        Friendship friendship = new Friendship();
        friendship.setReceiver(friend);
        friendships.add(friendship);

        user.setReceiverFriendshipList(friendships);
        Post post = new Post();
        post.setUserProfile(user);

        when(userProfileRepository.existsById(user.getEmail())).thenReturn(true);
        lenient().when(postRepository.save(post)).thenReturn(post);
        when(notificationService.createNotification(any())).thenReturn(Optional.empty());

        Optional<String> result = postServiceImpl.createPost(post);
        assertFalse(result.isPresent(), "No error expected");
        verify(notificationService, times(1)).createNotification(any(Notification.class));
    }

    /**
     * Test case for deletePostById
     */
    @Test
    public void deletePostById_postExists() {
        Integer postId = 1;
        when(postRepository.existsById(postId)).thenReturn(true);
        Optional<String> result = postServiceImpl.deletePostById(postId);
        assertFalse(result.isPresent(), "Expected no error message");
    }

    @Test
    public void deletePostById_postDoesNotExist() {
        Integer postId = 1;
        when(postRepository.existsById(postId)).thenReturn(false);
        Optional<String> result = postServiceImpl.deletePostById(postId);
        assertTrue(result.isPresent(), "Expected error message");
        assertEquals("Does not find post id, fail to delete post.", result.get(),
                "Expected specific error message");
    }

    /**
     * Test case for deletePost
     */
    @Test
    public void deletePost_postExists() {
        Post post = mock(Post.class);
        when(post.getPostId()).thenReturn(1);
        when(postRepository.existsById(1)).thenReturn(true);

        Optional<String> result = postServiceImpl.deletePost(post);
        assertFalse(result.isPresent(), "Expected no error message");
    }

    @Test
    public void deletePost_doesNotExist() {
        Post post = mock(Post.class);
        when(post.getPostId()).thenReturn(1);
        when(postRepository.existsById(1)).thenReturn(false);

        Optional<String> result = postServiceImpl.deletePost(post);
        assertTrue(result.isPresent(), "Expected error message");
        assertEquals("Does not find post id, fail to delete post.", result.get(), "Expected specific error message");
    }

    /**
     * Test case for updatePostById
     */
    @Test
    public void updatePostById_postExists() {
        Integer postId = 1;
        Post post = mock(Post.class);
        Post existingPost = mock(Post.class);
        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));

        Optional<String> result = postServiceImpl.updatePostById(postId, post);
        assertTrue(result.isEmpty(), "Updating a post with this id should be successful");
    }

    @Test
    public void updatePostById_postDoesNotExist() {
        Integer postId = 1;
        Post post = mock(Post.class);
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        Optional<String> result = postServiceImpl.updatePostById(postId, post);
        assertTrue(result.isPresent(), "You should not be able to find a post with this id");
        assertEquals("Post with ID " + postId + " was not found", result.get(), "Expected specific error message");
    }

    /**
     * Test case for likePost
     */
    @Test
    public void likePost_alreadyLiked() {
        Integer postId = 1;
        String userEmail = "test@dal.ca";
        Post post = new Post();
        UserProfile userProfile = mock(UserProfile.class);
        PostLike postLike = mock(PostLike.class);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userProfileRepository.findUserProfileByEmail(userEmail)).thenReturn(Optional.of(userProfile));
        when(postLikeRepository.findByPostAndUserProfile(post, userProfile)).thenReturn(Optional.of(postLike));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post result = postServiceImpl.likePost(postId, userEmail);
        assertEquals(post, result, "Should return the same post instance");
    }

    @Test
    public void likePost_notLikedYet() {
        Integer postId = 1;
        String userEmail = "test@dal.ca";
        UserProfile userProfile = new UserProfile();
        userProfile.setEmail(userEmail);
        Post post = new Post();
        post.setPostId(postId);
        post.setUserProfile(userProfile);
        post.setLikes(new ArrayList<>());

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userProfileRepository.findUserProfileByEmail(userEmail)).thenReturn(Optional.of(userProfile));
        when(postRepository.save(any(Post.class))).thenReturn(post);
        Post result = postServiceImpl.likePost(postId, userEmail);

        assertNotNull(result);
        assertEquals(1, result.getNumOfLike(), "Expected number of likes to increase by 1");
        assertEquals(post, result, "Expected the same post to be returned after update");
    }

    @Test
    public void likePost_PostNotFound() {
        Integer postId = 1;
        String userEmail = "test@dal.ca";
        when(postRepository.findById(postId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            postServiceImpl.likePost(postId, userEmail);
        });
        assertEquals("Post not found", exception.getMessage(),
                "Expected 'Post not found' exception message");
    }

    @Test
    public void likePost_userNotFound() {
        Integer postId = 1;
        String userEmail = "test@dal.ca";
        Post post = mock(Post.class);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userProfileRepository.findUserProfileByEmail(userEmail)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            postServiceImpl.likePost(postId, userEmail);
        });
        assertEquals("User not found", exception.getMessage(),
                "Expected 'User not found' exception message");
    }

    @Test
    public void likePost_NotificationSentWhenLikedFirstTime() {
        Integer postId = 1;
        String userEmail = "someone@dal.ca";

        UserProfile owner = new UserProfile();
        owner.setEmail("a@dal.ca");

        UserProfile liker = new UserProfile();
        liker.setEmail(userEmail);

        Post post = new Post();
        post.setPostId(postId);
        post.setUserProfile(owner);
        post.setLikes(new ArrayList<>());

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userProfileRepository.findUserProfileByEmail(userEmail)).thenReturn(Optional.of(liker));
        when(postLikeRepository.findByPostAndUserProfile(post, liker)).thenReturn(Optional.empty());
        when(notificationService.createNotification(any())).thenReturn(Optional.empty());

        Post resultPost = postServiceImpl.likePost(postId, userEmail);
        assertNotNull(resultPost, "Post should not be null");
        verify(notificationService).createNotification(any(Notification.class));  // Ensure notification is sent
        assertEquals(1, resultPost.getNumOfLike(), "Expected the number of likes to increase by 1");
    }

}
