package tigertalk.Tiger_Talks.backend.service._implementation.Authentication;

import tigertalk.model.Authentication.PasswordToken;
import tigertalk.model.User.UserProfile;
import tigertalk.repository.Authentication.PasswordTokenRepository;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service._implementation.Authentication.PasswordResetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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
        userA = new UserProfile();
        userA.setFirstName("Along");
        userA.setLastName("Aside");
        userA.setAge(22);
        userA.setGender("Male");
        userA.setUserName("userA");
        userA.setEmail("a@dal.ca");
        userA.setPassword("aaaa1A@a");
        userA.setSecurityQuestion("1");
        userA.setSecurityQuestionAnswer("What was your favourite book as a child?");

        userB = new UserProfile();
        userB.setFirstName("Beach");
        userB.setLastName("Boring");
        userB.setAge(21);
        userB.setGender("Male");
        userB.setUserName("userB");
        userB.setEmail("b@dal.ca");
        userB.setPassword("aaaa1A@a");
        userB.setSecurityQuestion("1");
        userB.setSecurityQuestionAnswer("What was your favourite book as a child?");
    }

    /**
     * Test case for createAndSendResetMail
     */
    @Test
    public void createAndSendResetMail_email_exist() {
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.of(userA));
        passwordResetServiceImpl.createAndSendResetMail(userA.getEmail());
        verify(passwordTokenRepository).save(any(PasswordToken.class));
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
        PasswordToken foundToken = mock(PasswordToken.class);
        when(passwordTokenRepository.findPasswordTokenByToken(token)).thenReturn(Optional.of(foundToken));
        when(foundToken.isTokenExpired()).thenReturn(false);
        Optional<String> result = passwordResetServiceImpl.validateToken(token);
        assertFalse(result.isPresent());
    }

    @Test
    public void validateToken_token_exist_expired() {
        String token = "token exist expired";
        PasswordToken foundToken = mock(PasswordToken.class);
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
        Optional<String> result = passwordResetServiceImpl.resetPassword("user@dal.ca", "aaaa");
        assertTrue(result.isPresent());
        assertEquals("Password must have a minimum length of 8 characters.", result.get());
    }

    @Test
    public void resetPassword_invalid_noUppercase() {
        Optional<String> result = passwordResetServiceImpl.resetPassword("user@dal.ca", "aaaaaaaa");
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 uppercase character.", result.get());
    }

    @Test
    public void resetPassword_invalid_noLowercase() {
        Optional<String> result = passwordResetServiceImpl.resetPassword("user@dal.ca", "AAAAAAAA");
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 lowercase character.", result.get());
    }

    @Test
    public void resetPassword_invalid_noNumber() {
        Optional<String> result = passwordResetServiceImpl.resetPassword("user@dal.ca", "AAAAaaaa");
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 number.", result.get());
    }

    @Test
    public void resetPassword_invalid_noSpecialCharacter() {
        Optional<String> result = passwordResetServiceImpl.resetPassword("user@dal.ca", "aaaa1Aaa");
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 special character.", result.get());
    }

    @Test
    public void resetPassword_valid_userExist() {
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.of(userA));
        assertTrue(passwordResetServiceImpl.resetPassword(userA.getEmail(), userA.getPassword()).isEmpty());
    }

    @Test
    public void resetPassword_valid_userNotExist() {
        Optional<String> result = passwordResetServiceImpl.resetPassword(userB.getEmail(), userA.getPassword());
        lenient().when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.of(userA));
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
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.empty());
        Optional<String> result = passwordResetServiceImpl.validateEmailExist(userA.getEmail());
        assertTrue(result.isPresent());
        assertEquals("Email does not exist in our system!", result.get());
    }

    @Test
    public void validateEmailExist_existedEmail() {
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.of(userA));
        Optional<String> result = passwordResetServiceImpl.validateEmailExist(userA.getEmail());
        assertTrue(result.isEmpty());
    }

}