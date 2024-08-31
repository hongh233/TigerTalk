package tigertalk.model.Authentication;

/**
 *
 * @param email
 * @param password
 */
public record EmailPassword(
        String email,
        String password
) {
}
