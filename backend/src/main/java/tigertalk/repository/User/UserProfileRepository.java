package tigertalk.repository.User;

import tigertalk.model.User.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

}
