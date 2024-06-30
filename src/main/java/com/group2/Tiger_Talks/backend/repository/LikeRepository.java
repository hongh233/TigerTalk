package com.group2.Tiger_Talks.backend.repository;

import com.group2.Tiger_Talks.backend.model.Like;
import com.group2.Tiger_Talks.backend.model.Post;
import com.group2.Tiger_Talks.backend.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    Optional<Like> findByPostAndUserProfile(Post post, UserProfile userProfile);
}