package tigertalk.model.Post;

public record PostLikeDTO (
        int numberOfLikes,
        boolean isLike
) {
}