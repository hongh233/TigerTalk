package com.group2.Tiger_Talks.backend.model.Group;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
public class GroupPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupPostId;

    @ManyToOne
    @JoinColumn(name = "groupId", referencedColumnName = "groupId", nullable = false)
    private Group group;

    @OneToMany(mappedBy = "groupPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPostComment> groupPostCommentList = new LinkedList<>();

    private LocalDateTime postCreateTime = LocalDateTime.now();

    private String groupPostContent;

    private String groupPostSenderEmail;


    public GroupPost(Group group, String groupPostContent, String groupPostSenderEmail) {
        this.group = group;
        this.groupPostContent = groupPostContent;
        this.groupPostSenderEmail = groupPostSenderEmail;
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

    public int getGroupPostId() {
        return groupPostId;
    }

    public void setGroupPostId(int groupPostId) {
        this.groupPostId = groupPostId;
    }

    public String getGroupPostSenderEmail() {
        return groupPostSenderEmail;
    }

    public void setGroupPostSenderEmail(String groupPostSenderEmail) {
        this.groupPostSenderEmail = groupPostSenderEmail;
    }


    public List<GroupPostComment> getGroupPostCommentList() {
        return groupPostCommentList;
    }

    public void setGroupPostCommentList(List<GroupPostComment> groupPostCommentList) {
        this.groupPostCommentList = groupPostCommentList;
    }

}