package com.group2.Tiger_Talks.backend.service._implementation.Post;

import com.group2.Tiger_Talks.backend.model.Notification.Notification;
import com.group2.Tiger_Talks.backend.model.Post.Post;
import com.group2.Tiger_Talks.backend.model.Post.PostComment;
import com.group2.Tiger_Talks.backend.model.Post.PostCommentDTO;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.model.User.UserProfileDTO;
import com.group2.Tiger_Talks.backend.repository.Post.PostCommentRepository;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostCommentServiceImplTest {

    @Mock
    private PostCommentRepository postCommentRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private PostCommentServiceImpl postCommentService;

    private UserProfile userA;
    private UserProfile userB;
    private Post post;
    private PostCommentDTO postCommentDTO1;
    private PostComment postComment1;
    private PostComment postComment2;


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

        post = new Post();
        post.setPostId(1);
        post.setContent("test content");
        post.setUserProfile(userB);

        UserProfileDTO commentSenderDTO = new UserProfileDTO(12, "d@dal.ca", "pending", false, "default", "offline", "userD", null, "private", "Male", "userD", "number", "https://res.cloudinary.com/dp4j9a7ry/image/upload/v1719765852/rvfq7rtgnni1ahktelff.jpg", "user", null);
        UserProfileDTO postSenderDTO = new UserProfileDTO(12, "hn582183@dal.ca", "pending", true, "default", "offline", "a", "", "private", "other", "Hong", "Huang", "https://res.cloudinary.com/dp4j9a7ry/image/upload/v1720207705/bwitpvkdv32sv3uvfsab.gif", "user", null);

        postCommentDTO1 = new PostCommentDTO();
        postCommentDTO1.setContent("This is a comment.");
        postCommentDTO1.setTimestamp(LocalDateTime.of(2024, 7, 5, 12, 0));
        postCommentDTO1.setCommentSenderUserProfileDTO(commentSenderDTO);
        postCommentDTO1.setPostSenderUserProfileDTO(postSenderDTO);
        postCommentDTO1.setPostId(1);

        PostCommentDTO postCommentDTO2 = new PostCommentDTO();
        postCommentDTO2.setContent("This is another comment.");
        postCommentDTO2.setTimestamp(LocalDateTime.of(2024, 7, 5, 12, 30));
        postCommentDTO2.setCommentSenderUserProfileDTO(commentSenderDTO);
        postCommentDTO2.setPostSenderUserProfileDTO(postSenderDTO);
        postCommentDTO2.setPostId(1);

        postComment1 = new PostComment();
        postComment1.setCommentId(1);
        postComment1.setContent(postCommentDTO1.getContent());
        postComment1.setTimestamp(postCommentDTO1.getTimestamp());
        postComment1.setCommentSenderUserProfile(userA);
        postComment1.setPost(post);
        postComment1.setPostSenderUserProfile(userB);

        postComment2 = new PostComment();
        postComment2.setCommentId(2);
        postComment2.setContent(postCommentDTO2.getContent());
        postComment2.setTimestamp(postCommentDTO2.getTimestamp());
        postComment2.setCommentSenderUserProfile(userA);
        postComment2.setPost(post);
        postComment2.setPostSenderUserProfile(userB);
    }

    /**
     * Test case for addComment
     */
    @Test
    public void addComment_success_return_check() {
        when(postRepository.findById(1)).thenReturn(Optional.of(post));
        when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(userB));
        when(postCommentRepository.save(any(PostComment.class))).thenReturn(postComment1);
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());

        PostCommentDTO result = postCommentService.addComment(postCommentDTO1);
        assertNotNull(result);
        assertEquals(postCommentDTO1.getContent(), result.getContent());
    }

    @Test
    public void addComment_success_timestamps_check() {
        when(postRepository.findById(1)).thenReturn(Optional.of(post));
        when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(userB));
        when(postCommentRepository.save(any(PostComment.class))).thenReturn(postComment1);
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());

        PostCommentDTO result = postCommentService.addComment(postCommentDTO1);
        assertEquals(postCommentDTO1.getTimestamp(), result.getTimestamp());
    }

    @Test
    public void addComment_success_commentSenderEmail_check() {
        when(postRepository.findById(1)).thenReturn(Optional.of(post));
        when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(userB));
        when(postCommentRepository.save(any(PostComment.class))).thenReturn(postComment1);
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());

        PostCommentDTO result = postCommentService.addComment(postCommentDTO1);
        assertEquals(postCommentDTO1.getCommentSenderUserProfileDTO().email(), result.getCommentSenderUserProfileDTO().email());
    }

    @Test
    public void addComment_success_postSenderEmail_check() {
        when(postRepository.findById(1)).thenReturn(Optional.of(post));
        when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(userB));
        when(postCommentRepository.save(any(PostComment.class))).thenReturn(postComment1);
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());

        PostCommentDTO result = postCommentService.addComment(postCommentDTO1);
        assertEquals(postCommentDTO1.getPostSenderUserProfileDTO().email(), result.getPostSenderUserProfileDTO().email());
    }

    @Test
    public void addComment_success_notification_check() {
        when(postRepository.findById(1)).thenReturn(Optional.of(post));
        when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(userB));
        when(postCommentRepository.save(any(PostComment.class))).thenReturn(postComment1);
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());

        postCommentService.addComment(postCommentDTO1);
        verify(notificationService, times(1)).createNotification(any(Notification.class));
    }

    @Test
    public void addComment_postNotFound() {
        when(postRepository.findById(1)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> postCommentService.addComment(postCommentDTO1));
        assertEquals("Post not found", exception.getMessage());
    }

    @Test
    public void addComment_senderNotFound() {
        when(postRepository.findById(1)).thenReturn(Optional.of(post));
        when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> postCommentService.addComment(postCommentDTO1));
        assertEquals("Comment sender user profile not found", exception.getMessage());
    }

    /**
     * Test case for getCommentsByPostId
     */
    @Test
    public void getCommentsByPostId_oneComment_return_check() {
        lenient().when(postCommentRepository.findByPost_PostId(1)).thenReturn(List.of(postComment1));
        lenient().when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(userA));
        lenient().when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(userB));

        List<PostCommentDTO> result = postCommentService.getCommentsByPostId(1);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void getCommentsByPostId_oneComment_comment_check() {
        lenient().when(postCommentRepository.findByPost_PostId(1)).thenReturn(List.of(postComment1));
        lenient().when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(userA));
        lenient().when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(userB));

        List<PostCommentDTO> result = postCommentService.getCommentsByPostId(1);
        PostCommentDTO dto = result.get(0);
        assertEquals(postComment1.getContent(), dto.getContent());
        assertEquals(postComment1.getTimestamp(), dto.getTimestamp());
    }

    @Test
    public void getCommentsByPostId_oneComment_email_check() {
        lenient().when(postCommentRepository.findByPost_PostId(1)).thenReturn(List.of(postComment1));
        lenient().when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(userA));
        lenient().when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(userB));

        List<PostCommentDTO> result = postCommentService.getCommentsByPostId(1);
        PostCommentDTO dto = result.get(0);
        assertEquals(userA.getEmail(), dto.getCommentSenderUserProfileDTO().email());
        assertEquals(userB.getEmail(), dto.getPostSenderUserProfileDTO().email());
    }

    @Test
    public void getCommentsByPostId_twoComments_return_check() {
        lenient().when(postCommentRepository.findByPost_PostId(1)).thenReturn(List.of(postComment1, postComment2));
        lenient().when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(userA));
        lenient().when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(userB));

        List<PostCommentDTO> result = postCommentService.getCommentsByPostId(1);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void getCommentsByPostId_twoComments_postComment_check() {
        lenient().when(postCommentRepository.findByPost_PostId(1)).thenReturn(List.of(postComment1, postComment2));
        lenient().when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(userA));
        lenient().when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(userB));

        List<PostCommentDTO> result = postCommentService.getCommentsByPostId(1);
        PostCommentDTO dto1 = result.get(0);
        PostCommentDTO dto2 = result.get(1);
        assertEquals(postComment1.getContent(), dto1.getContent());
        assertEquals(postComment1.getTimestamp(), dto1.getTimestamp());
        assertEquals(postComment2.getContent(), dto2.getContent());
        assertEquals(postComment2.getTimestamp(), dto2.getTimestamp());
    }

    @Test
    public void getCommentsByPostId_twoComments_email_check() {
        lenient().when(postCommentRepository.findByPost_PostId(1)).thenReturn(List.of(postComment1, postComment2));
        lenient().when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(userA));
        lenient().when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(userB));

        List<PostCommentDTO> result = postCommentService.getCommentsByPostId(1);
        PostCommentDTO dto1 = result.get(0);
        PostCommentDTO dto2 = result.get(1);
        assertEquals(userA.getEmail(), dto1.getCommentSenderUserProfileDTO().email());
        assertEquals(userB.getEmail(), dto1.getPostSenderUserProfileDTO().email());
        assertEquals(userA.getEmail(), dto2.getCommentSenderUserProfileDTO().email());
        assertEquals(userB.getEmail(), dto2.getPostSenderUserProfileDTO().email());
    }

    @Test
    public void getCommentsByPostId_noComments() {
        when(postCommentRepository.findByPost_PostId(1)).thenReturn(Collections.emptyList());
        List<PostCommentDTO> result = postCommentService.getCommentsByPostId(1);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getCommentsByPostId_postNotFound() {
        lenient().when(postRepository.findById(1)).thenReturn(Optional.empty());
        List<PostCommentDTO> result = postCommentService.getCommentsByPostId(1);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Test case for getAllComments
     */
    @Test
    public void getAllComments_oneComment_return_check() {
        lenient().when(postCommentRepository.findAll()).thenReturn(Collections.singletonList(postComment1));
        lenient().when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(userA));
        lenient().when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(userB));

        List<PostCommentDTO> result = postCommentService.getAllComments();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void getAllComments_oneComment_postComment_check() {
        lenient().when(postCommentRepository.findAll()).thenReturn(Collections.singletonList(postComment1));
        lenient().when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(userA));
        lenient().when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(userB));

        List<PostCommentDTO> result = postCommentService.getAllComments();
        PostCommentDTO dto = result.get(0);
        assertEquals(postComment1.getContent(), dto.getContent());
        assertEquals(postComment1.getTimestamp(), dto.getTimestamp());
    }

    @Test
    public void getAllComments_oneComment_userEmail_check() {
        lenient().when(postCommentRepository.findAll()).thenReturn(Collections.singletonList(postComment1));
        lenient().when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(userA));
        lenient().when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(userB));

        List<PostCommentDTO> result = postCommentService.getAllComments();
        PostCommentDTO dto = result.get(0);
        assertEquals(userA.getEmail(), dto.getCommentSenderUserProfileDTO().email());
        assertEquals(userB.getEmail(), dto.getPostSenderUserProfileDTO().email());
    }

    @Test
    public void getAllComments_twoComments_return_check() {
        lenient().when(postCommentRepository.findAll()).thenReturn(Arrays.asList(postComment1, postComment2));
        lenient().when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(userA));
        lenient().when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(userB));

        List<PostCommentDTO> result = postCommentService.getAllComments();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void getAllComments_twoComments_postComment_check() {
        lenient().when(postCommentRepository.findAll()).thenReturn(Arrays.asList(postComment1, postComment2));
        lenient().when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(userA));
        lenient().when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(userB));

        List<PostCommentDTO> result = postCommentService.getAllComments();
        PostCommentDTO dto1 = result.get(0);
        PostCommentDTO dto2 = result.get(1);
        assertEquals(postComment1.getContent(), dto1.getContent());
        assertEquals(postComment1.getTimestamp(), dto1.getTimestamp());
        assertEquals(postComment2.getContent(), dto2.getContent());
        assertEquals(postComment2.getTimestamp(), dto2.getTimestamp());
    }

    @Test
    public void getAllComments_twoComments_userEmail_check() {
        lenient().when(postCommentRepository.findAll()).thenReturn(Arrays.asList(postComment1, postComment2));
        lenient().when(userProfileRepository.findById("d@dal.ca")).thenReturn(Optional.of(userA));
        lenient().when(userProfileRepository.findById("hn582183@dal.ca")).thenReturn(Optional.of(userB));

        List<PostCommentDTO> result = postCommentService.getAllComments();
        PostCommentDTO dto1 = result.get(0);
        PostCommentDTO dto2 = result.get(1);
        assertEquals(userA.getEmail(), dto1.getCommentSenderUserProfileDTO().email());
        assertEquals(userB.getEmail(), dto1.getPostSenderUserProfileDTO().email());
        assertEquals(userA.getEmail(), dto2.getCommentSenderUserProfileDTO().email());
        assertEquals(userB.getEmail(), dto2.getPostSenderUserProfileDTO().email());
    }

    @Test
    public void getAllComments_noComments() {
        when(postCommentRepository.findAll()).thenReturn(Collections.emptyList());
        List<PostCommentDTO> result = postCommentService.getAllComments();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllComments_postNotFound() {
        lenient().when(postRepository.findById(1)).thenReturn(Optional.empty());
        List<PostCommentDTO> result = postCommentService.getAllComments();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

}