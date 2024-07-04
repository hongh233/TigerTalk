package com.group2.Tiger_Talks.backend.service;

import com.group2.Tiger_Talks.backend.model.Post.Post;
import com.group2.Tiger_Talks.backend.model.Post.PostDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PostService {

    /**
     * Retrieves posts for a user and their friends based on the user's email.
     *
     * @param email the email of the user
     * @return a list of posts sorted by timestamp in descending order
     */
    List<PostDTO> getPostsForUserAndFriends(String email);

    /**
     * Retrieves posts for a user's friends based on the user's email.
     *
     * @param email the email of the user
     * @return a list of posts sorted by timestamp in descending order
     */

    List<PostDTO> getPostsForUser(String email);

    /**
     * Creates a new post.
     *
     * @param post the post to be created
     * @return an optional containing an error message if the user does not exist,
     * or empty if the post was created successfully
     */
    Optional<String> createPost(Post post);

    /**
     * Deletes a post by its ID.
     *
     * @param postId the ID of the post to be deleted
     * @return an optional containing an error message if the post does not exist,
     * or empty if the post was deleted successfully
     */
    Optional<String> deletePostById(Integer postId);

    /**
     * Deletes a post.
     *
     * @param post the post to be deleted
     * @return an optional containing an error message if the post does not exist, or empty if the post was deleted successfully
     */
    Optional<String> deletePost(Post post);

    /**
     * Updates a post by its ID.
     *
     * @param postId the ID of the post to be updated
     * @param post   the updated post-data
     * @return an optional containing an error message if the post does not exist,
     * or empty if the post was updated successfully
     */
    Optional<String> updatePostById(Integer postId, Post post);

    /**
     * Likes a post by a user.
     *
     * @param postId    Post ID to like
     * @param userEmail User's email
     * @return Updated Post entity after like operation
     */
    Post likePost(Integer postId, String userEmail);

}
