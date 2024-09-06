package tigertalk.model.Group;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "group_all")
public class Group {

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMembership> groupMemberList = new LinkedList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPost> groupPostList = new LinkedList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;

    private String groupName;

    private boolean isPrivate;

    private String interest;

    private String groupImg = "https://mediaim.expedia.com/destination/7/bb1caab964e8be84036cd5ee7b05e787.jpg?impolicy=fcrop&w=1920&h=480&q=medium";

    private LocalDateTime groupCreateTime = LocalDateTime.now();

    public Group() {
    }

    public Group(String groupName, boolean isPrivate, String interest) {
        this.groupName = groupName;
        this.isPrivate = isPrivate;
        this.interest = interest;
    }



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
                .map(GroupPost::toDto)
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

    public void updateFromDto(GroupDTO groupDTO) {
        this.groupName = groupDTO.groupName();
        this.isPrivate = groupDTO.isPrivate();
        this.interest = groupDTO.interest();
        this.groupImg = groupDTO.groupImg();
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

    public void setGroupPostList(List<GroupPost> groupPostList) {
        this.groupPostList = groupPostList;
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

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
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

}