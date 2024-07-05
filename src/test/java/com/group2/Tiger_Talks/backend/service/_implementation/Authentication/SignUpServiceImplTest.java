package com.group2.Tiger_Talks.backend.service._implementation.Authentication;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.group2.Tiger_Talks.backend.service._implementation.UserTemplate.USER_A;
import static com.group2.Tiger_Talks.backend.service._implementation.UserTemplate.USER_B;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SignUpServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private SignUpServiceImpl signUpServiceImpl;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     *  Test case for signUpUserProfile
     */
    @Test
    public void signUpUserProfile_invalid_firstName() {
        USER_A.setFirstName("Eric@");
        Optional<String> result = signUpServiceImpl.signUpUserProfile(USER_A);
        assertTrue(result.isPresent());
        assertEquals("First name must contain no symbols", result.get());
    }

    @Test
    public void signUpUserProfile_invalid_lastName() {
        USER_A.setLastName("Bob@");
        Optional<String> result = signUpServiceImpl.signUpUserProfile(USER_A);
        assertTrue(result.isPresent());
        assertEquals("Last name must contain no symbols", result.get());
    }

    @Test
    public void signUpUserProfile_invalid_age() {
        USER_A.setAge(-1);
        Optional<String> result = signUpServiceImpl.signUpUserProfile(USER_A);
        assertTrue(result.isPresent());
        assertEquals("Age must be greater than 0", result.get());
    }

    @Test
    public void signUpUserProfile_invalid_email() {
        USER_A.setEmail("hh@126.com");
        Optional<String> result = signUpServiceImpl.signUpUserProfile(USER_A);
        assertTrue(result.isPresent());
        assertEquals("Invalid email address. Please use dal email address!", result.get());
    }

    @Test
    public void signUpUserProfile_existed_username() {
        USER_A.setUserName("kirito");
        USER_B.setUserName("kirito");
        when(userProfileRepository.findUserProfileByUserName(USER_A.getUserName())).thenReturn(Optional.of(USER_B));
        when(userProfileRepository.existsById(USER_A.getEmail())).thenReturn(false);
        Optional<String> result = signUpServiceImpl.signUpUserProfile(USER_A);
        assertTrue(result.isPresent());
        assertEquals("Username has already existed!", result.get());
    }

    @Test
    public void signUpUserProfile_existed_email() {
        when(userProfileRepository.existsById(USER_A.getEmail())).thenReturn(true);
        Optional<String> result = signUpServiceImpl.signUpUserProfile(USER_A);
        assertTrue(result.isPresent());
        assertEquals("Email has already existed!", result.get());
    }

    @Test
    public void signUpUserProfile_password_invalid_length() {
        USER_A.setPassword("aaaa");
        Optional<String> result = signUpServiceImpl.signUpUserProfile(USER_A);
        assertTrue(result.isPresent());
        assertEquals("Password must have a minimum length of 8 characters.", result.get());
    }

    @Test
    public void signUpUserProfile_password_no_uppercase() {
        USER_A.setPassword("aaaaaaaa");
        Optional<String> result = signUpServiceImpl.signUpUserProfile(USER_A);
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 uppercase character.", result.get());
    }

    @Test
    public void signUpUserProfile_password_no_lowercase() {
        USER_A.setPassword("AAAAAAAA");
        Optional<String> result = signUpServiceImpl.signUpUserProfile(USER_A);
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 lowercase character.", result.get());
    }

    @Test
    public void signUpUserProfile_password_no_number() {
        USER_A.setPassword("AAAAaaaa");
        Optional<String> result = signUpServiceImpl.signUpUserProfile(USER_A);
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 number.", result.get());
    }

    @Test
    public void signUpUserProfile_password_no_specialCharacter() {
        USER_A.setPassword("AAAaaa11");
        Optional<String> result = signUpServiceImpl.signUpUserProfile(USER_A);
        assertTrue(result.isPresent());
        assertEquals("Password must have at least 1 special character.", result.get());
    }

    @Test
    public void signUpUserProfile_normal() {
        assertTrue(signUpServiceImpl.signUpUserProfile(USER_A).isEmpty());
    }

    @Test
    public void signUpUserProfile_normal_save() {
        USER_A.setEmail("123@dal.ca");
        when(userProfileRepository.findUserProfileByUserName(USER_A.getUserName())).thenReturn(Optional.empty());
        when(userProfileRepository.existsById(USER_A.getEmail())).thenReturn(false);
        Optional<String> result = signUpServiceImpl.signUpUserProfile(USER_A);

        assertTrue(result.isEmpty());
        ArgumentCaptor<UserProfile> userProfileCaptor = ArgumentCaptor.forClass(UserProfile.class);
        verify(userProfileRepository).save(userProfileCaptor.capture());
        assertEquals(USER_A, userProfileCaptor.getValue());
    }

    /**
     *  Test case for signUpTest: This may be deleted since it is a temporary api
     */
}