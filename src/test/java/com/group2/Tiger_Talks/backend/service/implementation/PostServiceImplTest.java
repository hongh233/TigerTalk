package com.group2.Tiger_Talks.backend.service.implementation;

import com.group2.Tiger_Talks.backend.model.Post;
import com.group2.Tiger_Talks.backend.model.PostDTO;
import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.repository.PostRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

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
    public void testGetPostsForUserAndFriendsUserProfile() {
        String email = "test@example.com";
        UserProfile userProfile = mock(UserProfile.class);
        when(userProfile.getEmail()).thenReturn(email);

        List<PostDTO> result = postServiceImpl.getPostsForUserAndFriends(userProfile);

        assertNotNull(result, "Expected non-null list of PostDTOs");
        verify(userProfile, times(1)).getEmail();
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

        assertTrue(result.isPresent(), "Expected success message");
        assertEquals("Post updated successfully", result.get(), "Expected specific success message");
        verify(postRepository, times(1)).save(existingPost);
    }

    @Test
    public void testUpdatePostByIdPostDoesNotExist() {
        Integer postId = 1;
        Post post = mock(Post.class);
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            postServiceImpl.updatePostById(postId, post);
        });

        assertEquals("Post was not found", exception.getMessage(), "Expected specific error message");
        verify(postRepository, never()).save(post);
    }
}
