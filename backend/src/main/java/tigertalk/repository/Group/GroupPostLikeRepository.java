package tigertalk.repository.Group;

import tigertalk.model.Group.GroupPostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupPostLikeRepository extends JpaRepository<GroupPostLike, Integer> {
    Optional<GroupPostLike> findByGroupPostGroupPostIdAndUserProfileEmail(Integer groupPostId, String userProfileEmail);
}