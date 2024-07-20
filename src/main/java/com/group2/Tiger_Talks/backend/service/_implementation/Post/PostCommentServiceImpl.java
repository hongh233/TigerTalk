package com.group2.Tiger_Talks.backend.service._implementation.Post;

import com.group2.Tiger_Talks.backend.model.Post.Post;
import com.group2.Tiger_Talks.backend.model.Post.PostComment;
import com.group2.Tiger_Talks.backend.model.Post.PostCommentDTO;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.Post.PostCommentRepository;
import com.group2.Tiger_Talks.backend.repository.Post.PostRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Post.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostCommentServiceImpl implements PostCommentService {

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private PostRepository postRepository;


    @Override
    public PostCommentDTO addComment(PostCommentDTO postCommentDTO) {

        // Get the Post entity
        Optional<Post> post = postRepository.findById(postCommentDTO.postId());
        if (post.isEmpty()) {
            throw new RuntimeException("Post not found");
        }

        // Get the user information of the comment sent
        Optional<UserProfile> commentSenderUserProfile = userProfileRepository.findById(postCommentDTO.commentSenderUserProfileDTO().email());
        if (commentSenderUserProfile.isEmpty()) {
            throw new RuntimeException("Comment sender user profile not found");
        }

        // Get the user information of the poster
        Optional<UserProfile> postSenderUserProfile = userProfileRepository.findById(postCommentDTO.postSenderUserProfileDTO().email());
        if (postSenderUserProfile.isEmpty()) {
            throw new RuntimeException("Post sender user profile not found");
        }

        // Create and set PostComment
        PostComment postComment = new PostComment(
                post.get(),
                postCommentDTO.content(),
                commentSenderUserProfile.get(),
                postSenderUserProfile.get()
        );

        // 保存评论
        PostComment savedComment = postCommentRepository.save(postComment);

        return savedComment.toDto();
    }

    @Override
    public List<PostCommentDTO> getCommentsByPostId(Integer postId) {
        return postCommentRepository.findByPost_PostId(postId)
                .stream()
                .map(PostComment::toDto)
                .toList();
    }

    //System.out.println(postCommentDTO);
    @Override
    public List<PostCommentDTO> getAllComments() {
        return postCommentRepository.findAll()
                .stream()
                .map(PostComment::toDto)
                .collect(Collectors.toList());
    }

}
