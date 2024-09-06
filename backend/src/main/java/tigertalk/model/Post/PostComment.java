package tigertalk.model.Post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import tigertalk.model.User.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PostComment {

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "postId")
    @JsonBackReference
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_sender_user_profile", referencedColumnName = "email")
    private UserProfile commentSenderUserProfile;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    public PostComment() {
    }

    public PostComment(Post post, String content, UserProfile commentSenderUserProfile) {
        this.post = post;
        this.content = content;
        this.commentSenderUserProfile = commentSenderUserProfile;
    }

    public PostCommentDTO toDto() {
        return new PostCommentDTO(
                commentId,
                content,
                timestamp,
                commentSenderUserProfile.toDto(),
                post.getUserProfile().toDto(),
                post.getPostId(),
                commentSenderUserProfile.getOnlineStatus()
        );
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public UserProfile getCommentSenderUserProfile() {
        return commentSenderUserProfile;
    }

    public void setCommentSenderUserProfile(UserProfile commentSenderUserProfile) {
        this.commentSenderUserProfile = commentSenderUserProfile;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


}