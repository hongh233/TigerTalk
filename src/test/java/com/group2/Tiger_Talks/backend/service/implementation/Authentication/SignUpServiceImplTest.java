package com.group2.Tiger_Talks.backend.service.implementation.Authentication;

import com.group2.Tiger_Talks.backend.model.User.UserTemplate;
import com.group2.Tiger_Talks.backend.repsitory.User.UserTemplateRepository;
import com.group2.Tiger_Talks.backend.service.User.UserTemplateService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


public class SignUpServiceImplTest {
    private String[] securityQuestion = {
            "What was your favourite book as a child?",
            "In what city were you born?",
            "What is your favorite movie?"};
    private String[] securityQuestionAnswer = {"ABC", "DEF", "ASD"};

    private UserTemplate userTemplate_valid1 = new UserTemplate(
            "Kirigaya",
            "Kazuto",
            16,
            "male",
            "Kirito",
            951084,
            "kirito122@dal.ca",
            "Aa1@aaaa",
            securityQuestionAnswer,
            securityQuestion);

    private UserTemplate userTemplate_valid2 = new UserTemplate(
            "Yuukii",
            "Asunaa",
            18,
            "female",
            "Asuna",
            103759,
            "asuna233@dal.ca",
            "Dd2$dddd",
            securityQuestionAnswer,
            securityQuestion);
    @Mock
    private UserTemplateRepository userTemplateRepository;

    @Mock
    private UserTemplateService userTemplateService;

    @InjectMocks
    private SignUpServiceImpl signUpService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void signUpUserTemplate_normal() {
        assertEquals(Optional.empty(), signUpService.signUpUserTemplate(userTemplate_valid1));
    }

    @Test
    public void signUpUserTemplate_existedUsername() {
        // simulate username Kirito is in database
        when(userTemplateRepository.findUserTemplateByUserName("Kirito")).thenReturn(Optional.of(userTemplate_valid1));
        userTemplate_valid2.setUserName("Kirito");
        assertEquals(Optional.of("Username has already existed!"), signUpService.signUpUserTemplate(userTemplate_valid2));
    }

    @Test
    public void signUpUserTemplate_invalidBannerID() {
        userTemplate_valid1.setBannerID(1111111);
        assertEquals(Optional.of("Invalid Banner ID. Please input number between 0-999999"),
                signUpService.signUpUserTemplate(userTemplate_valid1));
    }

    @Test
    public void signUpUserTemplate_existedBannerID() {
        // simulate bannerID 951084 is in database
        when(userTemplateRepository.findUserTemplateByBannerID(951084)).
                thenReturn(Optional.of(userTemplate_valid1));
        userTemplate_valid2.setBannerID(951084);
        assertEquals(Optional.of("Banner ID has already existed!"),
                signUpService.signUpUserTemplate(userTemplate_valid2));
    }

    @Test
    public void signUpUserTemplate_invalidEmail() {
        userTemplate_valid1.setEmail("invalidEmail@gmail.com");
        assertEquals(Optional.of("Invalid email address. Please use dal email address!"),
                signUpService.signUpUserTemplate(userTemplate_valid1));
    }

    @Test
    public void signUpUserTemplate_existedEmail() {
        // simulate email kirito122@dal.ca is in database
        when(userTemplateRepository.findUserTemplateByEmail("kirito122@dal.ca"))
                .thenReturn(Optional.of(userTemplate_valid1));
        userTemplate_valid2.setEmail("kirito122@dal.ca");
        assertEquals(Optional.of("Email has already existed!"),
                signUpService.signUpUserTemplate(userTemplate_valid2));
    }

    @Test
    public void signUpUserTemplate_invalidPassword_length() {
        userTemplate_valid1.setPassword("Aa1@aaa");
        assertEquals(Optional.of("Password must have a minimum length of 8 characters."),
                signUpService.signUpUserTemplate(userTemplate_valid1));
    }

    @Test
    public void signUpUserTemplate_invalidPassword_noUppercase() {
        userTemplate_valid1.setPassword("aa1@aaaa");
        assertEquals(Optional.of("Password must have at least 1 uppercase character."),
                signUpService.signUpUserTemplate(userTemplate_valid1));
    }

    @Test
    public void signUpUserTemplate_invalidPassword_noLowercase() {
        userTemplate_valid1.setPassword("AA1@AAAA");
        assertEquals(Optional.of("Password must have at least 1 lowercase character."),
                signUpService.signUpUserTemplate(userTemplate_valid1));
    }

    @Test
    public void signUpUserTemplate_invalidPassword_noNumber() {
        userTemplate_valid1.setPassword("AAb@AAAA");
        assertEquals(Optional.of("Password must have at least 1 number."),
                signUpService.signUpUserTemplate(userTemplate_valid1));
    }

    @Test
    public void signUpUserTemplate_invalidPassword_noSpecialCharacter() {
        userTemplate_valid1.setPassword("AAb1AAAA");
        assertEquals(Optional.of("Password must have at least 1 special character."),
                signUpService.signUpUserTemplate(userTemplate_valid1));
    }

    @Test
    public void getAllUserTemplates() {
        when(userTemplateService.getAllUserTemplates()).thenReturn(Arrays.asList(userTemplate_valid1, userTemplate_valid2));
        assertEquals(Arrays.asList(userTemplate_valid1, userTemplate_valid2), signUpService.getAllUserTemplates());
    }
}