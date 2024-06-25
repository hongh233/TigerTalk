package com.group2.Tiger_Talks.backend.service.Post;

import com.group2.Tiger_Talks.backend.model.Post.Post;
import com.group2.Tiger_Talks.backend.repsitory.Post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByTimestampDesc();
    }

    public List<Post> getPostsByUser(String userEmail) {
        return postRepository.findByUserEmailOrderByTimestampDesc(userEmail);
    }

    public Post createPost(String userEmail, String content) {
        Post post = new Post(userEmail, content, LocalDateTime.now());
        return postRepository.save(post);
    }
}
