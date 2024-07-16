package com.group2.Tiger_Talks.backend.service._implementation.Post;

import com.group2.Tiger_Talks.backend.model.Post.Post;
import com.group2.Tiger_Talks.backend.model.Post.PostDTO;
import com.group2.Tiger_Talks.backend.model.Post.PostLike;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.PostLikeRepository;
import com.group2.Tiger_Talks.backend.repository.PostRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @InjectMocks
    private PostServiceImpl postServiceImpl;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPostsForUserAndFriendsEmailFound() {
        String email = "test@dal.ca";
        UserProfile userProfile = mock(UserProfile.class);
        when(userProfileRepository.findById(email)).thenReturn(Optional.of(userProfile));
        when(userProfile.getPostList()).thenReturn(new LinkedList<>());
        when(userProfile.getAllFriends()).thenReturn(new LinkedList<>());

        List<PostDTO> result = postServiceImpl.getPostsForUserAndFriends(email);

        assertNotNull(result, "Expected non-null list of PostDTOs");
        verify(userProfileRepository, times(1)).findById(email);
        verify(userProfile, times(1)).getPostList();
        verify(userProfile, times(1)).getAllFriends();
    }

    @Test
    public void testGetPostsForUserAndFriendsEmailNotFound() {
        String email = "test@dal.ca";
        when(userProfileRepository.findById(email)).thenReturn(Optional.empty());

        List<PostDTO> result = postServiceImpl.getPostsForUserAndFriends(email);

        assertNotNull(result, "Expected non-null list of PostDTOs");
        assertTrue(result.isEmpty(), "Expected empty list of PostDTOs");
        verify(userProfileRepository, times(1)).findById(email);
    }

    @Test
    public void testGetPostsForUserEmailFound() {
        String email = "test@dal.ca";
        UserProfile userProfile = mock(UserProfile.class);
        when(userProfileRepository.findById(email)).thenReturn(Optional.of(userProfile));
        when(userProfile.getPostList()).thenReturn(new LinkedList<>());

        List<PostDTO> result = postServiceImpl.getPostsForUser(email);

        assertNotNull(result, "Expected non-null list of PostDTOs");
        verify(userProfileRepository, times(1)).findById(email);
        verify(userProfile, times(1)).getPostList();
    }

    @Test
    public void testGetPostsForUserEmailNotFound() {
        String email = "test@dal.ca";
        when(userProfileRepository.findById(email)).thenReturn(Optional.empty());

        List<PostDTO> result = postServiceImpl.getPostsForUser(email);

        assertNotNull(result, "Expected non-null list of PostDTOs");
        assertTrue(result.isEmpty(), "Expected empty list of PostDTOs");
        verify(userProfileRepository, times(1)).findById(email);
    }

    @Test
    public void testCreatePostUserExists() {
        Post post = mock(Post.class);
        UserProfile userProfile = mock(UserProfile.class);
        when(post.getUserProfile()).thenReturn(userProfile);
        when(userProfile.getEmail()).thenReturn("test@dal.ca");
        when(userProfileRepository.existsById("test@dal.ca")).thenReturn(true);

        Optional<String> result = postServiceImpl.createPost(post);

        assertFalse(result.isPresent(), "Expected no error message");
        verify(postRepository, times(1)).save(post);
    }

    @Test
    public void testCreatePostUserNotExists() {
        Post post = mock(Post.class);
        UserProfile userProfile = mock(UserProfile.class);
        when(post.getUserProfile()).thenReturn(userProfile);
        when(userProfile.getEmail()).thenReturn("test@dal.ca");
        when(userProfileRepository.existsById("test@dal.ca")).thenReturn(false);

        Optional<String> result = postServiceImpl.createPost(post);

        assertTrue(result.isPresent(), "Expected error message");
        assertEquals("Does not find user, fail to create post.", result.get(),
                "Expected specific error message");
        verify(postRepository, never()).save(post);
    }

    @Test
    public void testDeletePostByIdPostExists() {
        Integer postId = 1;
        when(postRepository.existsById(postId)).thenReturn(true);

        Optional<String> result = postServiceImpl.deletePostById(postId);

        assertFalse(result.isPresent(), "Expected no error message");
        verify(postRepository, times(1)).deleteById(postId);
    }

    @Test
    public void testDeletePostByIdPostDoesNotExist() {
        Integer postId = 1;
        when(postRepository.existsById(postId)).thenReturn(false);

        Optional<String> result = postServiceImpl.deletePostById(postId);

        assertTrue(result.isPresent(), "Expected error message");
        assertEquals("Does not find post id, fail to delete post.", result.get(),
                "Expected specific error message");
        verify(postRepository, never()).deleteById(postId);
    }

    @Test
    public void testDeletePostPostExists() {
        Post post = mock(Post.class);
        when(post.getPostId()).thenReturn(1);
        when(postRepository.existsById(1)).thenReturn(true);

        Optional<String> result = postServiceImpl.deletePost(post);

        assertFalse(result.isPresent(), "Expected no error message");
        verify(postRepository, times(1)).delete(post);
    }

    @Test
    public void testDeletePostDoesNotExist() {
        Post post = mock(Post.class);
        when(post.getPostId()).thenReturn(1);
        when(postRepository.existsById(1)).thenReturn(false);

        Optional<String> result = postServiceImpl.deletePost(post);

        assertTrue(result.isPresent(), "Expected error message");
        assertEquals("Does not find post id, fail to delete post.", result.get(), "Expected specific error message");
        verify(postRepository, never()).delete(post);
    }


    @Test
    public void testUpdatePostByIdPostExists() {
        Integer postId = 1;
        Post post = mock(Post.class);
        Post existingPost = mock(Post.class);
        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));

        Optional<String> result = postServiceImpl.updatePostById(postId, post);
        assertTrue(result.isEmpty(), "Updating a post with this id should be successful");
        verify(postRepository, times(1)).save(existingPost);
    }

    @Test
    public void testUpdatePostByIdPostDoesNotExist() {
        Integer postId = 1;
        Post post = mock(Post.class);
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        Optional<String> result = postServiceImpl.updatePostById(postId, post);

        assertTrue(result.isPresent(), "You should not be able to find a post with this id");

        assertEquals("Post with ID " + postId + " was not found", result.get(), "Expected specific error message");
        verify(postRepository, never()).save(post);
    }

    @Test
    public void testLikePostAlreadyLiked() {
        Integer postId = 1;
        String userEmail = "test@dal.ca";
        Post post = mock(Post.class);
        UserProfile userProfile = mock(UserProfile.class);
        PostLike postLike = mock(PostLike.class);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userProfileRepository.findUserProfileByEmail(userEmail)).thenReturn(Optional.of(userProfile));
        when(postLikeRepository.findByPostAndUserProfile(post, userProfile)).thenReturn(Optional.of(postLike));

        Post updatedPost = new Post(userProfile, "Test content", null);
        when(postRepository.save(post)).thenReturn(updatedPost);

        Post result = postServiceImpl.likePost(postId, userEmail);

        assertEquals(updatedPost, result, "Expected the post to be updated and returned");
        verify(postLikeRepository, times(1)).delete(postLike);
        verify(postRepository, times(1)).save(post);
    }

    @Test
    public void testLikePostNotLikedYet() {
        Integer postId = 1;
        String userEmail = "test@dal.ca";
        Post post = mock(Post.class);
        UserProfile userProfile = mock(UserProfile.class);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userProfileRepository.findUserProfileByEmail(userEmail)).thenReturn(Optional.of(userProfile));

        Post updatedPost = new Post(userProfile, "Test content", null);
        when(postRepository.save(post)).thenReturn(updatedPost);

        Post result = postServiceImpl.likePost(postId, userEmail);

        assertEquals(updatedPost, result, "Expected the post to be updated and returned");
        verify(postLikeRepository, times(1)).save(any(PostLike.class));
        verify(postRepository, times(1)).save(post);
    }

    @Test
    public void testLikePostPostNotFound() {
        Integer postId = 1;
        String userEmail = "test@dal.ca";

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            postServiceImpl.likePost(postId, userEmail);
        });

        assertEquals("Post not found", exception.getMessage(),
                "Expected 'Post not found' exception message");
        verify(postLikeRepository, never()).save(any(PostLike.class));
        verify(postLikeRepository, never()).delete(any(PostLike.class));
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    public void testLikePostUserNotFound() {
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
        verify(postLikeRepository, never()).save(any(PostLike.class));
        verify(postLikeRepository, never()).delete(any(PostLike.class));
        verify(postRepository, never()).save(any(Post.class));
    }

}
