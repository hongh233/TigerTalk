package tigertalk.Tiger_Talks.backend.service._implementation.Authentication;

import tigertalk.model.User.UserProfile;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service._implementation.Authentication.SignUpServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SignUpServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private SignUpServiceImpl signUpServiceImpl;

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
     * Test case for signUpUserProfile
     */
    @Test
    public void signUpUserProfile_invalid_firstName() {
        userA.setFirstName("Eric@");
        Optional<String> result = signUpServiceImpl.signupUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("First name must contain no symbols", result.get());
    }

    @Test
    public void signUpUserProfile_invalid_lastName() {
        userA.setLastName("Bob@");
        Optional<String> result = signUpServiceImpl.signupUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Last name must contain no symbols", result.get());
    }

    @Test
    public void signUpUserProfile_invalid_age() {
        userA.setAge(-1);
        Optional<String> result = signUpServiceImpl.signupUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Age must be greater than 0", result.get());
    }

    @Test
    public void signUpUserProfile_invalid_email() {
        userA.setEmail("hh@126.com");
        Optional<String> result = signUpServiceImpl.signupUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Invalid email address. Please use dal email address!", result.get());
    }



    @Test
    public void signUpUserProfile_existed_email() {
        when(userProfileRepository.existsById(userA.getEmail())).thenReturn(true);
        Optional<String> result = signUpServiceImpl.signupUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Email has already existed!", result.get());
    }

    @Test
    public void signUpUserProfile_password_invalid_length() {
        userA.setPassword("aaaa");
        Optional<String> result = signUpServiceImpl.signupUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Password must have a minimum length of 8 characters.", result.get());
    }

    @Test
    public void signUpUserProfile_password_no_uppercase() {
        userA.setPassword("aaaaaaaa");
        Optional<String> result = signUpServiceImpl.signupUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 uppercase character.", result.get());
    }

    @Test
    public void signUpUserProfile_password_no_lowercase() {
        userA.setPassword("AAAAAAAA");
        Optional<String> result = signUpServiceImpl.signupUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 lowercase character.", result.get());
    }

    @Test
    public void signUpUserProfile_password_no_number() {
        userA.setPassword("AAAAaaaa");
        Optional<String> result = signUpServiceImpl.signupUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 number.", result.get());
    }

    @Test
    public void signUpUserProfile_password_no_specialCharacter() {
        userA.setPassword("AAAaaa11");
        Optional<String> result = signUpServiceImpl.signupUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 special character.", result.get());
    }

    @Test
    public void signUpUserProfile_normal() {
        assertTrue(signUpServiceImpl.signupUserProfile(userA).isEmpty());
    }

}