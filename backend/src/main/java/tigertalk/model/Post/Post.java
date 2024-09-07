package tigertalk.model.Post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import tigertalk.model.User.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Post {

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PostComment> postComments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_profile_id", referencedColumnName = "email")
    @JsonBackReference
    private UserProfile userProfile;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(columnDefinition ="TEXT")
    private String content;
    private String associatedImageURL;
    private Boolean edited;
    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    public Post() {
    }

    public Post(UserProfile userProfile, String content, String associatedImageURL) {
        this.userProfile = userProfile;
        this.content = content;
        this.associatedImageURL = associatedImageURL;
        this.edited = false;
    }

    public PostDTO toDto() {
        return new PostDTO(
                postId,
                userProfile.getEmail(),
                content,
                timestamp,
                postLikes.size(),
                userProfile.getUserName(),
                userProfile.getProfilePictureUrl(),
                associatedImageURL,
                edited,
                userProfile.getOnlineStatus()
        );
    }

    public List<PostComment> getPostComments() {
        return postComments;
    }

    public void setPostComments(List<PostComment> postComments) {
        this.postComments = postComments;
    }

    public List<PostLike> getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(List<PostLike> postLikes) {
        this.postLikes = postLikes;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAssociatedImageURL() {
        return associatedImageURL;
    }

    public void setAssociatedImageURL(String associatedImageURL) {
        this.associatedImageURL = associatedImageURL;
    }

    public Boolean getEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}