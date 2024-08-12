package tigertalk.model.Group;

import tigertalk.model.User.UserProfile;
import jakarta.persistence.*;

@Entity
public class GroupPostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "group_post_id")
    private GroupPost groupPost;
    @ManyToOne
    @JoinColumn(name = "user_profile_id", referencedColumnName = "email")
    private UserProfile userProfile;

    public GroupPostLike(GroupPost groupPost, UserProfile userProfile) {
        this.groupPost = groupPost;
        this.userProfile = userProfile;
    }

    public GroupPostLike() {
    }

    public Integer getId() {
        return id;
    }

    public GroupPost getPost() {
        return groupPost;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }
}
