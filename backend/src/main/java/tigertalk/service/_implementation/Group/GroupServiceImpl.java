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

import java.util.ArrayList;
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
    public Optional<String> joinGroup(String email, int groupID) {
        if (userProfileRepository.findById(email).isEmpty()) {
            return Optional.of("User not found");
        }
        if (groupRepository.findById(groupID).isEmpty()) {
            return Optional.of("Group not found");
        }

        UserProfile userProfile = userProfileRepository.findById(email).get();
        Group group = groupRepository.findById(groupID).get();
        groupMembershipRepository.save(new GroupMembership(group, userProfile, false));

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
                    "User " + userProfile.getEmail() + " has joined your group: " + group.getGroupName(),
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
        List<Group> groups = groupRepository.findAll();
        List<GroupDTO> groupDTOs = new ArrayList<>();
        for (Group group : groups) {
            groupDTOs.add(group.toDto());
        }
        return groupDTOs;
    }

    @Override
    public Optional<GroupDTO> getGroup(int groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            GroupDTO groupDTO = group.toDto();
            return Optional.of(groupDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<GroupDTO> getAllGroupsByUser(String userEmail) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userEmail);
        if (userProfileOptional.isEmpty()) {
            return Collections.emptyList();
        }

        List<GroupMembership> memberships = groupMembershipRepository.findByUserProfile_Email(userEmail);

        List<GroupDTO> groupDTOs = new ArrayList<>();
        for (GroupMembership membership : memberships) {
            GroupDTO groupDTO = membership.getGroup().toDto();
            groupDTOs.add(groupDTO);
        }
        return groupDTOs;
    }

    @Override
    public Optional<String> updateGroupInfo(GroupDTO groupDTO) {
        Optional<Group> groupOptional = groupRepository.findById(groupDTO.groupId());
        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            group.updateFromDto(groupDTO);
            groupRepository.save(group);
            return Optional.empty();
        } else {
            return Optional.of("Group id not found");
        }
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
                            "User " + userProfile.getEmail() + " has left your group: " + group.getGroupName(),
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
        List<GroupMembershipDTO> membershipDTOs = new ArrayList<>();

        for (GroupMembership membership : memberships) {
            membershipDTOs.add(membership.toDto());
        }
        return membershipDTOs;
    }

    @Override
    public Optional<Integer> getMemberShipId(String userEmail, int groupId) {
        List<GroupMembership> memberships = groupMembershipRepository.findByGroup_GroupId(groupId);
        for (GroupMembership membership : memberships) {
            if (membership.getUserProfile().getEmail().equals(userEmail)) {
                return Optional.of(membership.getGroupMembershipId());
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> transferGroupOwnership(int previousOwnerMembershipId, int newOwnerMembershipId) {
        Optional<GroupMembership> previousOwnerMembershipOpt = groupMembershipRepository.findById(previousOwnerMembershipId);
        Optional<GroupMembership> newOwnerMembershipOpt = groupMembershipRepository.findById(newOwnerMembershipId);

        if (previousOwnerMembershipOpt.isPresent() && newOwnerMembershipOpt.isPresent()) {
            GroupMembership previousOwnerMembership = previousOwnerMembershipOpt.get();
            GroupMembership newOwnerMembership = newOwnerMembershipOpt.get();
            previousOwnerMembership.setCreator(false);
            newOwnerMembership.setCreator(true);
            groupMembershipRepository.save(previousOwnerMembership);
            groupMembershipRepository.save(newOwnerMembership);

            Notification previousOwnerNotification = new Notification(
                    previousOwnerMembership.getUserProfile(),
                    "You have transferred ownership of the group: " + newOwnerMembership.getGroup().getGroupName(),
                    "GroupOwnershipTransfer"
            );
            Notification newOwnerNotification = new Notification(
                    newOwnerMembership.getUserProfile(),
                    "You are now the owner of the group: " + newOwnerMembership.getGroup().getGroupName(),
                    "GroupOwnershipTransfer"
            );
            notificationService.createNotification(previousOwnerNotification);
            notificationService.createNotification(newOwnerNotification);
            return Optional.empty();
        }else {
            return Optional.of("Error, can not find group membership!");
        }
    }
}
