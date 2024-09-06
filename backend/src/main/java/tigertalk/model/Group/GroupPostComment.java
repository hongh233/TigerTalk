package tigertalk.model.Group;

import jakarta.persistence.*;
import tigertalk.model.User.UserProfile;

import java.time.LocalDateTime;

@Entity
public class GroupPostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupPostCommentId;

    @ManyToOne
    @JoinColumn(name = "user_profile_id", referencedColumnName = "email")
    private UserProfile groupPostCommentCreator;

    @ManyToOne
    @JoinColumn(name = "group_post_id", referencedColumnName = "groupPostId", nullable = false)
    private GroupPost groupPost;

    private String content;

    @Column(nullable = false)
    private LocalDateTime groupPostCommentCreateTime = LocalDateTime.now();

    public GroupPostComment() {
    }

    public Integer getGroupPostCommentId() {
        return groupPostCommentId;
    }

    public void setGroupPostCommentId(Integer groupPostCommentId) {
        this.groupPostCommentId = groupPostCommentId;
    }


    public GroupPost getGroupPost() {
        return groupPost;
    }

    public void setGroupPost(GroupPost groupPost) {
        this.groupPost = groupPost;
    }

    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getGroupPostCommentCreateTime() {
        return groupPostCommentCreateTime;
    }

    public void setGroupPostCommentCreateTime(LocalDateTime groupPostCommentCreateTime) {
        this.groupPostCommentCreateTime = groupPostCommentCreateTime;
    }
    public UserProfile getGroupPostCommentCreator() {
        return groupPostCommentCreator;
    }

    public void setGroupPostCommentCreator(UserProfile postCommentCreator) {
        this.groupPostCommentCreator = postCommentCreator;
    }


    public GroupPostCommentDTO toDto() {
        return new GroupPostCommentDTO(
                groupPostCommentId,
                content,
                groupPostCommentCreateTime,
                groupPostCommentCreator.getUserName(),
                groupPostCommentCreator.getProfilePictureUrl(),
                groupPostCommentCreator.getEmail(),
                groupPostCommentCreator.getOnlineStatus()
        );
    }

    public void updateFromDto(GroupPostCommentDTO groupPostCommentDTO) {
        this.content = groupPostCommentDTO.groupPostCommentContent();
        this.groupPostCommentCreateTime = groupPostCommentDTO.groupPostCommentCreateTime();
    }
}
