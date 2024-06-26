//package com.group2.Tiger_Talks.backend.authentication;
//
//import com.group2.Tiger_Talks.backend.model.User.DTO.ForgotPasswordDTO;
//import com.group2.Tiger_Talks.backend.model.User.UserTemplate;
//import com.group2.Tiger_Talks.backend.repsitory.PasswordTokenRepository;
//import com.group2.Tiger_Talks.backend.repsitory.User.UserTemplateRepository;
//import com.group2.Tiger_Talks.backend.service.Authentication.PasswordResetService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@Transactional
//public class PasswordResetServiceTest {
//    @Autowired
//    private PasswordResetService passwordResetService;
//
//    @Autowired
//    private UserTemplateRepository userTemplateRepository;
//
//    @Autowired
//    private PasswordTokenRepository passwordTokenRepository;
//
//    @BeforeEach
//    public void setUp() {
//        // Ensure the database is clean before each test
//        userTemplateRepository.deleteAll();
//        passwordTokenRepository.deleteAll();
//
//        // Add a sample user
//        UserTemplate user = new UserTemplate("FName",
//                "LName",
//                18,
//                "u1",
//                "Male",
//                "abc@dal.ca",
//                "1234");
//        userTemplateRepository.save(user);
//    }
//
//    @Test
//    public void testCreateAndSendResetMail_UserExists() {
//
//        Optional<String> result = passwordResetService.createAndSendResetMail("abc@dal.ca");
//        assertFalse(result.isPresent());
//
//        // Check if the token was created and saved
//        assertFalse(passwordTokenRepository.findAll().isEmpty());
//
//        // Verify email was sent (using an actual mail server or mock mail sender)
//    }
//
//    @Test
//    public void testCreateAndSendResetMail_UserDoesNotExist() {
//        Optional<String> result = passwordResetService.createAndSendResetMail("nonexistent@dal.ca");
//        assertTrue(result.isPresent());
//        assertEquals("This email nonexistent@dal.ca is not registered for any user", result.get());
//    }
//
//    @Test
//    public void testValidateToken_ValidToken() {
//        // Create a token and save it
//        passwordResetService.createAndSendResetMail("abc@dal.ca");
//        String token = passwordTokenRepository.findAll().getFirst().getToken();
//
//        Optional<String> result = passwordResetService.validateToken(token);
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    public void testValidateToken_InvalidToken() {
//        Optional<String> result = passwordResetService.validateToken("invalidtoken");
//        assertTrue(result.isPresent());
//        assertEquals("Token is expired / invalid. Try resending the mail", result.get());
//    }
//
//    @Test
//    public void testResetPassword_ValidToken() {
//        ForgotPasswordDTO dto = new ForgotPasswordDTO("abc@dal.ca", "newpassword");
//        Optional<String> result = passwordResetService.resetPassword(dto);
//        assertFalse(result.isPresent());
//
//        UserTemplate user = userTemplateRepository.findUserTemplateByEmail("abc@dal.ca").orElseThrow();
//        assertEquals("newpassword", user.getPassword());
//    }
//
//    @Test
//    public void testResetPassword_InvalidEmail() {
//        ForgotPasswordDTO dto = new ForgotPasswordDTO("nonexistent@dal.ca", "newpassword");
//        Optional<String> result = passwordResetService.resetPassword(dto);
//        assertTrue(result.isPresent());
//        assertEquals("An error has occurred when resting your password. The email used no longer belongs to any account", result.get());
//    }
//}
