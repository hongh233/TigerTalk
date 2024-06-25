package com.group2.Tiger_Talks.backend.authentication;

import com.group2.Tiger_Talks.backend.model.User.UserTemplate;
import com.group2.Tiger_Talks.backend.repsitory.User.UserTemplateRepository;
import com.group2.Tiger_Talks.backend.service.Authentication.LogInService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoginServiceTest {
    @Autowired
    private LogInService logInService;

    @Autowired
    private UserTemplateRepository userTemplateRepository;

    @BeforeEach
    public void setUp() {
        // Clear the repository and add a sample user
        userTemplateRepository.deleteAll();
        UserTemplate user = new UserTemplate(
                "FName",          // firstName
                "LName",          // lastName
                18,               // age
                "u1",             // userName
                "Male",           // gender
                "abc@dal.ca",     // email
                "1234"            // password
        );
        userTemplateRepository.save(user);
    }

    @Test
    @Transactional
    public void logInUserTemplate_ValidCredentials_Success() {
        Optional<UserTemplate> result = logInService.logInUserTemplate("abc@dal.ca", "1234");
        assertTrue(result.isPresent());
        assertEquals("abc@dal.ca", result.get().getEmail());
    }

    @Test
    @Transactional
    public void logInUserTemplate_InvalidPassword_Failure() {
        Optional<UserTemplate> result = logInService.logInUserTemplate("abc@dal.ca", "wrongpassword");
        assertFalse(result.isPresent());
    }

    @Test
    @Transactional
    public void logInUserTemplate_NonExistentEmail_Failure() {
        Optional<UserTemplate> result = logInService.logInUserTemplate("nonexistent@example.com", "1234");
        assertFalse(result.isPresent());
    }

    @Test
    @Transactional
    public void getUserByEmail_ExistingEmail_Success() {
        Optional<UserTemplate> result = logInService.getUserByEmail("abc@dal.ca");
        assertTrue(result.isPresent());
        assertEquals("abc@dal.ca", result.get().getEmail());
    }

    @Test
    @Transactional
    public void getUserByEmail_NonExistentEmail_Failure() {
        Optional<UserTemplate> result = logInService.getUserByEmail("nonexistent@example.com");
        assertFalse(result.isPresent());
    }
}
