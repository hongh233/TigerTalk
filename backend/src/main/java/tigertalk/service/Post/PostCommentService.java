package tigertalk.service.Post;

import tigertalk.model.Post.PostCommentDTO;

import java.util.List;

public interface PostCommentService {
    /**
     * Adds a comment to a post.
     *
     * @param postCommentDTO the DTO of the comment to be added
     * @return the added PostCommentDTO
     */
    PostCommentDTO addComment(PostCommentDTO postCommentDTO);

    /**
     * Retrieves all comments for a specific post by its ID.
     *
     * @param postId the ID of the post
     * @return a list of PostCommentDTO objects
     */
    List<PostCommentDTO> getCommentsByPostId(Integer postId);

    /**
     * Retrieves all comments.
     *
     * @return a list of all PostCommentDTO objects
     */
    List<PostCommentDTO> getAllComments();
}

