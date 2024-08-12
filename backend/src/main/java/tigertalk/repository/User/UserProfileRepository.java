package tigertalk.repository.User;

import tigertalk.model.User.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
    Optional<UserProfile> findUserProfileByEmail(String email);

    Optional<UserProfile> findUserProfileByUserName(String userName);
}
