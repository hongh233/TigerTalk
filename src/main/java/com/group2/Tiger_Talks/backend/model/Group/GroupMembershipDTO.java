package com.group2.Tiger_Talks.backend.model.Group;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.model.User.UserProfileDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class GroupMembershipDTO {

    private int groupMembershipId;

    private UserProfileDTO userProfileDTO;

    private LocalDateTime joinTime;

    private boolean isCreator;

    public GroupMembershipDTO(GroupMembership groupMembership) {
        this.groupMembershipId = groupMembership.getGroupMembershipId();
        this.userProfileDTO = new UserProfileDTO(groupMembership.getUserProfile());
        this.joinTime = groupMembership.getJoinTime();
        this.isCreator = groupMembership.isCreator();
    }


    public int getGroupMembershipId() {
        return groupMembershipId;
    }

    public void setGroupMembershipId(int groupMembershipId) {
        this.groupMembershipId = groupMembershipId;
    }

    public UserProfileDTO getUserProfileDTO() {
        return userProfileDTO;
    }

    public void setUserProfileDTO(UserProfileDTO userProfileDTO) {
        this.userProfileDTO = userProfileDTO;
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

}
