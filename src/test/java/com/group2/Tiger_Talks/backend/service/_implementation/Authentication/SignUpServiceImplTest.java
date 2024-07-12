package com.group2.Tiger_Talks.backend.service._implementation.Authentication;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = SignUpServiceImplTest.class)
public class SignUpServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private SignUpServiceImpl signUpServiceImpl;

    private UserProfile userA;
    private UserProfile userB;

    @Before
    public void setUp(){
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
     *  Test case for signUpUserProfile
     */
    @Test
    public void signUpUserProfile_invalid_firstName() {
        userA.setFirstName("Eric@");
        Optional<String> result = signUpServiceImpl.signUpUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("First name must contain no symbols", result.get());
    }

    @Test
    public void signUpUserProfile_invalid_lastName() {
        userA.setLastName("Bob@");
        Optional<String> result = signUpServiceImpl.signUpUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Last name must contain no symbols", result.get());
    }

    @Test
    public void signUpUserProfile_invalid_age() {
        userA.setAge(-1);
        Optional<String> result = signUpServiceImpl.signUpUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Age must be greater than 0", result.get());
    }

    @Test
    public void signUpUserProfile_invalid_email() {
        userA.setEmail("hh@126.com");
        Optional<String> result = signUpServiceImpl.signUpUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Invalid email address. Please use dal email address!", result.get());
    }

    @Test
    public void signUpUserProfile_existed_username() {
        userA.setUserName("kirito");
        userB.setUserName("kirito");
        when(userProfileRepository.findUserProfileByUserName(userA.getUserName())).thenReturn(Optional.of(userB));
        when(userProfileRepository.existsById(userA.getEmail())).thenReturn(false);
        Optional<String> result = signUpServiceImpl.signUpUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Username has already existed!", result.get());
    }

    @Test
    public void signUpUserProfile_existed_email() {
        when(userProfileRepository.existsById(userA.getEmail())).thenReturn(true);
        Optional<String> result = signUpServiceImpl.signUpUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Email has already existed!", result.get());
    }

    @Test
    public void signUpUserProfile_password_invalid_length() {
        userA.setPassword("aaaa");
        Optional<String> result = signUpServiceImpl.signUpUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Password must have a minimum length of 8 characters.", result.get());
    }

    @Test
    public void signUpUserProfile_password_no_uppercase() {
        userA.setPassword("aaaaaaaa");
        Optional<String> result = signUpServiceImpl.signUpUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 uppercase character.", result.get());
    }

    @Test
    public void signUpUserProfile_password_no_lowercase() {
        userA.setPassword("AAAAAAAA");
        Optional<String> result = signUpServiceImpl.signUpUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 lowercase character.", result.get());
    }

    @Test
    public void signUpUserProfile_password_no_number() {
        userA.setPassword("AAAAaaaa");
        Optional<String> result = signUpServiceImpl.signUpUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 number.", result.get());
    }

    @Test
    public void signUpUserProfile_password_no_specialCharacter() {
        userA.setPassword("AAAaaa11");
        Optional<String> result = signUpServiceImpl.signUpUserProfile(userA);
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 special character.", result.get());
    }

    @Test
    public void signUpUserProfile_normal() {
        assertTrue(signUpServiceImpl.signUpUserProfile(userA).isEmpty());
    }

    @Test
    public void signUpUserProfile_normal_save() {
        userA.setEmail("123@dal.ca");
        when(userProfileRepository.findUserProfileByUserName(userA.getUserName())).thenReturn(Optional.empty());
        when(userProfileRepository.existsById(userA.getEmail())).thenReturn(false);
        Optional<String> result = signUpServiceImpl.signUpUserProfile(userA);

        assertTrue(result.isEmpty());
        ArgumentCaptor<UserProfile> userProfileCaptor = ArgumentCaptor.forClass(UserProfile.class);
        verify(userProfileRepository).save(userProfileCaptor.capture());
        assertEquals(userA, userProfileCaptor.getValue());
    }

    /**
     *  Test case for signUpTest: This may be deleted since it is a temporary api
     */
}