package tigertalk.Tiger_Talks.backend.service._implementation.Authentication;

import tigertalk.model.Authentication.ForgotPasswordDTO;
import tigertalk.model.Authentication.PasswordTokenImpl;
import tigertalk.model.User.UserProfile;
import tigertalk.repository.PasswordTokenRepository;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service._implementation.Authentication.PasswordResetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PasswordResetServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private PasswordTokenRepository passwordTokenRepository;

    @InjectMocks
    private PasswordResetServiceImpl passwordResetServiceImpl;

    private UserProfile userA;
    private UserProfile userB;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userA = new UserProfile(
                "Along",
                "Aside",
                22,
                "Male",
                "userA",
                "a@dal.ca",
                "aaaa1A@a",
                new String[]{"1", "2", "3"},
                new String[]{
                        "What was your favourite book as a child?",
                        "In what city were you born?",
                        "What is the name of the hospital where you were born?"
                }
        );
        userB = new UserProfile(
                "Beach",
                "Boring",
                21,
                "Male",
                "userB",
                "b@dal.ca",
                "aaaa1A@a",
                new String[]{"1", "2", "3"},
                new String[]{
                        "What was your favourite book as a child?",
                        "In what city were you born?",
                        "What is the name of the hospital where you were born?"
                }
        );
    }

    /**
     * Test case for createAndSendResetMail
     */
    @Test
    public void createAndSendResetMail_email_exist() {
        when(userProfileRepository.findById(userA.email())).thenReturn(Optional.of(userA));
        passwordResetServiceImpl.createAndSendResetMail(userA.email());
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
        lenient().when(userProfileRepository.findById("11@gmail.com")).thenReturn(Optional.empty());
        Optional<String> result = passwordResetServiceImpl.createAndSendResetMail("11@gmail.com");
        assertTrue(result.isPresent());
        assertEquals("Invalid email address. Please use dal email address!", result.get());
    }

    /**
     * Test case for validateToken
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
     * Test case for resetPassword
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
        when(userProfileRepository.findById(userA.email())).thenReturn(Optional.of(userA));
        assertTrue(passwordResetServiceImpl.resetPassword(
                new ForgotPasswordDTO(userA.email(), userA.getPassword())).isEmpty());
    }

    @Test
    public void resetPassword_valid_userNotExist() {
        Optional<String> result = passwordResetServiceImpl.resetPassword(
                new ForgotPasswordDTO(userB.email(), userA.getPassword()));
        lenient().when(userProfileRepository.findById(userA.email())).thenReturn(Optional.of(userA));
        assertTrue(result.isPresent());
        assertEquals("An error occurred while resetting your password. " +
                "The email used no longer belongs to any account", result.get());
    }

    /**
     * Test case for validateEmailExist
     */
    @Test
    public void validateEmailExist_invalidEmail() {
        Optional<String> result = passwordResetServiceImpl.validateEmailExist("invalid@gmail.com");
        assertTrue(result.isPresent());
        assertEquals("Invalid email address. Please use dal email address!", result.get());
    }

    @Test
    public void validateEmailExist_notExistedEmail() {
        when(userProfileRepository.findById(userA.email())).thenReturn(Optional.empty());
        Optional<String> result = passwordResetServiceImpl.validateEmailExist(userA.email());
        assertTrue(result.isPresent());
        assertEquals("Email does not exist in our system!", result.get());
    }

    @Test
    public void validateEmailExist_existedEmail() {
        when(userProfileRepository.findById(userA.email())).thenReturn(Optional.of(userA));
        Optional<String> result = passwordResetServiceImpl.validateEmailExist(userA.email());
        assertTrue(result.isEmpty());
    }

    /**
     * Test case for verifySecurityAnswers
     */
    @Test
    public void verifySecurityAnswers_email_not_found() {
        when(userProfileRepository.findById(userA.email())).thenReturn(Optional.empty());
        Optional<String> result = passwordResetServiceImpl.verifySecurityAnswers(
                userA.email(),
                userA.getSecurityQuestions()[0],
                userA.getSecurityQuestionsAnswer()[0]);
        assertTrue(result.isPresent());
        assertEquals("Fatal Error: Cannot find email specified in the database", result.get());
    }

    @Test
    public void verifySecurityAnswers_answer_not_exist() {
        String email = userA.email();
        String question = userA.getSecurityQuestions()[0];
        String answer = userA.getSecurityQuestionsAnswer()[0];
        UserProfile mockUser = mock(UserProfile.class);

        when(userProfileRepository.findById(email)).thenReturn(Optional.of(mockUser));
        when(mockUser.findAnswerForSecurityQuestion(question)).thenReturn(Optional.empty());

        Optional<String> result = passwordResetServiceImpl.verifySecurityAnswers(email, question, answer);
        assertTrue(result.isPresent());
        assertEquals("Fatal Error: Answer doesn't exist for this question", result.get());
    }

    @Test
    public void verifySecurityAnswers_answer_correct() {
        String email = userA.email();
        String question = userA.getSecurityQuestions()[0];
        String answer = userA.getSecurityQuestionsAnswer()[0];
        UserProfile mockUser = mock(UserProfile.class);

        when(userProfileRepository.findById(email)).thenReturn(Optional.of(mockUser));
        when(mockUser.findAnswerForSecurityQuestion(question)).thenReturn(Optional.of(answer));

        Optional<String> result = passwordResetServiceImpl.verifySecurityAnswers(email, question, answer);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testVerifySecurityAnswers_answer_incorrect() {
        String email = userA.email();
        String question = userA.getSecurityQuestions()[0];
        String answer = userA.getSecurityQuestionsAnswer()[0];
        UserProfile mockUser = mock(UserProfile.class);

        when(userProfileRepository.findById(email)).thenReturn(Optional.of(mockUser));
        when(mockUser.findAnswerForSecurityQuestion(question)).thenReturn(Optional.of(answer));

        Optional<String> result = passwordResetServiceImpl.verifySecurityAnswers(email, question, "WrongAnswer");
        assertTrue(result.isPresent());
        assertEquals("Security questions answers are incorrect.", result.get());
    }

    /**
     * Test case for getSecurityQuestions
     */
    @Test
    public void getSecurityQuestions_normal() {
        UserProfile mockUser = mock(UserProfile.class);
        String email = userA.email();
        String[] securityQuestions = userA.getSecurityQuestions();

        when(userProfileRepository.findById(email)).thenReturn(Optional.of(mockUser));
        when(mockUser.getSecurityQuestions()).thenReturn(securityQuestions);
        assertArrayEquals(securityQuestions, passwordResetServiceImpl.getSecurityQuestions(email));
    }

    @Test
    public void getSecurityQuestions_userNotFound() {
        String email = userA.email();
        when(userProfileRepository.findById(email)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> passwordResetServiceImpl.getSecurityQuestions(email));
    }
}