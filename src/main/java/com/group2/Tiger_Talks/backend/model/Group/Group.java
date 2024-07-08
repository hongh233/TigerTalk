package com.group2.Tiger_Talks.backend.model.Group;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "user_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;

    private String groupName;

    private boolean isPrivate;

    private String groupImg;

    private LocalDateTime groupCreateTime = LocalDateTime.now();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMembership> groupMemberList = new LinkedList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPost> groupPostList = new LinkedList<>();

    public Group() {
    }

    public Group(String groupName, boolean isPrivate) {
        this.groupName = groupName;
        this.isPrivate = isPrivate;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public LocalDateTime getGroupCreateTime() {
        return groupCreateTime;
    }

    public void setGroupCreateTime(LocalDateTime groupCreateTime) {
        this.groupCreateTime = groupCreateTime;
    }

    public List<GroupMembership> getGroupMemberList() {
        return groupMemberList;
    }

    public void setGroupMemberList(List<GroupMembership> groupMemberList) {
        this.groupMemberList = groupMemberList;
    }


    public List<GroupPost> getGroupPostList() {
        return groupPostList;
    }

    public void setGroupPostList(List<GroupPost> messages) {
        this.groupPostList = messages;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }


    public String getGroupImg() {
        return groupImg;
    }

    public void setGroupImg(String groupImg) {
        this.groupImg = groupImg;
    }
}