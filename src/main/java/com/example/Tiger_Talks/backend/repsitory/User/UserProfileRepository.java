package com.example.Tiger_Talks.backend.repsitory.User;

import com.example.Tiger_Talks.backend.model.User.User;
import com.example.Tiger_Talks.backend.model.User.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    UserProfile findUserProfileByUser(User user);
}
