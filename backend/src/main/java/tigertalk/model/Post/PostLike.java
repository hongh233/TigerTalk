package tigertalk.model.Post;

import tigertalk.model.User.UserProfile;
import jakarta.persistence.*;

@Entity
public class PostLike {

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_profile_id", referencedColumnName = "email")
    private UserProfile userProfile;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public PostLike() {
    }

    public PostLike(Post post, UserProfile userProfile) {
        this.post = post;
        this.userProfile = userProfile;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}