package tigertalk.Tiger_Talks.backend.service._implementation.Post;

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
import tigertalk.service._implementation.Post.PostServiceImpl;
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

    @Mock
    private FriendshipRepository friendshipRepository;

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
        when(friendshipRepository.findAllFriendsByEmail(userProfile.getEmail())).thenReturn(new LinkedList<>());
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

        List<UserProfile> friends = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            UserProfile friend = new UserProfile();
            friend.setEmail("friend" + i + "@dal.ca");
            friends.add(friend);
        }
        Post post = new Post();
        post.setUserProfile(user);

        when(userProfileRepository.existsById(user.getEmail())).thenReturn(true);
        lenient().when(postRepository.save(post)).thenReturn(post);
        when(friendshipRepository.findAllFriendsByEmail(user.getEmail())).thenReturn(friends);
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

        List<UserProfile> friends = new LinkedList<>();
        UserProfile friend = new UserProfile();
        friend.setEmail("friend@dal.ca");
        friends.add(friend);

        Post post = new Post();
        post.setUserProfile(user);

        when(userProfileRepository.existsById(user.getEmail())).thenReturn(true);
        lenient().when(postRepository.save(post)).thenReturn(post);
        when(friendshipRepository.findAllFriendsByEmail(user.getEmail())).thenReturn(friends);
        when(notificationService.createNotification(any())).thenReturn(Optional.empty());

        Optional<String> result = postServiceImpl.createPost(post);
        assertFalse(result.isPresent(), "No error expected");
        verify(notificationService, times(1)).createNotification(any(Notification.class));
    }

    @Test
    public void createPost_notificationServiceFailure() {
        Post post = new Post();
        UserProfile userProfile = new UserProfile();
        userProfile.setEmail("test@dal.ca");
        post.setUserProfile(userProfile);
        List<UserProfile> friends = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            UserProfile friend = new UserProfile();
            friend.setEmail("friend" + i + "@dal.ca");
            friends.add(friend);
        }

        when(userProfileRepository.existsById(userProfile.getEmail())).thenReturn(true);
        when(friendshipRepository.findAllFriendsByEmail(userProfile.getEmail())).thenReturn(friends);
        when(notificationService.createNotification(any())).thenReturn(Optional.of("Failed to create notification"));

        Optional<String> result = postServiceImpl.createPost(post);
        assertTrue(result.isPresent(), "Expected error message");
        assertEquals("Failed to create notification", result.get());
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

    /**
     * Test case for editPost
     */
    @Test
    public void editPost_success_result_notNull() {
        Integer postID = 1;
        String newContent = "Updated content";

        Post existingPost = new Post();
        existingPost.setPostId(postID);
        existingPost.setContent("Original content");
        existingPost.setEdited(false);

        Post updatedPost = new Post();
        updatedPost.setPostId(postID);
        updatedPost.setContent(newContent);
        updatedPost.setEdited(true);

        when(postRepository.findById(postID)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        Post result = postServiceImpl.editPost(postID, newContent);

        assertNotNull(result);
    }

    @Test
    public void editPost_success_check_result_content() {
        Integer postID = 1;
        String newContent = "Updated content";

        Post existingPost = new Post();
        existingPost.setPostId(postID);
        existingPost.setContent("Original content");
        existingPost.setEdited(false);

        Post updatedPost = new Post();
        updatedPost.setPostId(postID);
        updatedPost.setContent(newContent);
        updatedPost.setEdited(true);

        when(postRepository.findById(postID)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        Post result = postServiceImpl.editPost(postID, newContent);
        assertEquals(newContent, result.getContent());
    }

    @Test
    public void editPost_success_check_editState() {
        Integer postID = 1;
        String newContent = "Updated content";

        Post existingPost = new Post();
        existingPost.setPostId(postID);
        existingPost.setContent("Original content");
        existingPost.setEdited(false);

        Post updatedPost = new Post();
        updatedPost.setPostId(postID);
        updatedPost.setContent(newContent);
        updatedPost.setEdited(true);

        when(postRepository.findById(postID)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        Post result = postServiceImpl.editPost(postID, newContent);
        assertTrue(result.getEdited());
    }

    @Test
    public void editPost_PostNotFound() {
        Integer postID = 1;
        String newContent = "Updated content";

        when(postRepository.findById(postID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            postServiceImpl.editPost(postID, newContent);
        });

        String expectedMessage = "Post not found with id: " + postID;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
