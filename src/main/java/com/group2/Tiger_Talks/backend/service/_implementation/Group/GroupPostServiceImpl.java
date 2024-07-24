package com.group2.Tiger_Talks.backend.service._implementation.Group;

import com.group2.Tiger_Talks.backend.model.Group.*;
import com.group2.Tiger_Talks.backend.model.Notification.Notification;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Group.GroupPostLikeRepository;
import com.group2.Tiger_Talks.backend.repository.Group.GroupPostRepository;
import com.group2.Tiger_Talks.backend.repository.Group.GroupRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Group.GroupPostService;
import com.group2.Tiger_Talks.backend.service.Notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class GroupPostServiceImpl implements GroupPostService {

    @Autowired
    private GroupPostLikeRepository groupPostLikeRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupPostRepository groupPostRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private NotificationService notificationService;

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
            Group group = groupRepository.findById(groupPost.getGroup().getGroupId()).get();
            List<GroupMembership> members = group.getGroupMemberList();

            for (GroupMembership member : members) {
                UserProfile user = member.getUserProfile();
                if (!user.getEmail().equals(groupPost.getGroupPostSenderEmail())) {
                    Notification notification = new Notification(
                            user,
                            "User " + groupPost.getGroupPostSenderEmail() + " has created a new post in the group: " + group.getGroupName(),
                            "GroupPostCreation"
                    );
                    notificationService.createNotification(notification);
                }
            }
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

    @Override
    public GroupPost likePost(Integer groupPostId, String userEmail) {
        // Retrieve the post by postId
        GroupPost post = groupPostRepository.findById(groupPostId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Retrieve the user profile by userEmail
        UserProfile userProfile = userProfileRepository.findUserProfileByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user has already liked the post
        Optional<GroupPostLike> existingLike = groupPostLikeRepository.
                findByGroupPostGroupPostIdAndUserProfileEmail(post.getGroupPostId(), userProfile.getEmail());

        boolean liked; // Track if it is a like or unlike
        if (existingLike.isPresent()) {
            // Unlike the post
            groupPostLikeRepository.delete(existingLike.get());
            post.getPostLikes().remove(existingLike.get());
            liked = false;
        } else {
            // Like the post
            GroupPostLike newPostLike = new GroupPostLike(post, userProfile);
            groupPostLikeRepository.save(newPostLike);
            post.getPostLikes().add(newPostLike);
            liked = true;
        }

        // Save the updated post
        groupPostRepository.save(post);

        // Send notification only on like, not on unlike
        // Send notification to the post-owner
        if (liked) {
            String content = userProfile.getEmail() + " liked your post.";
            notificationService.createNotification(
                    new Notification(
                            post.getUserProfile(),
                            content,
                            "PostLiked"));
        }

        return post;
    }
}