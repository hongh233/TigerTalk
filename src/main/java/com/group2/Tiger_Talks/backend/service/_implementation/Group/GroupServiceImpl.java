package com.group2.Tiger_Talks.backend.service._implementation.Group;

import com.group2.Tiger_Talks.backend.model.Group.*;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Group.GroupMembershipRepository;
import com.group2.Tiger_Talks.backend.repository.Group.GroupRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMembershipRepository groupMembershipRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public Optional<String> createGroup(String groupName, String creatorEmail, boolean isPrivate) {
        if (userProfileRepository.findById(creatorEmail).isEmpty()) {
            return Optional.of("User not found");
        }
        UserProfile userProfile = userProfileRepository.findById(creatorEmail).get();

        Group group = new Group(groupName, isPrivate);
        groupRepository.save(group);
        GroupMembership groupMembership = new GroupMembership(group, userProfile, true);
        groupMembershipRepository.save(groupMembership);
        return Optional.empty();
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

        return Optional.empty();
    }

    @Override
    public List<GroupDTO> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(GroupDTO::new)
                .toList();
    }

    @Override
    public Optional<GroupDTO> getGroup(int groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        return group.map(GroupDTO::new);
    }

    @Override
    public List<GroupDTO> getAllGroupsByUser(String userEmail) {
        Optional<UserProfile> userProfile = userProfileRepository.findById(userEmail);
        if (userProfile.isEmpty()) {
            return Collections.emptyList();
        }
        List<GroupMembership> memberships = groupMembershipRepository.findByUserProfile_Email(userEmail);
        return memberships.stream()
                .map(membership -> new GroupDTO(membership.getGroup()))
                .toList();
    }

    @Override
    public Optional<String> updateGroupInfo(GroupUpdate groupUpdate) {
        Optional<Group> groupTemp = groupRepository.findById(groupUpdate.getGroupId());
        if (groupTemp.isEmpty()) {
            return Optional.of("Group id not found");
        }
        Group group = groupTemp.get();
        group.setGroupName(groupUpdate.getGroupName());
        group.setGroupImg(groupUpdate.getGroupImg());
        group.setPrivate(groupUpdate.isPrivate());
        groupRepository.save(group);
        return Optional.empty();
    }

    @Override
    public Optional<String> deleteGroup(int groupId) {
        Optional<Group> groupTemp = groupRepository.findById(groupId);
        if (groupTemp.isEmpty()) {
            return Optional.of("Group id not found");
        }
        Group group = groupTemp.get();
        groupMembershipRepository.deleteAll(group.getGroupMemberList());
        groupRepository.delete(group);
        return Optional.empty();
    }

    // I haven't considered whether we can delete group creator
    @Override
    public Optional<String> deleteGroupMembership(int groupMembershipId) {
        Optional<GroupMembership> membershipTemp = groupMembershipRepository.findById(groupMembershipId);
        if (membershipTemp.isEmpty()) {
            return Optional.of("Group membership id not found");
        }
        groupMembershipRepository.delete(membershipTemp.get());
        return Optional.empty();
    }

    @Override
    public List<GroupMembershipDTO> getGroupMembersByGroupId(int groupId) {
        List<GroupMembership> memberships = groupMembershipRepository.findByGroup_GroupId(groupId);
        return memberships.stream()
                .map(GroupMembershipDTO::new)
                .toList();
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

}
