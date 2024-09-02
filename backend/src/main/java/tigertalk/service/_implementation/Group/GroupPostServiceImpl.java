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

import java.util.*;

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

    @Override
    public Optional<String> createGroupPost(GroupPost groupPost) {
        if (userProfileRepository.existsById(groupPost.getUserProfile().getEmail()) &&
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
                if (!user.getEmail().equals(groupPost.getUserProfile().getEmail())) {
                    Notification notification = new Notification(
                            user,
                            "User " + groupPost.getUserProfile().getEmail() + " has created a new post in the group: " + group.getGroupName(),
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
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            List<GroupPost> groupPosts = group.getGroupPostList();

            List<GroupPostDTO> groupPostDTOs = new ArrayList<>();
            for (GroupPost groupPost : groupPosts) {
                groupPostDTOs.add(groupPost.toDto());
            }

            groupPostDTOs.sort(Comparator.comparing(
                    GroupPostDTO::groupPostCreateTime,
                    Comparator.nullsLast(Comparator.naturalOrder())
                    ).reversed());

            return groupPostDTOs;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public GroupPost likePost(Integer groupPostId, String userEmail) {
        Optional<GroupPost> groupPostOptional = groupPostRepository.findById(groupPostId);
        if (groupPostOptional.isEmpty()) {
            throw new RuntimeException("Post not found");
        }
        GroupPost groupPost = groupPostOptional.get();

        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userEmail);
        if (userProfileOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        UserProfile userProfile = userProfileOptional.get();

        Optional<GroupPostLike> existingLike = groupPostLikeRepository
                .findByGroupPostGroupPostIdAndUserProfileEmail(groupPost.getGroupPostId(), userProfile.getEmail());

        boolean liked;
        if (existingLike.isPresent()) {
            groupPostLikeRepository.delete(existingLike.get());
            groupPost.getPostLikes().remove(existingLike.get());
            liked = false;
        } else {
            GroupPostLike newPostLike = new GroupPostLike(groupPost, userProfile);
            groupPostLikeRepository.save(newPostLike);
            groupPost.getPostLikes().add(newPostLike);
            liked = true;
        }

        groupPostRepository.save(groupPost);

        if (liked) {
            notificationService.createNotification(
                    new Notification(
                            userProfileRepository.findById(groupPost.getUserProfile().getEmail()).get(),
                            userProfile.getEmail() + " liked your post in group " + groupPost.getGroup().getGroupName(),
                            "GroupPostLiked"));
        }

        return groupPost;
    }
}