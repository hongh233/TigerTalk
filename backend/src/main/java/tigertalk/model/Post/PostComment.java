package tigertalk.model.Post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import tigertalk.model.FullyDTOConvertible;
import tigertalk.model.User.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PostComment implements FullyDTOConvertible<PostCommentDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "postId")
    @JsonBackReference
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_sender_user_profile", referencedColumnName = "email")
    private UserProfile commentSenderUserProfile;

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

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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


    public UserProfile getCommentSenderUserProfile() {
        return commentSenderUserProfile;
    }

    public void setCommentSenderUserProfile(UserProfile commentSenderUserProfile) {
        this.commentSenderUserProfile = commentSenderUserProfile;
    }

    @Override
    public PostCommentDTO toDto() {
        return new PostCommentDTO(
                this.getCommentId(),
                this.getContent(),
                this.getTimestamp(),
                this.getCommentSenderUserProfile().toDto(),
                this.getPost().getUserProfile().toDto(),
                this.getPost().getPostId()
        );
    }

    @Override
    public void updateFromDto(PostCommentDTO postCommentDTO) {
        this.content = postCommentDTO.content();
        this.timestamp = postCommentDTO.timestamp();
    }
}