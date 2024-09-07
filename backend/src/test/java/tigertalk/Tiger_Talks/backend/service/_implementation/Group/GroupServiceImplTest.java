package tigertalk.Tiger_Talks.backend.service._implementation.Group;

import tigertalk.model.Group.Group;
import tigertalk.model.Group.GroupDTO;
import tigertalk.model.Group.GroupMembership;
import tigertalk.model.Group.GroupMembershipDTO;
import tigertalk.model.Notification.Notification;
import tigertalk.model.User.UserProfile;
import tigertalk.repository.Group.GroupMembershipRepository;
import tigertalk.repository.Group.GroupRepository;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.Notification.NotificationService;
import tigertalk.service._implementation.Group.GroupServiceImpl;
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
import static org.mockito.Mockito.*;
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
        userA = new UserProfile();
        userA.setFirstName("Along");
        userA.setLastName("Aside");
        userA.setBirthday("1980-01-01");
        userA.setGender("Male");
        userA.setUserName("userA");
        userA.setEmail("a@dal.ca");
        userA.setPassword("aaaa1A@a");
        userA.setSecurityQuestion("1");
        userA.setSecurityQuestionAnswer("What was your favourite book as a child?");

        userB = new UserProfile();
        userB.setFirstName("Beach");
        userB.setLastName("Boring");
        userB.setBirthday("1980-01-01");
        userB.setGender("Male");
        userB.setUserName("userB");
        userB.setEmail("b@dal.ca");
        userB.setPassword("aaaa1A@a");
        userB.setSecurityQuestion("1");
        userB.setSecurityQuestionAnswer("What was your favourite book as a child?");

        groupPub = new Group("Test Group", false, interests[1]);
        groupPub.setGroupId(1);

        group2Pub = new Group("Test Group2", false, interests[2]);
        group2Pub.setGroupId(2);


        Group group3Pub = new Group("Test Group3", false, interests[3]);

        group3Pub = new Group("Test Group3", false, interests[4]);

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

        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());

        Optional<String> result = groupService.createGroup("Group A", "a@dal.ca", true, interests[0]);
        assertTrue(result.isEmpty());
    }

    @Test
    public void createGroup_notificationCreation() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());

        groupService.createGroup("Group A", "a@dal.ca", true, interests[0]);
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
        groupService.createGroup("Group A", "a@dal.ca", true, "");

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
        groupService.createGroup("Group A", "a@dal.ca", true, interests[0]);

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
        Optional<String> result = groupService.joinGroup("a@dal.ca", 1);
        assertTrue(result.isEmpty());
    }

    @Test
    public void joinGroup_UserToPrivateGroupFails() {
        when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
        when(groupRepository.findById(4)).thenReturn(Optional.of(group4Private));
        Optional<String> result = groupService.joinGroup("b@dal.ca", 4);
        assertTrue(result.isPresent());
    }

    @Test
    public void joinGroup_UserToPrivateGroupByAdminPasses() {
        when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
        when(groupRepository.findById(4)).thenReturn(Optional.of(group4Private));
        Optional<String> result = groupService.joinGroup("b@dal.ca", 4);
        assertTrue(result.isEmpty());
    }

    @Test
    public void joinGroup_UserAlreadyGroupUserMember() {
        GroupMembership existingMembership = new GroupMembership(groupPub, userA, true);
        groupPub.setGroupMemberList(List.of(existingMembership));
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(groupRepository.findById(1)).thenReturn(Optional.of(groupPub));
        Optional<String> result = groupService.joinGroup("a@dal.ca", 1);
        assertTrue(result.isPresent());
        assertEquals("User is already a member of the group", result.get());
    }

    @Test
    public void joinGroup_User_userNotFound() {
        GroupMembership existingMembership = new GroupMembership(groupPub, userA, true);
        groupPub.setGroupMemberList(List.of(existingMembership));
        Optional<String> result = groupService.joinGroup("noFound@dal.ca", 1);
        assertTrue(result.isPresent());
        assertEquals("User not found", result.get());
    }

    @Test
    public void joinGroup_groupUserNotFound() {
        GroupMembership existingMembership = new GroupMembership(groupPub, userA, true);
        groupPub.setGroupMemberList(List.of(existingMembership));
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        Optional<String> result = groupService.joinGroup("a@dal.ca", 1);
        assertTrue(result.isPresent());
        assertEquals("Group not found", result.get());
    }

    @Test
    public void joinGroup_User_notificationToUser() {
        GroupMembership creatorMembership = new GroupMembership(groupPub, userA, true);
        groupPub.setGroupMemberList(List.of(creatorMembership));

        when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
        when(groupRepository.findById(1)).thenReturn(Optional.of(groupPub));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());
        when(groupMembershipRepository.findGroupCreatorByGroupId(1)).thenReturn(Optional.of(creatorMembership));

        groupService.joinGroup("b@dal.ca", 1);

        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationService, times(2)).createNotification(notificationCaptor.capture());
        List<Notification> capturedNotifications = notificationCaptor.getAllValues();

        Notification userNotification = capturedNotifications.get(0);
        assertEquals("You have successfully joined the group: Test Group", userNotification.getContent());
        assertEquals("GroupJoin", userNotification.getNotificationType());
    }

    @Test
    public void joinGroup_User_notificationToCreator() {
        GroupMembership creatorMembership = new GroupMembership(groupPub, userA, true);
        groupPub.setGroupMemberList(List.of(creatorMembership));

        when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
        when(groupRepository.findById(1)).thenReturn(Optional.of(groupPub));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());
        when(groupMembershipRepository.findGroupCreatorByGroupId(1)).thenReturn(Optional.of(creatorMembership));

        groupService.joinGroup("b@dal.ca", 1);

        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationService, times(2)).createNotification(notificationCaptor.capture());
        List<Notification> capturedNotifications = notificationCaptor.getAllValues();

        Notification creatorNotification = capturedNotifications.get(1);
        assertEquals("User b@dal.ca has joined your group: Test Group", creatorNotification.getContent());
        assertEquals("GroupJoin", creatorNotification.getNotificationType());
    }

    @Test
    public void joinGroup_notificationToUserFailure() {
        GroupMembership creatorMembership = new GroupMembership(groupPub, userA, true);
        groupPub.setGroupMemberList(List.of(creatorMembership));
        lenient().when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
        lenient().when(groupRepository.findById(1)).thenReturn(Optional.of(groupPub));
        lenient().when(notificationService.createNotification(any(Notification.class)))
                .thenReturn(Optional.of("Failed to create notification: Database error"));
        lenient().when(groupMembershipRepository.findGroupCreatorByGroupId(1)).thenReturn(Optional.of(creatorMembership));
        Optional<String> result = groupService.joinGroup("b@dal.ca", 1);
        assertTrue(result.isPresent());
        assertEquals("Failed to create notification: Database error", result.get());
    }

    @Test
    public void joinGroup_notificationToCreatorFailure() {
        GroupMembership creatorMembership = new GroupMembership(groupPub, userA, true);
        groupPub.setGroupMemberList(List.of(creatorMembership));
        when(userProfileRepository.findById("b@dal.ca")).thenReturn(Optional.of(userB));
        when(groupRepository.findById(1)).thenReturn(Optional.of(groupPub));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty())
                .thenReturn(Optional.of("Failed to create notification: Database error"));
        when(groupMembershipRepository.findGroupCreatorByGroupId(1)).thenReturn(Optional.of(creatorMembership));
        Optional<String> result = groupService.joinGroup("b@dal.ca", 1);
        assertTrue(result.isPresent());
        assertEquals("Failed to create notification: Database error", result.get());
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
            assertEquals(1, group_dto.groupId());
            assertEquals("Test Group", group_dto.groupName());
            assertEquals(interests[1], group_dto.interest());
        }, () -> {
            fail("No group found for id: " + groupID);
        });
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


    /**
     * Test case for deleteGroup
     */
    @Test
    public void deleteGroup_sendsNotificationToAllMembers_checkMember1() {
        GroupMembership membershipA = new GroupMembership(groupPub, userA, false);
        GroupMembership membershipB = new GroupMembership(groupPub, userB, false);
        groupPub.setGroupMemberList(List.of(membershipA, membershipB));

        when(groupRepository.findById(1)).thenReturn(Optional.of(groupPub));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());

        groupService.deleteGroup(1);

        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationService, times(2)).createNotification(notificationCaptor.capture());
        List<Notification> capturedNotifications = notificationCaptor.getAllValues();

        assertEquals(2, capturedNotifications.size());

        Notification notificationA = capturedNotifications.get(0);
        assertEquals("Group Test Group has been deleted.", notificationA.getContent());
        assertEquals("GroupDeletion", notificationA.getNotificationType());
        assertEquals(userA, notificationA.getUserProfile());
    }

    @Test
    public void deleteGroup_sendsNotificationToAllMembers_checkMember2() {
        GroupMembership membershipA = new GroupMembership(groupPub, userA, false);
        GroupMembership membershipB = new GroupMembership(groupPub, userB, false);
        groupPub.setGroupMemberList(List.of(membershipA, membershipB));

        when(groupRepository.findById(1)).thenReturn(Optional.of(groupPub));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());

        groupService.deleteGroup(1);

        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationService, times(2)).createNotification(notificationCaptor.capture());
        List<Notification> capturedNotifications = notificationCaptor.getAllValues();

        assertEquals(2, capturedNotifications.size());

        Notification notificationB = capturedNotifications.get(1);
        assertEquals("Group Test Group has been deleted.", notificationB.getContent());
        assertEquals("GroupDeletion", notificationB.getNotificationType());
        assertEquals(userB, notificationB.getUserProfile());
    }

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

    @Test
    public void deleteGroup_notificationToMemberFailure() {
        GroupMembership membershipA = new GroupMembership(groupPub, userA, false);
        GroupMembership membershipB = new GroupMembership(groupPub, userB, false);
        groupPub.setGroupMemberList(List.of(membershipA, membershipB));

        when(groupRepository.findById(1)).thenReturn(Optional.of(groupPub));
        when(notificationService.createNotification(any(Notification.class)))
                .thenReturn(Optional.of("Failed to create notification: Database error"));
        Optional<String> result = groupService.deleteGroup(1);

        assertTrue(result.isPresent());
        assertEquals("Failed to create notification: Database error", result.get());
    }


    /**
     * Test case for deleteGroupMembership
     */
    @Test
    public void deleteGroupMembership_membershipIdNotFound() {
        when(groupMembershipRepository.findById(2)).thenReturn(Optional.empty());
        Optional<String> result = groupService.deleteGroupMembership(2);
        assertTrue(result.isPresent());
        assertEquals("Group membership id not found", result.get());
    }

    @Test
    public void deleteGroupMembership_successfulDelete_correctMessage() {
        lenient().when(groupMembershipRepository.findById(2)).thenReturn(Optional.of(groupMembership1));
        lenient().when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());
        Optional<String> result = groupService.deleteGroupMembership(2);
        assertTrue(result.isEmpty());
    }

    @Test
    public void deleteGroupMembership_successfulDelete_notExistInRepository() {
        when(groupMembershipRepository.findById(2)).thenReturn(Optional.of(groupMembership1));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());
        groupService.deleteGroupMembership(2);

        ArgumentCaptor<GroupMembership> membershipCaptor = ArgumentCaptor.forClass(GroupMembership.class);
        verify(groupMembershipRepository).delete(membershipCaptor.capture());

        assertEquals(groupMembership1, membershipCaptor.getValue());
        when(groupMembershipRepository.findById(2)).thenReturn(Optional.empty());
        assertTrue(groupMembershipRepository.findById(2).isEmpty());
    }

    @Test
    public void deleteGroupMembership_sendsNotificationToUser_checkBasic() {
        when(groupMembershipRepository.findById(2)).thenReturn(Optional.of(groupMembership1));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());
        groupService.deleteGroupMembership(2);
        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationService, times(1)).createNotification(notificationCaptor.capture());
        Notification capturedNotification = notificationCaptor.getAllValues().get(0);
        assertNotNull(capturedNotification);
        assertEquals(userA, capturedNotification.getUserProfile());
    }

    @Test
    public void deleteGroupMembership_sendsNotificationToUser_checkContent() {
        when(groupMembershipRepository.findById(2)).thenReturn(Optional.of(groupMembership1));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());
        groupService.deleteGroupMembership(2);
        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationService, times(1)).createNotification(notificationCaptor.capture());
        Notification capturedNotification = notificationCaptor.getAllValues().get(0);
        assertEquals("You have left the group: " + groupPub.getGroupName(), capturedNotification.getContent());
        assertEquals("GroupMembershipDeletion", capturedNotification.getNotificationType());
    }

    @Test
    public void deleteGroupMembership_sendsNotificationToGroupCreator_checkBasic() {
        when(groupMembershipRepository.findById(2)).thenReturn(Optional.of(groupMembership1));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());
        when(groupMembershipRepository.findGroupCreatorByGroupId(groupPub.getGroupId())).thenReturn(Optional.of(groupMembership1));
        groupService.deleteGroupMembership(2);
        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationService, times(2)).createNotification(notificationCaptor.capture());
        Notification capturedNotification = notificationCaptor.getAllValues().get(1);
        assertNotNull(capturedNotification);
        assertEquals(groupMembership1.getUserProfile(), capturedNotification.getUserProfile());
    }

    @Test
    public void deleteGroupMembership_sendsNotificationToGroupCreator_checkContent() {
        when(groupMembershipRepository.findById(2)).thenReturn(Optional.of(groupMembership1));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty());
        when(groupMembershipRepository.findGroupCreatorByGroupId(groupPub.getGroupId())).thenReturn(Optional.of(groupMembership1));
        groupService.deleteGroupMembership(2);
        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationService, times(2)).createNotification(notificationCaptor.capture());
        Notification capturedNotification = notificationCaptor.getAllValues().get(1);
        assertEquals("User " + userA.getEmail() + " has left your group: " + groupPub.getGroupName(), capturedNotification.getContent());
        assertEquals("GroupMembershipDeletion", capturedNotification.getNotificationType());
    }

    @Test
    public void deleteGroupMembership_notificationToUserFailure() {
        when(groupMembershipRepository.findById(2)).thenReturn(Optional.of(groupMembership1));
        when(notificationService.createNotification(any(Notification.class)))
                .thenReturn(Optional.of("Failed to create notification: Database error"));
        Optional<String> result = groupService.deleteGroupMembership(2);
        assertTrue(result.isPresent());
        assertEquals("Failed to create notification: Database error", result.get());
    }

    @Test
    public void deleteGroupMembership_notificationToCreatorFailure() {
        when(groupMembershipRepository.findById(2)).thenReturn(Optional.of(groupMembership1));
        when(notificationService.createNotification(any(Notification.class))).thenReturn(Optional.empty())
                .thenReturn(Optional.of("Failed to create notification: Database error"));
        when(groupMembershipRepository.findGroupCreatorByGroupId(groupPub.getGroupId())).thenReturn(Optional.of(groupMembership1));
        Optional<String> result = groupService.deleteGroupMembership(2);
        assertTrue(result.isPresent());
        assertEquals("Failed to create notification: Database error", result.get());
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