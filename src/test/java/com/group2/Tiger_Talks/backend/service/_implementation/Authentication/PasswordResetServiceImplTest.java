package com.group2.Tiger_Talks.backend.service._implementation.Authentication;

import com.group2.Tiger_Talks.backend.model.Authentication.ForgotPasswordDTO;
import com.group2.Tiger_Talks.backend.model.Authentication.PasswordTokenImpl;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.PasswordTokenRepository;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.group2.Tiger_Talks.backend.service._implementation.UserTemplate.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PasswordResetServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private PasswordTokenRepository passwordTokenRepository;

    @InjectMocks
    private PasswordResetServiceImpl passwordResetServiceImpl;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     *  Test case for createAndSendResetMail
     */
    @Test
    public void createAndSendResetMail_email_exist() {
        when(userProfileRepository.findById(USER_A.getEmail())).thenReturn(Optional.of(USER_A));
        passwordResetServiceImpl.createAndSendResetMail(USER_A.getEmail());
        verify(passwordTokenRepository).save(any(PasswordTokenImpl.class));
    }

    @Test
    public void createAndSendResetMail_email_not_exist() {
        when(userProfileRepository.findById("notExist@dal.ca")).thenReturn(Optional.empty());
        Optional<String> result = passwordResetServiceImpl.createAndSendResetMail("notExist@dal.ca");
        assertTrue(result.isPresent());
        assertEquals("Email does not exist in our system!", result.get());
    }

    @Test
    public void createAndSendResetMail_email_invalid_form() {
        when(userProfileRepository.findById("11@gmail.com")).thenReturn(Optional.empty());
        Optional<String> result = passwordResetServiceImpl.createAndSendResetMail("11@gmail.com");
        assertTrue(result.isPresent());
        assertEquals("Invalid email address. Please use dal email address!", result.get());
    }

    /**
     *  Test case for validateToken
     */
    @Test
    public void validateToken_token_exist_notExpired() {
        String token = "token exist not expired";
        PasswordTokenImpl foundToken = mock(PasswordTokenImpl.class);
        when(passwordTokenRepository.findPasswordTokenByToken(token)).thenReturn(Optional.of(foundToken));
        when(foundToken.isTokenExpired()).thenReturn(false);
        Optional<String> result = passwordResetServiceImpl.validateToken(token);
        assertFalse(result.isPresent());
    }

    @Test
    public void validateToken_token_exist_expired() {
        String token = "token exist expired";
        PasswordTokenImpl foundToken = mock(PasswordTokenImpl.class);
        when(passwordTokenRepository.findPasswordTokenByToken(token)).thenReturn(Optional.of(foundToken));
        when(foundToken.isTokenExpired()).thenReturn(true);
        Optional<String> result = passwordResetServiceImpl.validateToken(token);
        assertTrue(result.isPresent());
        assertEquals("Token has exceeded its time limit and is now expired", result.get());
    }

    @Test
    public void validateToken_token_notExist() {
        String token = "token not exist";
        when(passwordTokenRepository.findPasswordTokenByToken(token)).thenReturn(Optional.empty());
        Optional<String> result = passwordResetServiceImpl.validateToken(token);
        assertTrue(result.isPresent());
        assertEquals("Token is expired / invalid. Try resending the mail", result.get());
    }

    /**
     *  Test case for resetPassword
     */
    @Test
    public void resetPassword_invalid_length() {
        Optional<String> result = passwordResetServiceImpl.resetPassword(
                new ForgotPasswordDTO("user@dal.ca", "aaaa"));
        assertTrue(result.isPresent());
        assertEquals("Password must have a minimum length of 8 characters.", result.get());
    }

    @Test
    public void resetPassword_invalid_noUppercase() {
        Optional<String> result = passwordResetServiceImpl.resetPassword(
                new ForgotPasswordDTO("user@dal.ca", "aaaaaaaa"));
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 uppercase character.", result.get());
    }

    @Test
    public void resetPassword_invalid_noLowercase() {
        Optional<String> result = passwordResetServiceImpl.resetPassword(
                new ForgotPasswordDTO("user@dal.ca", "AAAAAAAA"));
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 lowercase character.", result.get());
    }

    @Test
    public void resetPassword_invalid_noNumber() {
        Optional<String> result = passwordResetServiceImpl.resetPassword(
                new ForgotPasswordDTO("user@dal.ca", "AAAAaaaa"));
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 number.", result.get());
    }

    @Test
    public void resetPassword_invalid_noSpecialCharacter() {
        Optional<String> result = passwordResetServiceImpl.resetPassword(
                new ForgotPasswordDTO("user@dal.ca", "aaaa1Aaa"));
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 special character.", result.get());
    }

    @Test
    public void resetPassword_valid_userExist() {
        when(userProfileRepository.findById(USER_A.getEmail())).thenReturn(Optional.of(USER_A));
        assertTrue(passwordResetServiceImpl.resetPassword(
                        new ForgotPasswordDTO(USER_A.getEmail(), USER_A.getPassword())).isEmpty());
    }

    @Test
    public void resetPassword_valid_userNotExist() {
        Optional<String> result = passwordResetServiceImpl.resetPassword(
                new ForgotPasswordDTO(USER_B.getEmail(), USER_A.getPassword()));
        when(userProfileRepository.findById(USER_A.getEmail())).thenReturn(Optional.of(USER_A));
        assertTrue(result.isPresent());
        assertEquals("An error occurred while resetting your password. " +
                "The email used no longer belongs to any account", result.get());
    }

    /**
     *  Test case for validateEmailExist
     */
    @Test
    public void validateEmailExist_invalidEmail() {
        Optional<String> result = passwordResetServiceImpl.validateEmailExist("invalid@gmail.com");
        assertTrue(result.isPresent());
        assertEquals("Invalid email address. Please use dal email address!", result.get());
    }

    @Test
    public void validateEmailExist_notExistedEmail() {
        when(userProfileRepository.findById(USER_A.getEmail())).thenReturn(Optional.empty());
        Optional<String> result = passwordResetServiceImpl.validateEmailExist(USER_A.getEmail());
        assertTrue(result.isPresent());
        assertEquals("Email does not exist in our system!", result.get());
    }

    @Test
    public void validateEmailExist_existedEmail() {
        when(userProfileRepository.findById(USER_A.getEmail())).thenReturn(Optional.of(USER_A));
        Optional<String> result = passwordResetServiceImpl.validateEmailExist(USER_A.getEmail());
        assertTrue(result.isEmpty());
    }

    /**
     *  Test case for verifySecurityAnswers
     */
    @Test
    public void verifySecurityAnswers_email_not_found() {
        when(userProfileRepository.findById(USER_A.getEmail())).thenReturn(Optional.empty());
        Optional<String> result = passwordResetServiceImpl.verifySecurityAnswers(
                USER_A.getEmail(),
                USER_A.getSecurityQuestions()[0],
                USER_A.getSecurityQuestionsAnswer()[0]);
        assertTrue(result.isPresent());
        assertEquals("Fatal Error: Cannot find email specified in the database", result.get());
    }

    @Test
    public void verifySecurityAnswers_answer_not_exist() {
        String email = USER_A.getEmail();
        String question = USER_A.getSecurityQuestions()[0];
        String answer = USER_A.getSecurityQuestionsAnswer()[0];
        UserProfile mockUser = mock(UserProfile.class);

        when(userProfileRepository.findById(email)).thenReturn(Optional.of(mockUser));
        when(mockUser.findAnswerForSecurityQuestion(question)).thenReturn(Optional.empty());

        Optional<String> result = passwordResetServiceImpl.verifySecurityAnswers(email, question, answer);
        assertTrue(result.isPresent());
        assertEquals("Fatal Error: Answer doesn't exist for this question", result.get());
    }

    @Test
    public void verifySecurityAnswers_answer_correct() {
        String email = USER_A.getEmail();
        String question = USER_A.getSecurityQuestions()[0];
        String answer = USER_A.getSecurityQuestionsAnswer()[0];
        UserProfile mockUser = mock(UserProfile.class);

        when(userProfileRepository.findById(email)).thenReturn(Optional.of(mockUser));
        when(mockUser.findAnswerForSecurityQuestion(question)).thenReturn(Optional.of(answer));

        Optional<String> result = passwordResetServiceImpl.verifySecurityAnswers(email, question, answer);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testVerifySecurityAnswers_answer_incorrect() {
        String email = USER_A.getEmail();
        String question = USER_A.getSecurityQuestions()[0];
        String answer = USER_A.getSecurityQuestionsAnswer()[0];
        UserProfile mockUser = mock(UserProfile.class);

        when(userProfileRepository.findById(email)).thenReturn(Optional.of(mockUser));
        when(mockUser.findAnswerForSecurityQuestion(question)).thenReturn(Optional.of(answer));

        Optional<String> result = passwordResetServiceImpl.verifySecurityAnswers(email, question, "WrongAnswer");
        assertTrue(result.isPresent());
        assertEquals("Security questions answers are incorrect.", result.get());
    }

    /**
     *  Test case for getSecurityQuestions
     */
    @Test
    public void getSecurityQuestions_normal() {
        UserProfile mockUser = mock(UserProfile.class);
        String email = USER_A.getEmail();
        String[] securityQuestions = USER_A.getSecurityQuestions();

        when(userProfileRepository.findById(email)).thenReturn(Optional.of(mockUser));
        when(mockUser.getSecurityQuestions()).thenReturn(securityQuestions);
        assertArrayEquals(securityQuestions, passwordResetServiceImpl.getSecurityQuestions(email));
    }

    @Test
    public void getSecurityQuestions_userNotFound() {
        String email = USER_A.getEmail();
        when(userProfileRepository.findById(email)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            passwordResetServiceImpl.getSecurityQuestions(email);
        });
    }
}