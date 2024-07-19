package com.group2.Tiger_Talks.backend.service._implementation.Post;

import com.group2.Tiger_Talks.backend.model.Post.Post;
import com.group2.Tiger_Talks.backend.model.Post.PostComment;
import com.group2.Tiger_Talks.backend.model.Post.PostCommentDTO;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.model.User.UserProfileDTO;
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
        Optional<Post> postOptional = postRepository.findById(postCommentDTO.getPostId());
        if (postOptional.isEmpty()) {
            throw new RuntimeException("Post not found");
        }

        // Get the user information of the comment sent
        //System.out.println(postCommentDTO);
        Optional<UserProfile> commentSenderUserProfile = userProfileRepository.findById(postCommentDTO.getCommentSenderUserProfileDTO().email());
        //Optional<UserProfile> commentSenderUserProfile = userProfileRepository.findUserProfileByEmail(postCommentDTO.getCommentSenderUserProfileDTO().email());
        if (commentSenderUserProfile.isEmpty()) {
            throw new RuntimeException("Comment sender user profile not found");
        }

        // Get the user information of the poster
        Optional<UserProfile> postSenderUserProfile = userProfileRepository.findById(postCommentDTO.getPostSenderUserProfileDTO().email());
        //Optional<UserProfile> postSenderUserProfile = userProfileRepository.findUserProfileByEmail(postCommentDTO.getPostSenderUserProfileDTO().email());
        if (postSenderUserProfile.isEmpty()) {
            throw new RuntimeException("Post sender user profile not found");
        }

        // Create and set PostComment
        PostComment postComment = new PostComment();
        postComment.setPost(postOptional.get());
        postComment.setCommentSenderUserProfile(commentSenderUserProfile.get());
        postComment.setPostSenderUserProfile(postSenderUserProfile.get());
        postComment.setContent(postCommentDTO.getContent());
        postComment.setTimestamp(postCommentDTO.getTimestamp());

        // 保存评论
        PostComment savedComment = postCommentRepository.save(postComment);

        // 转换为DTO
        PostCommentDTO savedCommentDTO = new PostCommentDTO(savedComment);
        savedCommentDTO.setCommentSenderUserProfileDTO(commentSenderUserProfile.get().toDto());
        savedCommentDTO.setPostSenderUserProfileDTO(postSenderUserProfile.get().toDto());

        return savedCommentDTO;
    }

    @Override
    public List<PostCommentDTO> getCommentsByPostId(Integer postId) {
        List<PostComment> comments = postCommentRepository.findByPost_PostId(postId);
        return comments.stream()
                .map(comment -> {
                    PostCommentDTO dto = new PostCommentDTO(comment);
                    userProfileRepository.findById(comment.getCommentSenderUserProfile().getEmail()).ifPresent(userProfile -> {
                        dto.setCommentSenderUserProfileDTO(userProfile.toDto());
                    });
                    userProfileRepository.findById(comment.getPostSenderUserProfile().getEmail()).ifPresent(userProfile -> {
                        dto.setPostSenderUserProfileDTO(userProfile.toDto());
                    });
                    return dto;
                })
                .collect(Collectors.toList());
    }

    //System.out.println(postCommentDTO);
    @Override
    public List<PostCommentDTO> getAllComments() {
        List<PostComment> comments = postCommentRepository.findAll();
        return comments.stream()
                .map(comment -> {
                    PostCommentDTO dto = new PostCommentDTO(comment);
                    userProfileRepository.findById(comment.getCommentSenderUserProfile().getEmail()).ifPresent(userProfile -> {
                        dto.setCommentSenderUserProfileDTO(userProfile.toDto());
                    });
                    userProfileRepository.findById(comment.getPostSenderUserProfile().getEmail()).ifPresent(userProfile -> {
                        dto.setPostSenderUserProfileDTO(userProfile.toDto());
                    });
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
