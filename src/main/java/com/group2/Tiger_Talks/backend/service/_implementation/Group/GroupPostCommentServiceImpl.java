package com.group2.Tiger_Talks.backend.service._implementation.Group;

import com.group2.Tiger_Talks.backend.model.Group.GroupMembership;
import com.group2.Tiger_Talks.backend.model.Group.GroupPost;
import com.group2.Tiger_Talks.backend.model.Group.GroupPostComment;
import com.group2.Tiger_Talks.backend.model.Group.GroupPostCommentDTO;
import com.group2.Tiger_Talks.backend.repository.Group.GroupMembershipRepository;
import com.group2.Tiger_Talks.backend.repository.Group.GroupPostCommentRepository;
import com.group2.Tiger_Talks.backend.repository.Group.GroupPostRepository;
import com.group2.Tiger_Talks.backend.repository.Group.GroupRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Group.GroupPostCommentService;
import com.group2.Tiger_Talks.backend.service.Group.GroupPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupPostCommentServiceImpl implements GroupPostCommentService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMembershipRepository groupMembershipRepository;

    @Autowired
    private GroupPostRepository groupPostRepository;

    @Autowired
    private GroupPostCommentRepository groupPostCommentRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public Optional<String> createGroupPostComment(int groupPostId, GroupPostComment groupPostComment) {
        Optional<GroupPost> groupPostOpt = groupPostRepository.findById(groupPostId);
        if (groupPostOpt.isPresent()) {
            GroupPost groupPost = groupPostOpt.get();
            groupPostComment.setGroupPost(groupPost);
            Optional<GroupMembership> groupMembershipOpt = groupMembershipRepository.findByGroupAndUserProfileEmail(
                    groupPost.getGroup(), groupPostComment.getGroupMembership().getUserProfile().getEmail());

            if (groupMembershipOpt.isPresent()) {
                groupPostComment.setGroupMembership(groupMembershipOpt.get());
                groupPostCommentRepository.save(groupPostComment);
                return Optional.empty();
            } else {
                return Optional.of("User is not a member of the group, fail to create group post comment.");
            }
        } else {
            return Optional.of("Group post id not found, fail to create group post comment.");
        }
    }

    @Override
    public Optional<String> deleteGroupPostCommentById(int groupPostCommentId) {
        Optional<GroupPostComment> groupPostCommentOpt = groupPostCommentRepository.findById(groupPostCommentId);
        if (groupPostCommentOpt.isPresent()) {
            groupPostCommentRepository.deleteById(groupPostCommentId);
            return Optional.empty();
        } else {
            return Optional.of("Group post comment id not found, fail to delete group post comment.");
        }
    }

    @Override
    public List<GroupPostCommentDTO> getCommentsByGroupPostId(Integer groupPostId) {
        return groupPostRepository.findById(groupPostId)
                .map(groupPost -> groupPost.getGroupPostCommentList().stream()
                        .map(GroupPostCommentDTO::new)
                        .sorted(Comparator.comparing(GroupPostCommentDTO::groupPostCommentCreateTime).reversed())
                        .collect(Collectors.toList())
                )
                .orElse(Collections.emptyList());
    }

}