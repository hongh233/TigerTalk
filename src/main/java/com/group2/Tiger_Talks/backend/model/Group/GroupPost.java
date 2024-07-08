package com.group2.Tiger_Talks.backend.model.Group;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class GroupPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;

    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;

    private LocalDateTime postCreateTime = LocalDateTime.now();

    private String groupPostContent;

    private String groupPostSender;    // userprofile email


    public GroupPost(String groupPostContent, String groupPostSender) {
        this.groupPostContent = groupPostContent;
        this.groupPostSender = groupPostSender;
    }

    public GroupPost() {
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public LocalDateTime getPostCreateTime() {
        return postCreateTime;
    }

    public void setPostCreateTime(LocalDateTime messageCreateTime) {
        this.postCreateTime = messageCreateTime;
    }

    public String getGroupPostContent() {
        return groupPostContent;
    }

    public void setGroupPostContent(String groupPostContent) {
        this.groupPostContent = groupPostContent;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getGroupPostSender() {
        return groupPostSender;
    }

    public void setGroupPostSender(String groupPostSender) {
        this.groupPostSender = groupPostSender;
    }


}