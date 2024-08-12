package com.group2.Tiger_Talks.backend.model.Authentication;

/**
 * Data Transfer Object for ForgotPassword.
 * This record is used to transfer forgot password when resting passwords
 *
 * @param email    the email of the user who forgot the password
 * @param password the new password
 */
public record ForgotPasswordDTO(
        String email,
        String password
) {
}
