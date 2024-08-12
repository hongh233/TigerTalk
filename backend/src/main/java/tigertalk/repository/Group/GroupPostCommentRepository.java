package tigertalk.repository.Group;

import tigertalk.model.Group.GroupPostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupPostCommentRepository extends JpaRepository<GroupPostComment, Integer> {
}
