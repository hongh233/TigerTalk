package com.group2.Tiger_Talks.backend.repository.User;

import com.group2.Tiger_Talks.backend.model.UserProfile;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
    Optional<UserProfile> findUserProfileByEmail(String email);

    Optional<UserProfile> findUserProfileByUserName(String userName);
}
