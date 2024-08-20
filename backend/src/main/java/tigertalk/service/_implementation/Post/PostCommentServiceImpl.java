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

    @Autowired
    private NotificationService notificationService;


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
                commentSenderUserProfile.get()
        );

        // save the comment
        PostComment savedComment = postCommentRepository.save(postComment);

        // Create and send notification if your not the one commenting on your own post
        if (!postSenderUserProfile.get().email().equals(commentSenderUserProfile.get().email())) {
            Notification notification = new Notification(
                    postSenderUserProfile.get(),
                    "You have a new comment on your post by " + commentSenderUserProfile.get().email(),
                    "PostComment"
            );
            notificationService.createNotification(notification);
        }

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