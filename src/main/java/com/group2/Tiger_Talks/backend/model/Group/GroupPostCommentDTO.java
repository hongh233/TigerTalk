package com.group2.Tiger_Talks.backend.model.Group;

import java.time.LocalDateTime;

public class GroupPostCommentDTO {

    private final int groupPostCommentId;
    private final String groupPostCommentContent;
    private final LocalDateTime groupPostCommentCreateTime;
    private final String groupPostCommentSenderUserName;
    private final String groupPostCommentSenderProfilePictureURL;
    private final String senderEmail;

    public GroupPostCommentDTO(GroupPostComment groupPostComment) {
        this.groupPostCommentId = groupPostComment.getGroupPostCommentId();
        this.groupPostCommentContent = groupPostComment.getContent();
        this.groupPostCommentCreateTime = groupPostComment.getGroupPostCommentCreateTime();
        this.groupPostCommentSenderUserName = groupPostComment.getGroupMembership().getUserProfile().getUserName();
        this.groupPostCommentSenderProfilePictureURL = groupPostComment.getGroupMembership().getUserProfile().getProfilePictureUrl();
        this.senderEmail = groupPostComment.getGroupMembership().getUserProfile().getEmail();
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public int getGroupPostCommentId() {
        return groupPostCommentId;
    }

    public String getGroupPostCommentContent() {
        return groupPostCommentContent;
    }

    public LocalDateTime getGroupPostCommentCreateTime() {
        return groupPostCommentCreateTime;
    }

    public String getGroupPostCommentSenderUserName() {
        return groupPostCommentSenderUserName;
    }

    public String getGroupPostCommentSenderProfilePictureURL() {
        return groupPostCommentSenderProfilePictureURL;
    }

}
