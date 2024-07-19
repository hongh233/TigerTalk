package com.group2.Tiger_Talks.backend.model.Group;

import com.group2.Tiger_Talks.backend.model.DtoConvertible;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class GroupMembership implements DtoConvertible<GroupMembershipDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupMembershipId;

    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "email")
    private UserProfile userProfile;

    @OneToMany(mappedBy = "groupMembership", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPostComment> groupPostCommentList;

    private LocalDateTime joinTime = LocalDateTime.now();

    private boolean isCreator;


    public GroupMembership() {
    }

    public GroupMembership(Group group, UserProfile userProfile, boolean isCreator) {
        this.group = group;
        this.userProfile = userProfile;
        this.isCreator = isCreator;
    }

    public int getGroupMembershipId() {
        return groupMembershipId;
    }

    public void setGroupMembershipId(int membershipId) {
        this.groupMembershipId = membershipId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public LocalDateTime getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(LocalDateTime joinTime) {
        this.joinTime = joinTime;
    }

    public boolean isCreator() {
        return isCreator;
    }

    public void setCreator(boolean creator) {
        isCreator = creator;
    }


    public List<GroupPostComment> getGroupPostCommentList() {
        return groupPostCommentList;
    }

    public void setGroupPostCommentList(List<GroupPostComment> groupPostCommentList) {
        this.groupPostCommentList = groupPostCommentList;
    }

    @Override
    public GroupMembershipDTO toDto() {
        return new GroupMembershipDTO(
                getGroupMembershipId(),
                getUserProfile().toDto(),
                getJoinTime(),
                isCreator()
        );
    }

    // Not updatable
    @Override
    public void updateFromDto(GroupMembershipDTO groupMembershipDTO) {
    }
}