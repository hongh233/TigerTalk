package com.group2.Tiger_Talks.backend.service._implementation.Group;

import com.group2.Tiger_Talks.backend.model.Group.GroupMembership;
import com.group2.Tiger_Talks.backend.model.Group.GroupPost;
import com.group2.Tiger_Talks.backend.model.Group.GroupPostComment;
import com.group2.Tiger_Talks.backend.model.Group.GroupPostCommentDTO;
import com.group2.Tiger_Talks.backend.model.Notification.Notification;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Group.GroupMembershipRepository;
import com.group2.Tiger_Talks.backend.repository.Group.GroupPostCommentRepository;
import com.group2.Tiger_Talks.backend.repository.Group.GroupPostRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Group.GroupPostCommentService;
import com.group2.Tiger_Talks.backend.service.Notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupPostCommentServiceImpl implements GroupPostCommentService {

    @Autowired
    private GroupMembershipRepository groupMembershipRepository;

    @Autowired
    private GroupPostRepository groupPostRepository;

    @Autowired
    private GroupPostCommentRepository groupPostCommentRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public Optional<String> createGroupPostComment(int groupPostId, GroupPostComment groupPostComment) {
        Optional<GroupPost> groupPostOpt = groupPostRepository.findById(groupPostId);
        if (groupPostOpt.isPresent()) {
            GroupPost groupPost = groupPostOpt.get();
            groupPostComment.setGroupPost(groupPost);
            Optional<GroupMembership> groupMembershipOpt = groupMembershipRepository.findByGroupAndUserProfileEmail(
                    groupPost.getGroup(), groupPostComment.getGroupMembership().getUserProfile().email());

            if (groupMembershipOpt.isPresent()) {
                groupPostComment.setGroupMembership(groupMembershipOpt.get());
                groupPostCommentRepository.save(groupPostComment);

                // Create and send notification to GroupPost owner if it's not the same as the commenter
                String groupPostOwnerEmail = groupPost.getGroupPostSenderEmail();
                String commenterEmail = groupPostComment.getGroupMembership().getUserProfile().email();
                if (!groupPostOwnerEmail.equals(commenterEmail)) {
                    Optional<UserProfile> groupPostOwnerOpt = userProfileRepository.findById(groupPostOwnerEmail);
                    if (groupPostOwnerOpt.isPresent()) {
                        UserProfile groupPostOwner = groupPostOwnerOpt.get();
                        Notification notification = new Notification(
                                groupPostOwner,
                                "User " + commenterEmail +
                                        " commented on your post in group " + groupPost.getGroup().getGroupName(),
                                "GroupPostComment"
                        );
                        notificationService.createNotification(notification);
                    }
                }

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
                        .map(GroupPostComment::toDto)
                        .sorted(Comparator.comparing(GroupPostCommentDTO::groupPostCommentCreateTime).reversed())
                        .collect(Collectors.toList())
                )
                .orElse(Collections.emptyList());
    }

}