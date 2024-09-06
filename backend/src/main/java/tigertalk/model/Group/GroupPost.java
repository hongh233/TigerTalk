package tigertalk.model.Group;

import com.fasterxml.jackson.annotation.JsonBackReference;
import tigertalk.model.User.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GroupPost {
    // TODO: Add number of likes to a group post and number of comments

    @ManyToOne
    @JoinColumn(name = "groupId", referencedColumnName = "groupId", nullable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "user_profile_id", referencedColumnName = "email")
    @JsonBackReference
    private UserProfile userProfile;

    @OneToMany(mappedBy = "groupPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPostComment> groupPostCommentList = new ArrayList<>();
    @OneToMany(mappedBy = "groupPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPostLike> groupPostLikes = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupPostId;
    private LocalDateTime postCreateTime = LocalDateTime.now();
    private String groupPostContent;
    private String groupPostPictureURL;

    public GroupPost() {
    }

    public GroupPostDTO toDto() {
        return new GroupPostDTO(
                this.getGroupPostId(),
                this.userProfile.getEmail(),
                this.getGroupPostContent(),
                this.getPostCreateTime(),
                this.userProfile.getUserName(),
                this.userProfile.getProfilePictureUrl(),
                this.getGroupPostPictureURL()
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

    public List<GroupPostComment> getGroupPostCommentList() {
        return groupPostCommentList;
    }

    public void setGroupPostCommentList(List<GroupPostComment> groupPostCommentList) {
        this.groupPostCommentList = groupPostCommentList;
    }

    public List<GroupPostLike> getGroupPostLikes() {
        return groupPostLikes;
    }

    public void setGroupPostLikes(List<GroupPostLike> groupPostLikes) {
        this.groupPostLikes = groupPostLikes;
    }

    public int getGroupPostId() {
        return groupPostId;
    }

    public void setGroupPostId(int groupPostId) {
        this.groupPostId = groupPostId;
    }

    public LocalDateTime getPostCreateTime() {
        return postCreateTime;
    }

    public void setPostCreateTime(LocalDateTime postCreateTime) {
        this.postCreateTime = postCreateTime;
    }

    public String getGroupPostContent() {
        return groupPostContent;
    }

    public void setGroupPostContent(String groupPostContent) {
        this.groupPostContent = groupPostContent;
    }

    public String getGroupPostPictureURL() {
        return groupPostPictureURL;
    }

    public void setGroupPostPictureURL(String groupPostPictureURL) {
        this.groupPostPictureURL = groupPostPictureURL;
    }

}