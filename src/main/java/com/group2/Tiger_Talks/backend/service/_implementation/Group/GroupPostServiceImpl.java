package com.group2.Tiger_Talks.backend.service._implementation.Group;

import com.group2.Tiger_Talks.backend.model.Group.GroupMembership;
import com.group2.Tiger_Talks.backend.model.Group.GroupPost;
import com.group2.Tiger_Talks.backend.model.Group.GroupPostDTO;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Group.GroupPostRepository;
import com.group2.Tiger_Talks.backend.repository.Group.GroupRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Group.GroupPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class GroupPostServiceImpl implements GroupPostService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupPostRepository groupPostRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    public static Optional<UserProfile> findUserProfileByEmail(GroupPost groupPost) {
        String email = groupPost.getGroupPostSenderEmail();
        List<GroupMembership> groupMembershipList = groupPost.getGroup().getGroupMemberList();
        for (GroupMembership groupMembership : groupMembershipList) {
            UserProfile userProfile = groupMembership.getUserProfile();
            if (userProfile.getEmail().equals(email)) {
                return Optional.of(userProfile);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> createGroupPost(GroupPost groupPost) {
        if (userProfileRepository.existsById(groupPost.getGroupPostSenderEmail()) &&
                groupRepository.existsById(groupPost.getGroup().getGroupId())) {
            groupPostRepository.save(groupPost);
            return Optional.empty();
        } else {
            return Optional.of("User or Group not found, fail to create group post.");
        }
    }


    /* I expect there is no update, think of one thing: if you send something on teams
       or discord, are you able to update your message? If you find something should be
       updated, you may have to delete your message and resend. And also because the logic
       of update is complex, it's not necessary to do that.
     */

    @Override
    public Optional<String> deleteGroupPostById(Integer groupPostId) {
        if (groupPostRepository.existsById(groupPostId)) {
            groupPostRepository.deleteById(groupPostId);
            return Optional.empty();
        } else {
            return Optional.of("Group post id not found, fail to delete group post.");
        }
    }

    @Override
    public List<GroupPostDTO> getAllGroupPostsByGroupId(Integer groupId) {
        return groupRepository.findById(groupId)
                .map(group -> group.getGroupPostList().stream()
                        .map(GroupPost::toDto)
                        .sorted(
                                Comparator.comparing(
                                        GroupPostDTO::groupPostCreateTime,
                                        Comparator.nullsLast(Comparator.naturalOrder())).reversed()
                        ).toList()
                ).orElseGet(Collections::emptyList);
    }
}