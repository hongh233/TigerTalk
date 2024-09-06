package tigertalk.model.Group;

import tigertalk.model.User.UserProfile;
import jakarta.persistence.*;

@Entity
public class GroupPostLike {

    @ManyToOne
    @JoinColumn(name = "group_post_id")
    private GroupPost groupPost;
    @ManyToOne
    @JoinColumn(name = "user_profile_id", referencedColumnName = "email")
    private UserProfile userProfile;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public GroupPostLike() {

    }

    public GroupPostLike(GroupPost groupPost, UserProfile userProfile) {
        this.groupPost = groupPost;
        this.userProfile = userProfile;
    }

    public GroupPost getGroupPost() {
        return groupPost;
    }

    public void setGroupPost(GroupPost groupPost) {
        this.groupPost = groupPost;
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
