package tigertalk.model.Group;

import com.fasterxml.jackson.annotation.JsonBackReference;
import tigertalk.model.FullyDTOConvertible;
import tigertalk.model.User.UserProfile;
import tigertalk.service._implementation.Group.GroupPostServiceImpl;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GroupPost implements FullyDTOConvertible<GroupPostDTO> {
    // TODO: Add number of likes to a group post and number of comments

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupPostId;

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


    private LocalDateTime postCreateTime = LocalDateTime.now();
    private String groupPostContent;
    private String groupPostPictureURL;

    public GroupPost() {
    }

    public List<GroupPostLike> getPostLikes() {
        return groupPostLikes;
    }

    public int getNumOfLike() {
        return groupPostLikes.size();
    }

    public void setPostLikes(List<GroupPostLike> postLikes) {
        this.groupPostLikes = postLikes;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public LocalDateTime getPostCreateTime() {
        return postCreateTime;
    }

    public void setPostCreateTime(LocalDateTime messageCreateTime) {
        this.postCreateTime = messageCreateTime;
    }

    public String getGroupPostContent() {
        return groupPostContent;
    }

    public void setGroupPostContent(String groupPostContent) {
        this.groupPostContent = groupPostContent;
    }

    public int getGroupPostId() {
        return groupPostId;
    }

    public void setGroupPostId(int groupPostId) {
        this.groupPostId = groupPostId;
    }


    public List<GroupPostComment> getGroupPostCommentList() {
        return groupPostCommentList;
    }

    public void setGroupPostCommentList(List<GroupPostComment> groupPostCommentList) {
        this.groupPostCommentList = groupPostCommentList;
    }

    public String getGroupPostPictureURL() {
        return groupPostPictureURL;
    }

    public void setGroupPostPictureURL(String groupPostPictureURL) {
        this.groupPostPictureURL = groupPostPictureURL;
    }

    public List<GroupPostLike> getGroupPostLikes() {
        return groupPostLikes;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public GroupPostDTO toDto() {
        return new GroupPostDTO(
                this.getGroupPostId(),
                this.userProfile.email(),
                this.getGroupPostContent(),
                this.getPostCreateTime(),
                this.userProfile.userName(),
                this.userProfile.getProfilePictureUrl(),
                this.getGroupPostPictureURL()
        );
    }

    @Override
    public void updateFromDto(GroupPostDTO groupPostDTO) {
        this.groupPostContent = groupPostDTO.groupPostContent();
        this.postCreateTime = groupPostDTO.groupPostCreateTime();
        this.groupPostPictureURL = groupPostDTO.postPictureURL();
    }
}