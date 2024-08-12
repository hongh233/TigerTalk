package tigertalk.repository.Friend;

import tigertalk.model.Friend.FriendshipMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipMessageRepository extends JpaRepository<FriendshipMessage, Integer> {
    List<FriendshipMessage> findByFriendship_FriendshipId(int friendshipId);
}