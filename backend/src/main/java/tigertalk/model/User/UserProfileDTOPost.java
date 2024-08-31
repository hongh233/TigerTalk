package tigertalk.model.User;

import tigertalk.model.Post.Post;
import tigertalk.model.Post.PostDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object for UserProfile with associated posts.
 *
 * @param email             the email of the user
 * @param userName          the username of the user
 * @param profilePictureUrl the URL of the user's profile picture
 * @param postList          the list of posts created by the user
 */
public record UserProfileDTOPost(
        String email,
        String userName,
        String profilePictureUrl,
        List<PostDTO> postList
) {
    /**
     * Constructs a UserProfileDTOPost from a UserProfile.
     *
     * @param userProfile the user profile to convert
     */
    public UserProfileDTOPost(UserProfile userProfile) {
        this(
                userProfile.getEmail(),
                userProfile.getUserName(),
                userProfile.getProfilePictureUrl(),
                userProfile.getPostList().stream()
                        .map(Post::toDto)
                        .collect(Collectors.toList())
        );
    }
}
