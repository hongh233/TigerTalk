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
}
