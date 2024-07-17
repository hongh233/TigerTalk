package com.group2.Tiger_Talks.backend.repository.Post;

import com.group2.Tiger_Talks.backend.model.Post.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {
    List<PostComment> findByPost_PostId(Integer postId);

}


