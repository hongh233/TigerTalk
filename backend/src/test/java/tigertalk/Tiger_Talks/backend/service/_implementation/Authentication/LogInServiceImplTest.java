package tigertalk.Tiger_Talks.backend.service._implementation.Authentication;

import tigertalk.model.User.UserProfile;
import tigertalk.model.User.UserProfileDTO;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service._implementation.Authentication.LogInServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class LogInServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private LogInServiceImpl logInServiceImpl;
    private UserProfile userA;
    private UserProfile userB;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userA = new UserProfile();
        userA.setFirstName("Along");
        userA.setLastName("Aside");
        userA.setBirthday("1980-01-01");
        userA.setGender("Male");
        userA.setUserName("userA");
        userA.setEmail("a@dal.ca");
        userA.setPassword("aaaa1A@a");
        userA.setSecurityQuestion("1");
        userA.setSecurityQuestionAnswer("What was your favourite book as a child?");

        userB = new UserProfile();
        userB.setFirstName("Beach");
        userB.setLastName("Boring");
        userB.setBirthday("1980-01-01");
        userB.setGender("Male");
        userB.setUserName("userB");
        userB.setEmail("b@dal.ca");
        userB.setPassword("aaaa1A@a");
        userB.setSecurityQuestion("1");
        userB.setSecurityQuestionAnswer("What was your favourite book as a child?");
    }

    /**
     * Test case for logInUser
     */
    @Test
    public void logInUser_normal_resultExist() {
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.of(userA));
        when(userProfileRepository.save(userA)).thenReturn(userA);
        Optional<UserProfileDTO> result = logInServiceImpl.loginUser(userA.getEmail(), userA.getPassword());
        assertTrue(result.isPresent());
    }

    @Test
    public void logInUser_normal_resultCorrect() {
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.of(userA));
        when(userProfileRepository.save(userA)).thenReturn(userA);
        Optional<UserProfileDTO> result = logInServiceImpl.loginUser(userA.getEmail(), userA.getPassword());
        assertTrue(result.isPresent());
        assertEquals(userA.getEmail(), result.get().email());
    }


    @Test
    public void logInUser_wrongPassword() {
        userA.setPassword("aaaa1A@a");
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.of(userA));
        when(userProfileRepository.save(userA)).thenReturn(userA);
        Optional<UserProfileDTO> result = logInServiceImpl.loginUser(userA.getEmail(), "bbbb2B@b");
        assertTrue(result.isEmpty());
    }

    @Test
    public void logInUser_userNotFound() {
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.of(userA));
        when(userProfileRepository.save(userA)).thenReturn(userA);
        Optional<UserProfileDTO> result = logInServiceImpl.loginUser(userB.getEmail(), userB.getPassword());
        assertTrue(result.isEmpty());
    }

    /**
     * Test case for logOut
     */
    @Test
    public void logOut_normal_onlineCheck_online() {
        userA.setOnlineStatus("offline");
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.of(userA));
        when(userProfileRepository.save(userA)).thenReturn(userA);
        logInServiceImpl.logOut(userA.getEmail());
        assertEquals("offline", userA.getOnlineStatus());
    }

    @Test
    public void logOut_normal_onlineCheck_offline() {
        userA.setOnlineStatus("offline");
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.of(userA));
        when(userProfileRepository.save(userA)).thenReturn(userA);
        logInServiceImpl.logOut(userA.getEmail());
        assertEquals("offline", userA.getOnlineStatus());
    }

    @Test
    public void logOut_userNotFound() {
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.empty());
        logInServiceImpl.logOut(userA.getEmail());
        verify(userProfileRepository, never()).save(any(UserProfile.class));
    }
}