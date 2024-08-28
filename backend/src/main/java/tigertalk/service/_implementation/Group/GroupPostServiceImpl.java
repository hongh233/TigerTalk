package tigertalk.service._implementation.Group;

import tigertalk.model.Group.*;
import tigertalk.model.Notification.Notification;
import tigertalk.model.User.UserProfile;
import tigertalk.repository.Group.GroupPostLikeRepository;
import tigertalk.repository.Group.GroupPostRepository;
import tigertalk.repository.Group.GroupRepository;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.Group.GroupPostService;
import tigertalk.service.Notification.NotificationService;
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
        String email = groupPost.getUserProfile().email();
        List<GroupMembership> groupMembershipList = groupPost.getGroup().getGroupMemberList();
        for (GroupMembership groupMembership : groupMembershipList) {
            UserProfile userProfile = groupMembership.getUserProfile();
            if (userProfile.email().equals(email)) {
                return Optional.of(userProfile);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> createGroupPost(GroupPost groupPost) {
        if (userProfileRepository.existsById(groupPost.getUserProfile().email()) &&
                groupRepository.existsById(groupPost.getGroup().getGroupId())) {

            groupPostRepository.save(groupPost);
            Optional<Group> groupId = groupRepository.findById(groupPost.getGroup().getGroupId());
            if (groupId.isEmpty()) {
                return Optional.of("Group not found, fail to create group post.");
            }

            Group group = groupId.get();
            List<GroupMembership> members = group.getGroupMemberList();

            for (GroupMembership member : members) {
                UserProfile user = member.getUserProfile();
                if (!user.email().equals(groupPost.getUserProfile().email())) {
                    Notification notification = new Notification(
                            user,
                            "User " + groupPost.getUserProfile().email() + " has created a new post in the group: " + group.getGroupName(),
                            "GroupPostCreation"
                    );
                    notificationService.createNotification(notification);
                }
            }
            return Optional.empty();
        } else {
            return Optional.of("User not found, fail to create group post.");
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
        GroupPost groupPost = groupPostRepository.findById(groupPostId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Retrieve the user profile by userEmail
        UserProfile userProfile = userProfileRepository.findUserProfileByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user has already liked the post
        Optional<GroupPostLike> existingLike = groupPostLikeRepository.
                findByGroupPostGroupPostIdAndUserProfileEmail(groupPost.getGroupPostId(), userProfile.email());

        boolean liked; // Track if it is a like or unlike
        if (existingLike.isPresent()) {
            // Unlike the post
            groupPostLikeRepository.delete(existingLike.get());
            groupPost.getPostLikes().remove(existingLike.get());
            liked = false;
        } else {
            // Like the post
            GroupPostLike newPostLike = new GroupPostLike(groupPost, userProfile);
            groupPostLikeRepository.save(newPostLike);
            groupPost.getPostLikes().add(newPostLike);
            liked = true;
        }

        // Save the updated post
        groupPostRepository.save(groupPost);

        // Send notification only on like, not on unlike
        // Send notification to the post-owner
        if (liked) {
            notificationService.createNotification(
                    new Notification(
                            userProfileRepository.findById(groupPost.getUserProfile().email()).get(),
                            userProfile.email() + " liked your post in group " + groupPost.getGroup().getGroupName(),
                            "GroupPostLiked"));
        }

        return groupPost;
    }
}