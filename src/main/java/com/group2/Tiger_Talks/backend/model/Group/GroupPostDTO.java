package com.group2.Tiger_Talks.backend.model.Group;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class GroupPostDTO {

    private final int groupPostId;
    private final String postSenderEmail;
    private final String groupPostContent;
    private final LocalDateTime groupPostCreateTime;
    private final String groupPostSenderUserName;
    private final String groupPostSenderProfilePictureURL;
    private final String postPictureURL;

    public GroupPostDTO(GroupPost groupPost) {
        this.groupPostId = groupPost.getGroupPostId();
        this.postSenderEmail = groupPost.getGroupPostSenderEmail();
        this.groupPostContent = groupPost.getGroupPostContent();
        this.groupPostCreateTime = groupPost.getPostCreateTime();
        this.groupPostSenderUserName = Objects.requireNonNull(findUserProfileByEmail(groupPost)).getUserName();
        this.groupPostSenderProfilePictureURL = Objects.requireNonNull(findUserProfileByEmail(groupPost)).getProfilePictureUrl();
        this.postPictureURL = groupPost.getGroupPostPictureURL();
    }

    public String getPostPictureURL() {
        return postPictureURL;
    }

    private UserProfile findUserProfileByEmail(GroupPost groupPost) {
        String email = groupPost.getGroupPostSenderEmail();
        List<GroupMembership> groupMembershipList = groupPost.getGroup().getGroupMemberList();
        for (GroupMembership groupMembership : groupMembershipList) {
            UserProfile userProfile = groupMembership.getUserProfile();
            if (userProfile.getEmail().equals(email)) {
                return userProfile;
            }
        }
        return null;
    }

    public int getGroupPostId() {
        return groupPostId;
    }

    public String getPostSenderEmail() {
        return postSenderEmail;
    }

    public String getGroupPostContent() {
        return groupPostContent;
    }

    public LocalDateTime getGroupPostCreateTime() {
        return groupPostCreateTime;
    }

    public String getGroupPostSenderUserName() {
        return groupPostSenderUserName;
    }

    public String getGroupPostSenderProfilePictureURL() {
        return groupPostSenderProfilePictureURL;
    }

}
