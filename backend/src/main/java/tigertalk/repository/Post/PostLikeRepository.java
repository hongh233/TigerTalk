package tigertalk.repository.Post;

import tigertalk.model.Post.Post;
import tigertalk.model.Post.PostLike;
import tigertalk.model.User.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
    Optional<PostLike> findByPostAndUserProfile(Post post, UserProfile userProfile);
}