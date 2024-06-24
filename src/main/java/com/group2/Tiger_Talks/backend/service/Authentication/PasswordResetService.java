package com.group2.Tiger_Talks.backend.service.Authentication;

import com.group2.Tiger_Talks.backend.service.DTO.ForgotPasswordDTO;

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
     * Resets password for the given user
     *
     * @param passwordDTO The email for said user
     * @return An error if one was encountered
     */
    Optional<String> resetPassword(ForgotPasswordDTO passwordDTO);

    /**
     * Constructs a password OTP token that would be sent to the user's email
     *
     * @param email The email to be sent too
     */
    Optional<String> createAndSendResetMail(String email);

    /**
     * validate whether the email is in the database and in correct form
     * @param email The email to be sent too
     * @return error message if email is not in the database or not in correct form
     *         empty if email exist and correct
     */
    Optional<String> validateEmailExist(String email);


    /**
     *
     * @param email   user email
     * @param answer1 security answer 1
     * @param answer2 security answer 2
     * @param answer3 security answer 3
     * @return error message if answer is not correct empty if correct
     */
    Optional<String> verifySecurityAnswers(String email, String answer1, String answer2, String answer3);

    String[] getSecurityQuestions(String email);
}
