package tigertalk.service._implementation.Group;

import tigertalk.model.Group.Group;
import tigertalk.model.Group.GroupDTO;
import tigertalk.model.Group.GroupMembership;
import tigertalk.model.Group.GroupMembershipDTO;
import tigertalk.model.Notification.Notification;
import tigertalk.model.User.UserProfile;
import tigertalk.repository.Group.GroupMembershipRepository;
import tigertalk.repository.Group.GroupRepository;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.Group.GroupService;
import tigertalk.service.Notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMembershipRepository groupMembershipRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public Optional<String> createGroup(String groupName, String creatorEmail, boolean isPrivate, String interest) {
        if (userProfileRepository.findById(creatorEmail).isEmpty()) {
            return Optional.of("User not found");
        }
        UserProfile userProfile = userProfileRepository.findById(creatorEmail).get();

        Group group = new Group(groupName, isPrivate, interest);
        groupRepository.save(group);
        GroupMembership groupMembership = new GroupMembership(group, userProfile, true);
        groupMembershipRepository.save(groupMembership);

        // Create and send the notification
        Notification notification = new Notification(
                userProfile,
                "You have successfully created the group: " + groupName,
                "GroupCreation"
        );
        return notificationService.createNotification(notification);
    }

    @Override
    public Optional<String> joinGroupUser(String userEmail, int groupId) {
        return joinGroup(userEmail, groupId, false);
    }

    @Override
    public Optional<String> joinGroupAdmin(String userEmail, int groupId) {
        return joinGroup(userEmail, groupId, true);
    }

    /**
     * Attempts to add a user to a group, either as a regular member or as an admin.
     *
     * @param userEmail    the email of the user to be added to the group
     * @param groupID      the ID of the group to join
     * @param isGroupAdmin {@code true} if the user is to be added as an admin, {@code false} if as a regular member
     * @return an {@link Optional} containing an error message if the user or group is not found,
     * if the group is private and the user is not an admin, or if the user is already a member of the group,
     * otherwise an empty {@link Optional} indicating the user was successfully added to the group
     */
    private Optional<String> joinGroup(String userEmail, int groupID, boolean isGroupAdmin) {
        if (userProfileRepository.findById(userEmail).isEmpty()) {
            return Optional.of("User not found");
        }
        if (groupRepository.findById(groupID).isEmpty()) {
            return Optional.of("Group not found");
        }

        UserProfile userProfile = userProfileRepository.findById(userEmail).get();
        Group group = groupRepository.findById(groupID).get();

        if (!isGroupAdmin) { // Check for a private group if im not an admin adding a user
            if (group.isPrivate())
                return Optional.of("This is a private group and you cannot join unless you are invited by its admin");
        }

        // Check if the user is already a member of the group
        for (GroupMembership membership : group.getGroupMemberList()) {
            if (membership.getUserProfile().equals(userProfile)) {
                return Optional.of("User is already a member of the group");
            }
        }
        GroupMembership groupMembership = new GroupMembership(group, userProfile, false);
        groupMembershipRepository.save(groupMembership);

        // Create and send notification to the user
        Notification userNotification = new Notification(
                userProfile,
                "You have successfully joined the group: " + group.getGroupName(),
                "GroupJoin"
        );
        Optional<String> notificationResult = notificationService.createNotification(userNotification);
        if (notificationResult.isPresent()) {
            return notificationResult;
        }

        // Create and send notification to the group creator
        Optional<GroupMembership> creatorMembership = groupMembershipRepository.findGroupCreatorByGroupId(groupID);
        if (creatorMembership.isPresent()) {
            UserProfile groupCreator = creatorMembership.get().getUserProfile();
            Notification creatorNotification = new Notification(
                    groupCreator,
                    "User " + userProfile.email() + " has joined your group: " + group.getGroupName(),
                    "GroupJoin"
            );
            notificationResult = notificationService.createNotification(creatorNotification);
            if (notificationResult.isPresent()) {
                return notificationResult;
            }
        }

        return Optional.empty();
    }

    @Override
    public List<GroupDTO> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(Group::toDto)
                .toList();
    }

    @Override
    public Optional<GroupDTO> getGroup(int groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        return group.map(Group::toDto);
    }

    @Override
    public List<GroupDTO> getAllGroupsByUser(String userEmail) {
        Optional<UserProfile> userProfile = userProfileRepository.findById(userEmail);
        if (userProfile.isEmpty()) {
            return Collections.emptyList();
        }
        List<GroupMembership> memberships = groupMembershipRepository.findByUserProfile_Email(userEmail);
        return memberships.stream()
                .map(membership -> membership.getGroup().toDto())
                .toList();
    }

    @Override
    public Optional<String> updateGroupInfo(GroupDTO groupDTO) {
        Optional<Group> groupTemp = groupRepository.findById(groupDTO.groupId());
        return groupTemp.map(group -> {
            group.updateFromDto(groupDTO);
            groupRepository.save(group);
            return Optional.<String>empty();
        }).orElseGet(() -> Optional.of("Group id not found"));
    }

    @Override
    public Optional<String> deleteGroup(int groupId) {
        Optional<Group> groupTemp = groupRepository.findById(groupId);
        if (groupTemp.isEmpty()) {
            return Optional.of("Group id not found");
        }

        Group group = groupTemp.get();
        List<GroupMembership> members = group.getGroupMemberList();
        for (GroupMembership member : members) {
            UserProfile user = member.getUserProfile();
            Notification notification = new Notification(
                    user,
                    "Group " + group.getGroupName() + " has been deleted.",
                    "GroupDeletion"
            );
            Optional<String> notificationResult = notificationService.createNotification(notification);
            if (notificationResult.isPresent()) {
                return notificationResult;
            }
        }

        groupMembershipRepository.deleteAll(members);
        groupRepository.delete(group);
        return Optional.empty();
    }

    @Override
    public Optional<String> deleteGroupMembership(int groupMembershipId) {
        Optional<GroupMembership> membershipTemp = groupMembershipRepository.findById(groupMembershipId);
        if (membershipTemp.isEmpty()) {
            return Optional.of("Group membership id not found");
        }
        GroupMembership groupMembership = membershipTemp.get();
        groupMembershipRepository.delete(groupMembership);

        // Create and send the notification to user
        UserProfile userProfile = groupMembership.getUserProfile();
        Group group = groupMembership.getGroup();
        Optional<String> notificationResult = notificationService.createNotification(
                new Notification(
                        userProfile,
                        "You have left the group: " + group.getGroupName(),
                        "GroupMembershipDeletion"));
        if (notificationResult.isPresent()) {
            return notificationResult;
        }

        // Create and send the notification to group creator
        Optional<GroupMembership> creatorMembership = groupMembershipRepository.findGroupCreatorByGroupId(group.getGroupId());
        if (creatorMembership.isPresent()) {
            UserProfile groupCreator = creatorMembership.get().getUserProfile();
            notificationResult = notificationService.createNotification(
                    new Notification(
                            groupCreator,
                            "User " + userProfile.email() + " has left your group: " + group.getGroupName(),
                            "GroupMembershipDeletion"));
            if (notificationResult.isPresent()) {
                return notificationResult;
            }
        }
        return Optional.empty();
    }

    @Override
    public List<GroupMembershipDTO> getGroupMembersByGroupId(int groupId) {
        List<GroupMembership> memberships = groupMembershipRepository.findByGroup_GroupId(groupId);
        return memberships.stream()
                .map(GroupMembership::toDto)
                .toList();
    }

    @Override
    public Optional<Integer> getMemberShipId(String userEmail, int groupId) {
        List<GroupMembership> memberships = groupMembershipRepository.findByGroup_GroupId(groupId);
        for (GroupMembership membership : memberships) {
            if (membership.getUserProfile().email().equals(userEmail)) {
                return Optional.of(membership.getGroupMembershipId());
            }
        }
        return Optional.empty();
    }
}