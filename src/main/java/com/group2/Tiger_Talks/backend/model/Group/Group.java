package com.group2.Tiger_Talks.backend.model.Group;

import com.group2.Tiger_Talks.backend.model.DtoConvertible;
import com.group2.Tiger_Talks.backend.model.Utils;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "user_group") // TODO (Bounty) : Change to group_all
public class Group implements DtoConvertible<GroupDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;

    private String groupName;

    private boolean isPrivate;

    private String interest;

    private String groupImg = Utils.DEFAULT_GROUP_PICTURE;

    private LocalDateTime groupCreateTime = LocalDateTime.now();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMembership> groupMemberList = new LinkedList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPost> groupPostList = new LinkedList<>();

    public Group() {
    }

    public Group(String groupName, boolean isPrivate, String interest) {
        this.groupName = groupName;
        this.isPrivate = isPrivate;
        this.interest = interest;
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

    @Override
    public GroupDTO toDto() {
        String groupCreatorEmail = this.groupMemberList.stream()
                .filter(GroupMembership::isCreator)
                .map(groupMembership -> groupMembership.getUserProfile().getEmail())
                .findFirst()
                .orElse(null);

        List<GroupMembershipDTO> groupMemberDTOList = this.groupMemberList.stream()
                .map(GroupMembership::toDto)
                .toList();

        List<GroupPostDTO> groupPostDTOList = this.groupPostList.stream()
                .map(GroupPostDTO::new)
                .toList();

        return new GroupDTO(
                this.groupId,
                this.groupName,
                this.isPrivate,
                this.interest,
                this.groupImg,
                this.groupCreateTime,
                groupCreatorEmail,
                groupMemberDTOList,
                groupPostDTOList
        );
    }

    @Override
    public void updateFromDto(GroupDTO groupDTO) {
        this.groupName = groupDTO.groupName();
        this.isPrivate = groupDTO.isPrivate();
        this.interest = groupDTO.interest();
        this.groupImg = groupDTO.groupImg();
    }
}