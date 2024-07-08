package com.group2.Tiger_Talks.backend.service._implementation.Group;

import com.group2.Tiger_Talks.backend.model.Group.*;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Group.GroupMembershipRepository;
import com.group2.Tiger_Talks.backend.repository.Group.GroupRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import org.apache.catalina.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GroupServiceImplTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupMembershipRepository groupMembershipRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private GroupServiceImpl groupService;

    private UserProfile userA;
    private UserProfile userB;
    private Group group;
    private Group group2;
    private GroupMembership groupMembership;
    private GroupMembership groupMembership1;
    private GroupMembership groupMembership2;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userA = new UserProfile(
                "Along", "Aside", 22, "Male", "userA", "a@dal.ca", "aaaa1A@a",
                new String[]{"1", "2", "3"},
                new String[]{
                        "What was your favourite book as a child?",
                        "In what city were you born?",
                        "What is the name of the hospital where you were born?"
                }
        );
        userB = new UserProfile(
                "Beach", "Boring", 21, "Male", "userB", "b@dal.ca", "aaaa1A@a",
                new String[]{"1", "2", "3"},
                new String[]{
                        "What was your favourite book as a child?",
                        "In what city were you born?",
                        "What is the name of the hospital where you were born?"
                }
        );
        group = new Group("Test Group", true);
        group.setGroupId(1);
        group2 = new Group("Test Group2", true);
        group2.setGroupId(2);
        groupMembership = new GroupMembership();
        groupMembership.setGroupMembershipId(1);


        groupMembership1 = new GroupMembership();
        groupMembership1.setGroupMembershipId(2);
        groupMembership1.setGroup(group);
        groupMembership1.setUserProfile(userA);

        groupMembership2 = new GroupMembership();
        groupMembership2.setGroupMembershipId(3);
        groupMembership2.setGroup(group);
        groupMembership2.setUserProfile(userB);
    }

    /**
     *  Test case for createGroup
     */
    @Test
    public void createGroup_userNotFound() {
        Optional<String> result = groupService.createGroup(
                "No User",
                "noUser@dal.ca",
                true);
        assertTrue(result.isPresent());
        assertEquals("User not found", result.get());
    }

    @Test
    public void createGroup_normal() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        Optional<String> result = groupService.createGroup(
                "Group A",
                "a@dal.ca",
                true);
        assertTrue(result.isPresent());
        assertEquals("Group successfully created", result.get());
    }

    /**
     *  Test case for joinGroup
     */
    @Test
    public void joinGroup_normal() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(groupRepository.findById(1)).thenReturn(Optional.of(group));
        Optional<String> result = groupService.joinGroup("a@dal.ca", 1);

        assertTrue(result.isPresent());
        assertEquals("User successfully joined the group", result.get());
    }

    @Test
    public void joinGroup_userAlreadyGroupMember() {
        GroupMembership existingMembership = new GroupMembership(group, userA, true);
        group.setGroupMemberList(List.of(existingMembership));
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(groupRepository.findById(1)).thenReturn(Optional.of(group));
        Optional<String> result = groupService.joinGroup("a@dal.ca", 1);
        assertTrue(result.isPresent());
        assertEquals("User is already a member of the group", result.get());
    }

    @Test
    public void joinGroup_userNotFound() {
        GroupMembership existingMembership = new GroupMembership(group, userA, true);
        group.setGroupMemberList(List.of(existingMembership));
        Optional<String> result = groupService.joinGroup("noFound@dal.ca", 1);
        assertTrue(result.isPresent());
        assertEquals("User not found", result.get());
    }

    @Test
    public void joinGroup_groupNotFound() {
        GroupMembership existingMembership = new GroupMembership(group, userA, true);
        group.setGroupMemberList(List.of(existingMembership));
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        Optional<String> result = groupService.joinGroup("a@dal.ca", 1);
        assertTrue(result.isPresent());
        assertEquals("Group not found", result.get());
    }

    /**
     *  Test case for getAllGroups
     */
    @Test
    public void getAllGroups_normal() {
        when(groupRepository.findAll()).thenReturn(List.of(group, group2));
        List<GroupDTO> groups = groupService.getAllGroups();
        assertEquals(2, groups.size());
        assertEquals("Test Group", groups.get(0).getGroupName());
        assertEquals("Test Group2", groups.get(1).getGroupName());
    }

    @Test
    public void getAllGroups_oneGroup() {
        when(groupRepository.findAll()).thenReturn(List.of(group));
        List<GroupDTO> groups = groupService.getAllGroups();
        assertEquals(1, groups.size());
        assertEquals("Test Group", groups.get(0).getGroupName());
    }

    @Test
    public void getAllGroups_noGroups() {
        when(groupRepository.findAll()).thenReturn(List.of());
        List<GroupDTO> groups = groupService.getAllGroups();
        assertEquals(0, groups.size());
    }

    /**
     *  Test case for getGroup
     */
    @Test
    public void getGroup() {
        when(groupRepository.findById(1)).thenReturn(Optional.of(group));
        GroupDTO groupDTO = groupService.getGroup(1);
        assertEquals(1, groupDTO.getGroupId());
        assertEquals("Test Group", groupDTO.getGroupName());
    }

    @Test
    public void getGroup_NotFound() {
        when(groupRepository.findById(1)).thenReturn(Optional.empty());
        GroupDTO groupDTO = groupService.getGroup(1);
        assertNull(groupDTO);
    }

    /**
     *  Test case for updateGroupInfo
     */
    @Test
    public void updateGroupInfo_groupIdNotFound() {
        GroupUpdate groupUpdate = new GroupUpdate(1, "New Group Name", "aaa.png", true);
        when(groupRepository.findById(1)).thenReturn(Optional.empty());
        Optional<String> result = groupService.updateGroupInfo(groupUpdate);
        assertTrue(result.isPresent());
        assertEquals("Group id not found", result.get());
    }

    @Test
    public void updateGroupInfo_successfulUpdate_correctMessage() {
        GroupUpdate groupUpdate = new GroupUpdate(1, "New Group Name", "aaa.png", true);
        when(groupRepository.findById(1)).thenReturn(Optional.of(group));

        Optional<String> result = groupService.updateGroupInfo(groupUpdate);
        assertTrue(result.isPresent());
        assertEquals("Group Info Successfully updated", result.get());
    }

    @Test
    public void updateGroupInfo_successfulUpdate_groupName() {
        GroupUpdate groupUpdate = new GroupUpdate(1, "New Group Name", "aaa.png", true);
        when(groupRepository.findById(1)).thenReturn(Optional.of(group));
        groupService.updateGroupInfo(groupUpdate);
        assertEquals("New Group Name", group.getGroupName());
    }

    @Test
    public void updateGroupInfo_successfulUpdate_groupImg() {
        GroupUpdate groupUpdate = new GroupUpdate(1, "New Group Name", "aaa.png", true);
        when(groupRepository.findById(1)).thenReturn(Optional.of(group));
        groupService.updateGroupInfo(groupUpdate);
        assertEquals("aaa.png", group.getGroupImg());
    }

    @Test
    public void updateGroupInfo_successfulUpdate_isPrivateStatus() {
        GroupUpdate groupUpdate = new GroupUpdate(1, "New Group Name", "aaa.png", true);
        when(groupRepository.findById(1)).thenReturn(Optional.of(group));
        groupService.updateGroupInfo(groupUpdate);
        assertTrue(group.isPrivate());
    }

    @Test
    public void updateGroupInfo_noChange() {
        GroupUpdate groupUpdate = new GroupUpdate(group2.getGroupId(),
                group2.getGroupName(),
                group2.getGroupImg(),
                group2.isPrivate());
        when(groupRepository.findById(2)).thenReturn(Optional.of(group2));

        Optional<String> result = groupService.updateGroupInfo(groupUpdate);
        assertTrue(result.isPresent());
        assertEquals("Group Info Successfully updated", result.get());
        assertEquals("Test Group2", group2.getGroupName());
        assertNull(group2.getGroupImg());
        assertTrue(group2.isPrivate());
    }

    /**
     *  Test case for deleteGroup
     */
    @Test
    public void deleteGroup_groupIdNotFound() {
        when(groupRepository.findById(1)).thenReturn(Optional.empty());
        Optional<String> result = groupService.deleteGroup(1);
        assertTrue(result.isPresent());
        assertEquals("Group id not found", result.get());
    }

    @Test
    public void deleteGroup_successfulDelete_true_message() {
        when(groupRepository.findById(1)).thenReturn(Optional.of(group)).thenReturn(Optional.empty());
        Optional<String> result = groupService.deleteGroup(1);
        assertTrue(result.isPresent());
        assertEquals("Group Successfully deleted", result.get());
    }

    @Test
    public void deleteGroup_successfulDelete_notExistInRepository() {
        when(groupRepository.findById(1)).thenReturn(Optional.of(group));
        groupService.deleteGroup(1);
        ArgumentCaptor<Group> groupCaptor = ArgumentCaptor.forClass(Group.class);
        verify(groupRepository).delete(groupCaptor.capture());
        assertEquals(group, groupCaptor.getValue());
        when(groupRepository.findById(1)).thenReturn(Optional.empty());
        assertTrue(groupRepository.findById(1).isEmpty());
    }

    /**
     *  Test case for deleteGroupMembership
     */
    @Test
    public void deleteGroupMembership_membershipIdNotFound() {
        when(groupMembershipRepository.findById(1)).thenReturn(Optional.empty());
        Optional<String> result = groupService.deleteGroupMembership(1);
        assertTrue(result.isPresent());
        assertEquals("Group membership id not found", result.get());
    }

    @Test
    public void deleteGroupMembership_successfulDelete_correctMessage() {
        when(groupMembershipRepository.findById(1)).thenReturn(Optional.of(groupMembership));
        Optional<String> result = groupService.deleteGroupMembership(1);
        assertTrue(result.isPresent());
        assertEquals("Group membership Successfully deleted", result.get());
    }

    @Test
    public void deleteGroupMembership_successfulDelete_notExistInRepository() {
        when(groupMembershipRepository.findById(1)).thenReturn(Optional.of(groupMembership));
        groupService.deleteGroupMembership(1);
        ArgumentCaptor<GroupMembership> membershipCaptor = ArgumentCaptor.forClass(GroupMembership.class);
        verify(groupMembershipRepository).delete(membershipCaptor.capture());
        assertEquals(groupMembership, membershipCaptor.getValue());
        when(groupMembershipRepository.findById(1)).thenReturn(Optional.empty());
        assertTrue(groupMembershipRepository.findById(1).isEmpty());
    }

    /**
     *  Test case for getGroupMembersByGroupId
     */
    @Test
    public void getGroupMembersByGroupId() {
        when(groupMembershipRepository.findByGroup_GroupId(1)).thenReturn(Arrays.asList(groupMembership1, groupMembership2));
        List<GroupMembershipDTO> result = groupService.getGroupMembersByGroupId(1);

        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getGroupMembershipId());
        assertEquals(3, result.get(1).getGroupMembershipId());
        assertEquals("a@dal.ca", result.get(0).getUserProfileDTO().email());
        assertEquals("b@dal.ca", result.get(1).getUserProfileDTO().email());
    }

    @Test
    public void getGroupMembersByGroupId_empty() {
        when(groupMembershipRepository.findByGroup_GroupId(1)).thenReturn(Collections.emptyList());
        List<GroupMembershipDTO> result = groupService.getGroupMembersByGroupId(1);
        assertEquals(0, result.size());
    }

    @Test
    public void getGroupMembersByGroupId_oneElement() {
        when(groupMembershipRepository.findByGroup_GroupId(1)).thenReturn(Collections.singletonList(groupMembership1));
        List<GroupMembershipDTO> result = groupService.getGroupMembersByGroupId(1);

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getGroupMembershipId());
        assertEquals("a@dal.ca", result.get(0).getUserProfileDTO().email());
    }



}