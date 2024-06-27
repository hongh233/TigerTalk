package com.group2.Tiger_Talks.backend.service.Post;

import com.group2.Tiger_Talks.backend.model.Post;
import com.group2.Tiger_Talks.backend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Post post = new Post(userEmail, content);
        return postRepository.save(post);
    }
}
