package com.group2.Tiger_Talks.backend.model.Group;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupUpdate {

    private int groupId;
    private String groupName;
    private String groupImg;
    private boolean isPrivate;


    public GroupUpdate(int groupId, String groupName, String groupImg, boolean isPrivate) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupImg = groupImg;
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

    public String getGroupImg() {
        return groupImg;
    }

    public void setGroupImg(String groupImg) {
        this.groupImg = groupImg;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }


}
