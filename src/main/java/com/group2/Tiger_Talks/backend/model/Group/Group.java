package com.group2.Tiger_Talks.backend.model.Group;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;

    private String groupName;
    private LocalDateTime groupCreateTime = LocalDateTime.now();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMembership> members;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMessage> messages;

    public Group() {
    }

    public Group(String groupName) {
        this.groupName = groupName;
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

    public void setGroupCreateTime(java.time.LocalDateTime groupCreateTime) {
        this.groupCreateTime = groupCreateTime;
    }

    public List<GroupMembership> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMembership> members) {
        this.members = members;
    }
}