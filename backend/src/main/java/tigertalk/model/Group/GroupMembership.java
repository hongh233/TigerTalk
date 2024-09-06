package tigertalk.model.Group;

import tigertalk.model.User.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class GroupMembership {

    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "email")
    private UserProfile userProfile;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupMembershipId;

    private LocalDateTime joinTime = LocalDateTime.now();

    private boolean isCreator;


    public GroupMembership() {
    }

    public GroupMembership(Group group, UserProfile userProfile, boolean isCreator) {
        this.group = group;
        this.userProfile = userProfile;
        this.isCreator = isCreator;
    }

    public GroupMembershipDTO toDto() {
        return new GroupMembershipDTO(
                getGroupMembershipId(),
                getUserProfile().toDto(),
                getJoinTime(),
                isCreator()
        );
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

    public int getGroupMembershipId() {
        return groupMembershipId;
    }

    public void setGroupMembershipId(int groupMembershipId) {
        this.groupMembershipId = groupMembershipId;
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