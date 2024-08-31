package tigertalk.service._implementation.Post;

import tigertalk.model.Notification.Notification;
import tigertalk.model.Post.Post;
import tigertalk.model.Post.PostComment;
import tigertalk.model.Post.PostCommentDTO;
import tigertalk.model.User.UserProfile;
import tigertalk.repository.Post.PostCommentRepository;
import tigertalk.repository.Post.PostRepository;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.Notification.NotificationService;
import tigertalk.service.Post.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostCommentServiceImpl implements PostCommentService {

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public PostCommentDTO addComment(PostCommentDTO postCommentDTO) {
        Optional<Post> post = postRepository.findById(postCommentDTO.postId());
        if (post.isEmpty()) {
            throw new RuntimeException("Post not found");
        }

        Optional<UserProfile> commentSenderUserProfile = userProfileRepository.findById(postCommentDTO.commentSenderUserProfileDTO().email());
        if (commentSenderUserProfile.isEmpty()) {
            throw new RuntimeException("Comment sender user profile not found");
        }

        Optional<UserProfile> postSenderUserProfile = userProfileRepository.findById(postCommentDTO.postSenderUserProfileDTO().email());
        if (postSenderUserProfile.isEmpty()) {
            throw new RuntimeException("Post sender user profile not found");
        }

        PostComment postComment = new PostComment(
                post.get(),
                postCommentDTO.content(),
                commentSenderUserProfile.get()
        );

        PostComment savedComment = postCommentRepository.save(postComment);

        if (!postSenderUserProfile.get().getEmail().equals(commentSenderUserProfile.get().getEmail())) {
            Notification notification = new Notification(
                    postSenderUserProfile.get(),
                    "You have a new comment on your post by " + commentSenderUserProfile.get().getEmail(),
                    "PostComment"
            );
            notificationService.createNotification(notification);
        }

        return savedComment.toDto();
    }

    @Override
    public List<PostCommentDTO> getCommentsByPostId(Integer postId) {
        List<PostComment> postComments = postCommentRepository.findByPost_PostId(postId);
        List<PostCommentDTO> postCommentDTOs = new ArrayList<>();
        for (PostComment postComment : postComments) {
            postCommentDTOs.add(postComment.toDto());
        }
        return postCommentDTOs;
    }

}
