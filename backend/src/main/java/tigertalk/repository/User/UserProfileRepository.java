package tigertalk.repository.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tigertalk.model.User.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

    // We don't care about uppercase or lowercase
    @Query("SELECT u FROM UserProfile u " +
            "WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :content, '%')) " +
            "OR LOWER(u.userName) LIKE LOWER(CONCAT('%', :content, '%')) " +
            "ORDER BY CASE " +
            "WHEN LOWER(u.email) = LOWER(:content) THEN 1 " +
            "WHEN LOWER(u.userName) = LOWER(:content) THEN 1 " +
            "WHEN LOWER(u.email) LIKE LOWER(CONCAT(:content, '%')) THEN 2 " +
            "WHEN LOWER(u.userName) LIKE LOWER(CONCAT(:content, '%')) THEN 2 " +
            "WHEN LOWER(u.email) LIKE LOWER(CONCAT('%', :content)) THEN 3 " +
            "WHEN LOWER(u.userName) LIKE LOWER(CONCAT('%', :content)) THEN 3 " +
            "ELSE 4 END, " +
            "u.email ASC, u.userName ASC")
    List<UserProfile> searchByEmailOrUserName(@Param("content") String content);
}
