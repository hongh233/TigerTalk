package com.group2.Tiger_Talks.backend.repository.Group;

import com.group2.Tiger_Talks.backend.model.Group.GroupPost;
import com.group2.Tiger_Talks.backend.model.Group.GroupPostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupPostRepository extends JpaRepository<GroupPost, Integer> {
}
