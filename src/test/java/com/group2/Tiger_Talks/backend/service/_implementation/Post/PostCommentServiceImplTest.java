package com.group2.Tiger_Talks.backend.service._implementation.Post;

import com.group2.Tiger_Talks.backend.model.Post.Post;
import com.group2.Tiger_Talks.backend.model.Post.PostComment;
import com.group2.Tiger_Talks.backend.model.Post.PostCommentDTO;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.model.User.UserProfileDTO;
import com.group2.Tiger_Talks.backend.repository.Post.PostCommentRepository;
import com.group2.Tiger_Talks.backend.repository.Post.PostRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostCommentServiceImplTest {

    @Mock
    private PostCommentRepository postCommentRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostCommentServiceImpl postCommentService;

    private UserProfile userA;
    private UserProfile userB;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userA = new UserProfile(
                "userD",
                "number",
                12,
                "Male",
                "userD",
                "d@dal.ca",
                "aaaa1A@a",
                new String[]{"1", "2", "3"},
                new String[]{
                        "What was your favourite book as a child?",
                        "In what city were you born?",
                        "What is the name of the hospital where you were born?"
                }
        );
        userB = new UserProfile(
                "Hong",
                "Huang",
                12,
                "other",
                "a",
                "hn582183@dal.ca",
                "aaaa1A@a",
                new String[]{"1", "2", "3"},
                new String[]{
                        "What was your favourite book as a child?",
                        "In what city were you born?",
                        "What is the name of the hospital where you were born?"
                }
        );
    }


    @Test
    public void testAddComment() {
        // Preparing test data
        Post post = new Post();
        post.setPostId(1);
        post.setContent("test content");
        post.setUserProfile(userB);


        UserProfile commentSenderUserProfile = userA;
        UserProfile postSenderUserProfile = userB;

        UserProfileDTO commentSenderDTO = userA.toDto();
        UserProfileDTO postSenderDTO = userB.toDto();


        PostComment postComment = new PostComment(
                post,
                "This is a comment.",
                commentSenderUserProfile,
                postSenderUserProfile
        );
        postComment.setCommentId(1);
        postComment.setTimestamp(LocalDateTime.of(2024, 7, 5, 12, 0));

        PostCommentDTO postCommentDTO = postComment.toDto();


        // Simulating dependent behaviors
        when(postRepository.findById(1)).thenReturn(Optional.of(post));
        when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(commentSenderUserProfile));
        when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(postSenderUserProfile));
        when(postCommentRepository.save(any(PostComment.class))).thenReturn(postComment);

        // Calling service
        PostCommentDTO result = postCommentService.addComment(postCommentDTO);

        // results validation
        assertNotNull(result);
        assertEquals(postCommentDTO.content(), result.content());
        assertEquals(postCommentDTO.timestamp(), result.timestamp());
        assertEquals(postCommentDTO.commentSenderUserProfileDTO().email(), result.commentSenderUserProfileDTO().email());
        assertEquals(postCommentDTO.postSenderUserProfileDTO().email(), result.postSenderUserProfileDTO().email());

    }

    @Test
    public void testAddCommentPostNotFound() {
        PostCommentDTO postCommentDTO = new PostCommentDTO(1, null, null);
        when(postRepository.findById(1)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> postCommentService.addComment(postCommentDTO)
        );
        assertEquals("Post not found", exception.getMessage());
    }

    @Test
    public void testAddCommentSenderNotFound() {
        Post post = new Post();
        post.setPostId(1);
        post.setContent("test content");
        post.setUserProfile(userB);
        PostCommentDTO postCommentDTO = new PostCommentDTO(1,userA.toDto(),null);
        lenient().when(postRepository.findById(1)).thenReturn(Optional.of(post));
        lenient().when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> postCommentService.addComment(postCommentDTO)
        );
        assertEquals("Comment sender user profile not found", exception.getMessage());
    }

    @Test
    public void testGetCommentsByPostId() {
        // Preparing test data
        Post post = new Post();
        post.setPostId(1);
        post.setContent("test content");
        post.setUserProfile(userB);

        UserProfile commentSenderUserProfile = userA;
        UserProfile postSenderUserProfile = userB;

        PostComment postComment = new PostComment(post, "This is a comment.", commentSenderUserProfile, postSenderUserProfile);
        postComment.setCommentId(1);
        postComment.setTimestamp(LocalDateTime.of(2024, 7, 5, 12, 0));

        // Simulating dependent behaviors
        lenient().when(postCommentRepository.findByPost_PostId(1)).thenReturn(List.of(postComment));
        lenient().when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(commentSenderUserProfile));
        lenient().when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(postSenderUserProfile));

        // Calling service
        List<PostCommentDTO> result = postCommentService.getCommentsByPostId(1);

        // results validation
        assertNotNull(result);
        assertEquals(1, result.size());
        PostCommentDTO dto = result.get(0);
        assertEquals(postComment.getContent(), dto.content());
        assertEquals(postComment.getTimestamp(), dto.timestamp());
        assertEquals(commentSenderUserProfile.getEmail(), dto.commentSenderUserProfileDTO().email());
        assertEquals(postSenderUserProfile.getEmail(), dto.postSenderUserProfileDTO().email());
    }

    @Test
    public void testGetCommentsByPostIdNoComments() {
        when(postCommentRepository.findByPost_PostId(1)).thenReturn(Collections.emptyList());

        List<PostCommentDTO> result = postCommentService.getCommentsByPostId(1);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    @Test
    public void testGetCommentsByPostIdPostNotFound() {
        lenient().when(postRepository.findById(1)).thenReturn(Optional.empty());
        List<PostCommentDTO> result = postCommentService.getCommentsByPostId(1);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllComments() {
        // Preparing test data
        Post post = new Post();
        post.setPostId(1);
        post.setContent("test content");
        post.setUserProfile(userB);

        UserProfile commentSenderUserProfile = userA;
        UserProfile postSenderUserProfile = userB;

        PostComment postComment = new PostComment(post, "This is a comment.", commentSenderUserProfile, postSenderUserProfile);
        postComment.setCommentId(1);
        postComment.setTimestamp(LocalDateTime.of(2024, 7, 5, 12, 0));

        // Simulating dependent behaviors
        lenient().when(postCommentRepository.findAll()).thenReturn(List.of(postComment));
        lenient().when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(commentSenderUserProfile));
        lenient().when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(postSenderUserProfile));

        // Calling service
        List<PostCommentDTO> result = postCommentService.getAllComments();

        // results validation
        assertNotNull(result);
        assertEquals(1, result.size());
        PostCommentDTO dto = result.get(0);
        assertEquals(postComment.getContent(), dto.content());
        assertEquals(postComment.getTimestamp(), dto.timestamp());
        assertEquals(commentSenderUserProfile.getEmail(), dto.commentSenderUserProfileDTO().email());
        assertEquals(postSenderUserProfile.getEmail(), dto.postSenderUserProfileDTO().email());
    }

    @Test
    public void testGetAllCommentsNoComments() {
        when(postCommentRepository.findAll()).thenReturn(Collections.emptyList());
        List<PostCommentDTO> result = postCommentService.getAllComments();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllCommentsPostNotFound() {
        lenient().when(postRepository.findById(1)).thenReturn(Optional.empty());
        List<PostCommentDTO> result = postCommentService.getAllComments();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


}