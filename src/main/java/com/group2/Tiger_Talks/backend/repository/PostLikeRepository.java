package com.group2.Tiger_Talks.backend.repository;

import com.group2.Tiger_Talks.backend.model.PostLike;
import com.group2.Tiger_Talks.backend.model.Post;
import com.group2.Tiger_Talks.backend.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
    Optional<PostLike> findByPostAndUserProfile(Post post, UserProfile userProfile);
}