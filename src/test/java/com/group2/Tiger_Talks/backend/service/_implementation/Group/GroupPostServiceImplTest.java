package com.group2.Tiger_Talks.backend.service._implementation.Group;

import com.group2.Tiger_Talks.backend.model.Group.Group;
import com.group2.Tiger_Talks.backend.model.Group.GroupMembership;
import com.group2.Tiger_Talks.backend.model.Group.GroupPost;
import com.group2.Tiger_Talks.backend.model.Group.GroupPostDTO;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Group.GroupPostRepository;
import com.group2.Tiger_Talks.backend.repository.Group.GroupRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

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


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test case for createGroupPost
     */
    @Test
    public void createGroupPost_success() {
        Group group = new Group();
        group.setGroupId(1);
        GroupPost groupPost = new GroupPost(group, "Content", "a@dal.ca", "picture");
        when(userProfileRepository.existsById(anyString())).thenReturn(true);
        when(groupRepository.existsById(anyInt())).thenReturn(true);
        Optional<String> result = groupPostService.createGroupPost(groupPost);
        assertTrue(result.isEmpty());
    }

    @Test
    public void createGroupPost_userNotFound() {
        Group group = new Group();
        group.setGroupId(1);
        GroupPost groupPost = new GroupPost(group, "Content", "a@dal.ca", "picture");
        lenient().when(userProfileRepository.existsById(anyString())).thenReturn(false);
        lenient().when(groupRepository.existsById(anyInt())).thenReturn(true);
        Optional<String> result = groupPostService.createGroupPost(groupPost);
        assertTrue(result.isPresent());
        assertEquals("User or Group not found, fail to create group post.", result.get());
    }

    @Test
    public void createGroupPost_groupNotFound() {
        Group group = new Group();
        group.setGroupId(1);
        GroupPost groupPost = new GroupPost(group, "Content", "a@dal.ca", "picture");
        when(userProfileRepository.existsById(anyString())).thenReturn(true);
        when(groupRepository.existsById(anyInt())).thenReturn(false);
        Optional<String> result = groupPostService.createGroupPost(groupPost);
        assertTrue(result.isPresent());
        assertEquals("User or Group not found, fail to create group post.", result.get());
    }

    @Test
    public void createGroupPost_userAndGroupAllNotFound() {
        Group group = new Group();
        group.setGroupId(1);
        GroupPost groupPost = new GroupPost(group, "Content", "a@dal.ca", "picture");
        lenient().when(userProfileRepository.existsById(anyString())).thenReturn(false);
        lenient().when(groupRepository.existsById(anyInt())).thenReturn(false);
        Optional<String> result = groupPostService.createGroupPost(groupPost);
        assertTrue(result.isPresent());
        assertEquals("User or Group not found, fail to create group post.", result.get());
    }

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

    @Test
    public void deleteGroupPostById_existTwo_deleteOne() {
        int groupPostId1 = 1;
        int groupPostId2 = 2;
        Group group = new Group();
        group.setGroupId(1);
        GroupPost groupPost1 = new GroupPost(group, "Content1", "a@dal.ca", "picture");
        GroupPost groupPost2 = new GroupPost(group, "Content2", "b@dal.ca", "picture");
        groupPost1.setGroupPostId(groupPostId1);
        groupPost2.setGroupPostId(groupPostId2);

        lenient().when(groupPostRepository.existsById(groupPostId1)).thenReturn(true);
        lenient().when(groupPostRepository.existsById(groupPostId2)).thenReturn(true);
        lenient().when(groupPostRepository.findById(groupPostId2)).thenReturn(Optional.of(groupPost2));

        // delete the first post
        Optional<String> result = groupPostService.deleteGroupPostById(groupPostId1);
        assertTrue(result.isEmpty());

        // ensure the second is still exist
        Optional<GroupPost> remainingGroupPost = groupPostRepository.findById(groupPostId2);
        assertTrue(remainingGroupPost.isPresent());
        assertEquals(groupPost2.getGroupPostId(), remainingGroupPost.get().getGroupPostId());
    }

    /**
     * Test case for getAllGroupPostsByGroupId
     */
    @Test
    public void getAllGroupPostsByGroupId_success_twoPosts() {
        int groupId = 1;
        Group group = new Group();
        group.setGroupId(groupId);

        UserProfile userProfile1 = new UserProfile();
        userProfile1.setEmail("a@dal.ca");
        userProfile1.setUserName("User A");
        userProfile1.setProfilePictureUrl("urlA");
        UserProfile userProfile2 = new UserProfile();
        userProfile2.setEmail("b@dal.ca");
        userProfile2.setUserName("User B");
        userProfile2.setProfilePictureUrl("urlB");

        GroupMembership membership1 = new GroupMembership();
        membership1.setUserProfile(userProfile1);
        GroupMembership membership2 = new GroupMembership();
        membership2.setUserProfile(userProfile2);
        group.setGroupMemberList(Arrays.asList(membership1, membership2));

        GroupPost groupPost1 = new GroupPost(group, "Content1", "a@dal.ca", "picture1");
        groupPost1.setPostCreateTime(LocalDateTime.of(2020, 12, 2, 3, 4));
        GroupPost groupPost2 = new GroupPost(group, "Content2", "b@dal.ca", "picture2");
        groupPost2.setPostCreateTime(LocalDateTime.of(2024, 12, 2, 3, 4));
        groupPost1.setGroupPostId(1);
        groupPost2.setGroupPostId(2);
        group.setGroupPostList(Arrays.asList(groupPost1, groupPost2));
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        List<GroupPostDTO> result = groupPostService.getAllGroupPostsByGroupId(groupId);
        assertEquals(2, result.size());
        assertEquals("Content2", result.get(0).getGroupPostContent());
        assertEquals("Content1", result.get(1).getGroupPostContent());
        assertEquals("User B", result.get(0).getGroupPostSenderUserName());
        assertEquals("User A", result.get(1).getGroupPostSenderUserName());
    }

    @Test
    public void getAllGroupPostsByGroupId_success_onePost() {
        int groupId = 1;
        Group group = new Group();
        group.setGroupId(groupId);

        UserProfile userProfile = new UserProfile();
        userProfile.setEmail("a@dal.ca");
        userProfile.setUserName("User A");
        userProfile.setProfilePictureUrl("urlA");

        GroupMembership membership = new GroupMembership();
        membership.setUserProfile(userProfile);
        group.setGroupMemberList(List.of(membership));

        GroupPost groupPost = new GroupPost(group, "Content1", "a@dal.ca", "picture");
        groupPost.setGroupPostId(1);
        group.setGroupPostList(List.of(groupPost));
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        List<GroupPostDTO> result = groupPostService.getAllGroupPostsByGroupId(groupId);

        assertEquals(1, result.size());
        assertEquals("Content1", result.get(0).getGroupPostContent());
        assertEquals("User A", result.get(0).getGroupPostSenderUserName());
        assertEquals("urlA", result.get(0).getGroupPostSenderProfilePictureURL());
    }

    @Test
    public void getAllGroupPostsByGroupId_groupNotFound() {
        int groupId = 1;
        when(groupRepository.findById(groupId)).thenReturn(Optional.empty());
        List<GroupPostDTO> result = groupPostService.getAllGroupPostsByGroupId(groupId);
        assertTrue(result.isEmpty());
    }

}