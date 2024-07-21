package com.group2.Tiger_Talks.backend.model.Post;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Post.
 *
 * @param postId              the unique identifier of the post
 * @param email               the email of the user who created the post
 * @param content             the content of the post
 * @param timestamp           the timestamp when the post was created
 * @param numOfLike           the number of likes the post has received
 * @param userProfileUserName the username of the user who created the post
 * @param profileProfileURL   the URL of the profile picture of the user who created the post
 * @param postImageURL        the URL of the image associated with the post
 */
public record PostDTO(
        Integer postId,
        String email,
        String content,
        LocalDateTime timestamp,
        int numOfLike,
        String userProfileUserName,
        String profileProfileURL,
        String postImageURL
) {
}
