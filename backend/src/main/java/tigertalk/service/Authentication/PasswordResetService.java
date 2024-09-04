package tigertalk.service.Authentication;


import java.util.Optional;

public interface PasswordResetService {

    /**
     * Used to validate the token if it exists
     *
     * @param token The token given
     * @return Returns an error message if it was invalid
     */
    Optional<String> validateToken(String token);


    /**
     * Constructs a password OTP token that would be sent to the user's email
     *
     * @param email The email to be sent too
     */
    Optional<String> createAndSendResetMail(String email);

    /**
     * validate whether the email is in the database and in correct form
     *
     * @param email The email to be sent too
     * @return error message if email is not in the database or not in correct form
     * empty if email exist and correct
     */
    Optional<String> validateEmailExist(String email);


    /**
     * @param email          user email
     * @param question       security question
     * @param questionAnswer security question answer
     * @return error message if answer is not correct empty if correct
     */
    Optional<String> verifySecurityAnswers(String email, String question, String questionAnswer);

}
