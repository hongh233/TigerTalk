package com.group2.Tiger_Talks.backend.repository;

import com.group2.Tiger_Talks.backend.model.Post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByOrderByTimestampDesc();
    List<Post> findByUserEmailOrderByTimestampDesc(String userEmail);
}
