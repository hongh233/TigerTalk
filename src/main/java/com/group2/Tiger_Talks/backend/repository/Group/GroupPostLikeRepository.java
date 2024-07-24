package com.group2.Tiger_Talks.backend.repository.Group;

import com.group2.Tiger_Talks.backend.model.Group.GroupPost;
import com.group2.Tiger_Talks.backend.model.Group.GroupPostLike;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupPostLikeRepository extends JpaRepository<GroupPostLike, Integer> {
    Optional<GroupPostLike> findByGroupPostGroupPostIdAndUserProfileEmail(Integer groupPostId, String userProfileEmail);
}