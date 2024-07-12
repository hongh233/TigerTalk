package com.group2.Tiger_Talks.backend.service._implementation.Group;

import com.group2.Tiger_Talks.backend.model.Group.*;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Group.GroupMembershipRepository;
import com.group2.Tiger_Talks.backend.repository.Group.GroupRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<String> joinGroup(String userEmail, int groupId) {
        if (userProfileRepository.findById(userEmail).isEmpty()) {
            return Optional.of("User not found");
        }
        if (groupRepository.findById(groupId).isEmpty()) {
            return Optional.of("Group not found");
        }

        UserProfile userProfile = userProfileRepository.findById(userEmail).get();
        Group group = groupRepository.findById(groupId).get();

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
                .collect(Collectors.toList());
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
            return List.of();
        }
        List<GroupMembership> memberships = groupMembershipRepository.findByUserProfile_Email(userEmail);
        return memberships.stream()
                .map(membership -> new GroupDTO(membership.getGroup()))
                .collect(Collectors.toList());
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

}
