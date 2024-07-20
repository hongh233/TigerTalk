package com.group2.Tiger_Talks.backend.service._implementation.Group;

import com.group2.Tiger_Talks.backend.model.Group.*;
import com.group2.Tiger_Talks.backend.model.Notification.Notification;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Group.GroupMembershipRepository;
import com.group2.Tiger_Talks.backend.repository.Group.GroupRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Notification.NotificationService;
import com.group2.Tiger_Talks.backend.service._implementation.Notification.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@ExtendWith(MockitoExtension.class)
public class GroupServiceImplTest {
    static final String[] interests = {"music", "games", "study", "memes", "toys", "school"};


    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupMembershipRepository groupMembershipRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private GroupServiceImpl groupService;

    private UserProfile userA;
    private UserProfile userB;
    private Group groupPub;
    private Group group2Pub;
    private Group group4Private;
    private GroupMembership groupMembership;
    private GroupMembership groupMembership1;
    private GroupMembership groupMembership2;
    private GroupMembership groupMembership3;

    @BeforeEach
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

        groupPub = new Group("Test Group", false, interests[1]);
        groupPub.setGroupId(1);

        group2Pub = new Group("Test Group2", false, interests[2]);
        group2Pub.setGroupId(2);

<<<<<<< src/test/java/com/group2/Tiger_Talks/backend/service/_implementation/Group/GroupServiceImplTest.java
        Group group3Pub = new Group("Test Group3", false);
=======
        group3Pub = new Group("Test Group3", false, interests[4]);
>>>>>>> src/test/java/com/group2/Tiger_Talks/backend/service/_implementation/Group/GroupServiceImplTest.java
        group3Pub.setGroupId(3);

        group4Private = new Group("Private Group", true, interests[5]);
        group4Private.setGroupId(4);

        groupMembership = new GroupMembership();
        groupMembership.setGroupMembershipId(1);

        groupMembership1 = new GroupMembership();
        groupMembership1.setGroupMembershipId(2);
        groupMembership1.setGroup(groupPub);
        groupMembership1.setUserProfile(userA);

        groupMembership2 = new GroupMembership();
        groupMembership2.setGroupMembershipId(3);
        groupMembership2.setGroup(groupPub);
        groupMembership2.setUserProfile(userB);

        groupMembership3 = new GroupMembership();
        groupMembership3.setGroupMembershipId(4);
        groupMembership3.setGroup(group3Pub);
        groupMembership3.setUserProfile(userA);

        GroupMembership groupMembership4Private = new GroupMembership();
        groupMembership4Private.setGroupMembershipId(5);
        groupMembership4Private.setGroup(group4Private);
        groupMembership4Private.setUserProfile(userA);
    }

    /**
     * Test case for createGroup
     */
    @Test
    public void createGroup_userNotFound() {
        Optional<String> result = groupService.createGroup(
                "No User",
                "noUser@dal.ca",
                true,
                interests[0]
        );
        assertTrue(result.isPresent());
        assertEquals("User not found", result.get());
    }

    @Test
    public void createGroup_normal() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
<<<<<<< src/test/java/com/group2/Tiger_Talks/backend/service/_implementation/Group/GroupServiceImplTest.java
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());

        Optional<String> result = groupService.createGroup("Group A", "a@dal.ca", true);
=======
        Optional<String> result = groupService.createGroup(
                "Group A",
                "a@dal.ca",
                true,
                interests[0]
        );
>>>>>>> src/test/java/com/group2/Tiger_Talks/backend/service/_implementation/Group/GroupServiceImplTest.java
        assertTrue(result.isEmpty());
    }

    @Test
    public void createGroup_notificationCreation() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());

        groupService.createGroup("Group A", "a@dal.ca", true);
        verify(notificationService, times(1)).createNotification(argThat(notification ->
                notification.getUserProfile().equals(userA) &&
                        notification.getContent().contains("Group A") &&
                        notification.getNotificationType().equals("GroupCreation")
        ));
    }

    @Test
    public void createGroup_notification_correct_receiver() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());
        groupService.createGroup("Group A", "a@dal.ca", true);

        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationService).createNotification(notificationCaptor.capture());
        Notification capturedNotification = notificationCaptor.getValue();
        assertNotNull(capturedNotification);
        assertEquals(userA, capturedNotification.getUserProfile());
    }

    @Test
    public void createGroup_notification_correct_content() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());
        groupService.createGroup("Group A", "a@dal.ca", true);

        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationService).createNotification(notificationCaptor.capture());
        Notification capturedNotification = notificationCaptor.getValue();
        assertEquals("You have successfully created the group: Group A", capturedNotification.getContent());
        assertEquals("GroupCreation", capturedNotification.getNotificationType());
    }

    /**
     * Test case for joinGroup
     */
    @Test
    public void joinGroup_User_normal() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(groupRepository.findById(1)).thenReturn(Optional.of(groupPub));
        Optional<String> result = groupService.joinGroupUser("a@dal.ca", 1);
        assertTrue(result.isEmpty());
    }

    @Test
    public void joinGroup_UserToPrivateGroupFails() {
        when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
        when(groupRepository.findById(4)).thenReturn(Optional.of(group4Private));
        Optional<String> result = groupService.joinGroupUser("b@dal.ca", 4);
        assertTrue(result.isPresent());
    }

    @Test
    public void joinGroup_UserToPrivateGroupByAdminPasses() {
        when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
        when(groupRepository.findById(4)).thenReturn(Optional.of(group4Private));
        Optional<String> result = groupService.joinGroupAdmin("b@dal.ca", 4);
        assertTrue(result.isEmpty());
    }

    @Test
    public void joinGroup_UserAlreadyGroupUserMember() {
        GroupMembership existingMembership = new GroupMembership(groupPub, userA, true);
        groupPub.setGroupMemberList(List.of(existingMembership));
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(groupRepository.findById(1)).thenReturn(Optional.of(groupPub));
        Optional<String> result = groupService.joinGroupUser("a@dal.ca", 1);
        assertTrue(result.isPresent());
        assertEquals("User is already a member of the group", result.get());
    }

    @Test
    public void joinGroup_User_userNotFound() {
        GroupMembership existingMembership = new GroupMembership(groupPub, userA, true);
        groupPub.setGroupMemberList(List.of(existingMembership));
        Optional<String> result = groupService.joinGroupUser("noFound@dal.ca", 1);
        assertTrue(result.isPresent());
        assertEquals("User not found", result.get());
    }

    @Test
    public void joinGroup_groupUserNotFound() {
        GroupMembership existingMembership = new GroupMembership(groupPub, userA, true);
        groupPub.setGroupMemberList(List.of(existingMembership));
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        Optional<String> result = groupService.joinGroupUser("a@dal.ca", 1);
        assertTrue(result.isPresent());
        assertEquals("Group not found", result.get());
    }

    /**
     * Test case for getAllGroups
     */
    @Test
    public void getAllGroups_normal() {
        when(groupRepository.findAll()).thenReturn(List.of(groupPub, group2Pub));
        List<GroupDTO> groups = groupService.getAllGroups();
        assertEquals(2, groups.size());
        assertEquals("Test Group", groups.get(0).groupName());
        assertEquals("Test Group2", groups.get(1).groupName());
    }

    @Test
    public void getAllGroups_oneGroup() {
        when(groupRepository.findAll()).thenReturn(List.of(groupPub));
        List<GroupDTO> groups = groupService.getAllGroups();
        assertEquals(1, groups.size());
        assertEquals("Test Group", groups.get(0).groupName());
    }

    @Test
    public void getAllGroups_noGroups() {
        when(groupRepository.findAll()).thenReturn(List.of());
        List<GroupDTO> groups = groupService.getAllGroups();
        assertEquals(0, groups.size());
    }

    /**
     * Test case for getGroup
     */
    @Test
    public void getGroup() {
        int groupID = 1;
        when(groupRepository.findById(groupID)).thenReturn(Optional.of(groupPub));
        Optional<GroupDTO> groupDTO = groupService.getGroup(groupID);
        groupDTO.ifPresentOrElse(group_dto -> {
<<<<<<< src/test/java/com/group2/Tiger_Talks/backend/service/_implementation/Group/GroupServiceImplTest.java
            assertEquals(1, group_dto.getGroupId());
            assertEquals("Test Group", group_dto.getGroupName());
        }, () -> fail("No group found for id: " + groupID));
=======
            assertEquals(1, group_dto.groupId());
            assertEquals("Test Group", group_dto.groupName());
            assertEquals(interests[1], group_dto.interest());
        }, () -> {
            fail("No group found for id: " + groupID);
        });
>>>>>>> src/test/java/com/group2/Tiger_Talks/backend/service/_implementation/Group/GroupServiceImplTest.java
    }

    @Test
    public void getGroup_NotFound() {
        when(groupRepository.findById(1)).thenReturn(Optional.empty());
        Optional<GroupDTO> groupDTO = groupService.getGroup(1);
        assertTrue(groupDTO.isEmpty());
    }

    /**
     * Test case for getAllGroupsByUser
     */
    @Test
    public void getAllGroupsByUser_userNotFound() {
        when(userProfileRepository.findById("noUser@dal.ca")).thenReturn(Optional.empty());
        List<GroupDTO> result = groupService.getAllGroupsByUser("noUser@dal.ca");
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllGroupsByUser_userHasNoGroups() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(groupMembershipRepository.findByUserProfile_Email("a@dal.ca")).thenReturn(Collections.emptyList());
        List<GroupDTO> result = groupService.getAllGroupsByUser("a@dal.ca");
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllGroupsByUser_userHasMultipleGroups() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(groupMembershipRepository.findByUserProfile_Email("a@dal.ca")).thenReturn(List.of(groupMembership1, groupMembership3));

        List<GroupDTO> result = groupService.getAllGroupsByUser("a@dal.ca");

        assertEquals(2, result.size());
        assertEquals("Test Group", result.get(0).groupName());
        assertEquals("Test Group3", result.get(1).groupName());
    }

    @Test
    public void getAllGroupsByUser_userHasOneGroup() {
        when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
        when(groupMembershipRepository.findByUserProfile_Email("b@dal.ca")).thenReturn(List.of(groupMembership2));

        List<GroupDTO> result = groupService.getAllGroupsByUser("b@dal.ca");

        assertEquals(1, result.size());
        assertEquals("Test Group", result.get(0).groupName());
    }

    /**
     * Test case for updateGroupInfo
     */
    @Test
    public void updateGroupInfo_groupIdNotFound() {
        GroupDTO groupUpdate = new GroupDTO(1, "New Group Name", "aaa.png", true, interests[0]);
        when(groupRepository.findById(1)).thenReturn(Optional.empty());
        Optional<String> result = groupService.updateGroupInfo(groupUpdate);
        assertTrue(result.isPresent());
        assertEquals("Group id not found", result.get());
    }

    @Test
    public void updateGroupInfo_successfulUpdate_correctMessage() {
        GroupDTO groupUpdate = new GroupDTO(1, "New Group Name", "aaa.png", true, interests[0]);
        when(groupRepository.findById(1)).thenReturn(Optional.of(groupPub));

        Optional<String> result = groupService.updateGroupInfo(groupUpdate);
        assertTrue(result.isEmpty());
    }

    @Test
    public void updateGroupInfo_successfulUpdate_groupName() {
        GroupDTO groupUpdate = new GroupDTO(1, "New Group Name", "aaa.png", true, interests[0]);
        when(groupRepository.findById(1)).thenReturn(Optional.of(groupPub));
        groupService.updateGroupInfo(groupUpdate);
        assertEquals("New Group Name", groupPub.getGroupName());
    }

    @Test
    public void updateGroupInfo_successfulUpdate_groupImg() {
        GroupDTO groupUpdate = new GroupDTO(1, "New Group Name", "aaa.png", true, interests[0]);
        when(groupRepository.findById(1)).thenReturn(Optional.of(groupPub));
        groupService.updateGroupInfo(groupUpdate);
        assertEquals("aaa.png", groupPub.getGroupImg());
    }

    @Test
    public void updateGroupInfo_successfulUpdate_isPrivateStatus() {
        GroupDTO groupUpdate = new GroupDTO(1, "New Group Name", "aaa.png", true, interests[0]);
        when(groupRepository.findById(1)).thenReturn(Optional.of(groupPub));
        groupService.updateGroupInfo(groupUpdate);
        assertTrue(groupPub.isPrivate());
    }

    @Test
    public void updateGroupInfo_noChange() {
        GroupDTO groupUpdate = new GroupDTO(
                group2Pub.getGroupId(),
                group2Pub.getGroupName(),
                group2Pub.getGroupImg(),
                group2Pub.isPrivate(),
                interests[0]
        );
        when(groupRepository.findById(2)).thenReturn(Optional.of(group2Pub));

        Optional<String> result = groupService.updateGroupInfo(groupUpdate);
        assertTrue(result.isEmpty());
        assertEquals("Test Group2", group2Pub.getGroupName());
        assertFalse(group2Pub.isPrivate());
    }

    /**
     * Test case for deleteGroup
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
        when(groupRepository.findById(1)).thenReturn(Optional.of(groupPub)).thenReturn(Optional.empty());
        Optional<String> result = groupService.deleteGroup(1);
        assertTrue(result.isEmpty());
    }

    @Test
    public void deleteGroup_successfulDelete_notExistInRepository() {
        when(groupRepository.findById(1)).thenReturn(Optional.of(groupPub));
        groupService.deleteGroup(1);
        ArgumentCaptor<Group> groupCaptor = ArgumentCaptor.forClass(Group.class);
        verify(groupRepository).delete(groupCaptor.capture());
        assertEquals(groupPub, groupCaptor.getValue());
        when(groupRepository.findById(1)).thenReturn(Optional.empty());
        assertTrue(groupRepository.findById(1).isEmpty());
    }

    /**
     * Test case for deleteGroupMembership
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
        assertTrue(result.isEmpty());
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
     * Test case for getGroupMembersByGroupId
     */
    @Test
    public void getGroupMembersByGroupId() {
        when(groupMembershipRepository.findByGroup_GroupId(1)).thenReturn(Arrays.asList(groupMembership1, groupMembership2));
        List<GroupMembershipDTO> result = groupService.getGroupMembersByGroupId(1);

        assertEquals(2, result.size());
        assertEquals(2, result.get(0).groupMembershipId());
        assertEquals(3, result.get(1).groupMembershipId());
        assertEquals("a@dal.ca", result.get(0).userProfileDTO().email());
        assertEquals("b@dal.ca", result.get(1).userProfileDTO().email());
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
        assertEquals(2, result.get(0).groupMembershipId());
        assertEquals("a@dal.ca", result.get(0).userProfileDTO().email());
    }

    /**
     * Test case for isMember
     */
    @Test
    public void isMember_userGetMemberShipId() {
        when(groupMembershipRepository.findByGroup_GroupId(1)).thenReturn(List.of(groupMembership1, groupMembership2));
        assertTrue(groupService.getMemberShipId("a@dal.ca", 1).isPresent());
    }

    @Test
    public void isMember_userGetNotMemberShipId() {
        when(groupMembershipRepository.findByGroup_GroupId(1)).thenReturn(List.of(groupMembership2));
        assertFalse(groupService.getMemberShipId("a@dal.ca", 1).isPresent());
    }

    @Test
    public void getMember_ShipId_noMembersInGroup() {
        when(groupMembershipRepository.findByGroup_GroupId(1)).thenReturn(Collections.emptyList());
        assertFalse(groupService.getMemberShipId("a@dal.ca", 1).isPresent());
    }


}