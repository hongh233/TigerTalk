package com.group2.Tiger_Talks.backend.repository.User;
import com.group2.Tiger_Talks.backend.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
}
