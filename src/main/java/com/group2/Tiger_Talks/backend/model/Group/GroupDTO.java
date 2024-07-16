package com.group2.Tiger_Talks.backend.model.Group;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupDTO {

    private int groupId;

    private String groupName;

    private boolean isPrivate;

    private String groupImg;

    private LocalDateTime groupCreateTime;

    private String groupCreatorEmail;

    private List<GroupMembershipDTO> groupMemberList = new LinkedList<>();

    private List<GroupPost> groupPostList = new LinkedList<>();

    public GroupDTO(Group group) {
        this.groupId = group.getGroupId();
        this.groupName = group.getGroupName();
        this.groupImg = group.getGroupImg();
        this.isPrivate = group.isPrivate();
        this.groupCreateTime = group.getGroupCreateTime();
        this.groupCreatorEmail = group.getGroupMemberList().stream()
                .filter(GroupMembership::isCreator)
                .map(groupMembership -> groupMembership.getUserProfile().getEmail())
                .findFirst()
                .orElse(null);
        this.groupMemberList = group.getGroupMemberList().stream()
                .map(GroupMembershipDTO::new)
                .toList();
    }

    public GroupDTO() {

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

    public LocalDateTime getGroupCreateTime() {
        return groupCreateTime;
    }

    public void setGroupCreateTime(LocalDateTime groupCreateTime) {
        this.groupCreateTime = groupCreateTime;
    }

    public List<GroupMembershipDTO> getGroupMemberList() {
        return groupMemberList;
    }

    public void setGroupMemberList(List<GroupMembershipDTO> groupMemberList) {
        this.groupMemberList = groupMemberList;
    }

    public List<GroupPost> getGroupPostList() {
        return groupPostList;
    }

    public void setGroupPostList(List<GroupPost> groupPostList) {
        this.groupPostList = groupPostList;
    }


    public String getGroupCreatorEmail() {
        return groupCreatorEmail;
    }

    public void setGroupCreatorEmail(String groupCreatorEmail) {
        this.groupCreatorEmail = groupCreatorEmail;
    }

}
