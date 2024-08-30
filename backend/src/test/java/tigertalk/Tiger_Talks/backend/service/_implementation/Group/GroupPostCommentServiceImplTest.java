package tigertalk.Tiger_Talks.backend.service._implementation.Group;

import tigertalk.model.Group.*;
import tigertalk.model.Notification.Notification;
import tigertalk.model.User.UserProfile;
import tigertalk.repository.Group.GroupMembershipRepository;
import tigertalk.repository.Group.GroupPostCommentRepository;
import tigertalk.repository.Group.GroupPostRepository;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.Notification.NotificationService;
import tigertalk.service._implementation.Group.GroupPostCommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class GroupPostCommentServiceImplTest {

    @InjectMocks
    private GroupPostCommentServiceImpl groupPostCommentService;

    @Mock
    private GroupMembershipRepository groupMembershipRepository;

    @Mock
    private GroupPostRepository groupPostRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private GroupPostCommentRepository groupPostCommentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test case for createGroupPostComment
     */

    @Test
    public void createGroupPostComment_groupPostNotFound() {
        int groupPostId = 1;
        GroupPostComment groupPostComment = new GroupPostComment();
        when(groupPostRepository.findById(groupPostId)).thenReturn(Optional.empty());
        Optional<String> result = groupPostCommentService.createGroupPostComment(groupPostId, groupPostComment);
        assertTrue(result.isPresent());
        assertEquals("Group post id not found, fail to create group post comment.", result.get());
    }


    /**
     * Test case for deleteGroupPostCommentById
     */
    @Test
    public void deleteGroupPostCommentById_existOne_deleteOne() {
        int groupPostCommentId = 1;
        GroupPostComment groupPostComment = new GroupPostComment();
        groupPostComment.setGroupPostCommentId(groupPostCommentId);
        when(groupPostCommentRepository.findById(groupPostCommentId)).thenReturn(Optional.of(groupPostComment));
        Optional<String> result = groupPostCommentService.deleteGroupPostCommentById(groupPostCommentId);
        assertFalse(result.isPresent());
    }

    @Test
    public void deleteGroupPostCommentById_notFound() {
        int groupPostCommentId = 1;
        when(groupPostCommentRepository.findById(groupPostCommentId)).thenReturn(Optional.empty());
        Optional<String> result = groupPostCommentService.deleteGroupPostCommentById(groupPostCommentId);
        assertTrue(result.isPresent());
        assertEquals("Group post comment id not found, fail to delete group post comment.", result.get());
    }

    @Test
    public void deleteGroupPostCommentById_existTwo_deleteOne() {
        int groupPostCommentId1 = 1;
        int groupPostCommentId2 = 2;
        GroupPostComment groupPostComment1 = new GroupPostComment();
        groupPostComment1.setGroupPostCommentId(groupPostCommentId1);
        GroupPostComment groupPostComment2 = new GroupPostComment();
        groupPostComment2.setGroupPostCommentId(groupPostCommentId2);

        when(groupPostCommentRepository.findById(groupPostCommentId1)).thenReturn(Optional.of(groupPostComment1));
        when(groupPostCommentRepository.findById(groupPostCommentId2)).thenReturn(Optional.of(groupPostComment2));

        // delete the first GroupPostComment
        Optional<String> result = groupPostCommentService.deleteGroupPostCommentById(groupPostCommentId1);
        assertFalse(result.isPresent());

        // ensure the second GroupPostComment is still exist
        Optional<GroupPostComment> remainingGroupPostComment = groupPostCommentRepository.findById(groupPostCommentId2);
        assertTrue(remainingGroupPostComment.isPresent());
        assertEquals(groupPostCommentId2, remainingGroupPostComment.get().getGroupPostCommentId().intValue());
    }

    /**
     * Test case for getCommentsByGroupPostId
     */

    @Test
    public void getCommentsByGroupPostId_postNotFound() {
        int groupPostId = 1;
        when(groupPostRepository.findById(groupPostId)).thenReturn(Optional.empty());
        List<GroupPostCommentDTO> result = groupPostCommentService.getCommentsByGroupPostId(groupPostId);
        assertTrue(result.isEmpty());
    }
}