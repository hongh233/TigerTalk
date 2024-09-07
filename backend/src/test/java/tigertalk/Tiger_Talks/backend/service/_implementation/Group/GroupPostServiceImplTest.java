package tigertalk.Tiger_Talks.backend.service._implementation.Group;

import tigertalk.model.Group.*;
import tigertalk.model.Notification.Notification;
import tigertalk.model.User.UserProfile;
import tigertalk.repository.Group.GroupPostLikeRepository;
import tigertalk.repository.Group.GroupPostRepository;
import tigertalk.repository.Group.GroupRepository;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.Notification.NotificationService;
import tigertalk.service._implementation.Group.GroupPostServiceImpl;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class GroupPostServiceImplTest {

    @InjectMocks
    private GroupPostServiceImpl groupPostService;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupPostRepository groupPostRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private GroupPostLikeRepository groupPostLikeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test case for createGroupPost
     */

    /**
     * Test case for deleteGroupPostById
     */
    @Test
    public void deleteGroupPostById_existOne_deleteOne() {
        int groupPostId = 1;
        when(groupPostRepository.existsById(groupPostId)).thenReturn(true);
        Optional<String> result = groupPostService.deleteGroupPostById(groupPostId);
        assertTrue(result.isEmpty());
    }

    @Test
    public void deleteGroupPostById_notFound() {
        int groupPostId = 1;
        when(groupPostRepository.existsById(groupPostId)).thenReturn(false);
        Optional<String> result = groupPostService.deleteGroupPostById(groupPostId);
        assertTrue(result.isPresent());
        assertEquals("Group post id not found, fail to delete group post.", result.get());
    }



    /**
     * Test case for getAllGroupPostsByGroupId
     */




    @Test
    public void getAllGroupPostsByGroupId_groupNotFound() {
        int groupId = 1;
        when(groupRepository.findById(groupId)).thenReturn(Optional.empty());
        List<GroupPostDTO> result = groupPostService.getAllGroupPostsByGroupId(groupId);
        assertTrue(result.isEmpty());
    }

    /**
     * Test case for likePost
     */






    /**
     * Test case for findUserProfileByEmail
     */



}