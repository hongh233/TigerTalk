package com.group2.Tiger_Talks.backend.service._implementation.Authentication;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.model.User.UserProfileDTO;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.group2.Tiger_Talks.backend.model.Utils.OnlineStatus.AVAILABLE;
import static com.group2.Tiger_Talks.backend.model.Utils.OnlineStatus.OFFLINE;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = LogInServiceImplTest.class)
public class LogInServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private LogInServiceImpl logInServiceImpl;
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
     *  Test case for logInUser
     */
    @Test
    public void logInUser_normal_resultExist() {
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.of(userA));
        when(userProfileRepository.save(userA)).thenReturn(userA);
        Optional<UserProfileDTO> result = logInServiceImpl.logInUser(userA.getEmail(), userA.getPassword());
        Assert.assertTrue(result.isPresent());
    }

    @Test
    public void logInUser_normal_resultCorrect() {
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.of(userA));
        when(userProfileRepository.save(userA)).thenReturn(userA);
        Optional<UserProfileDTO> result = logInServiceImpl.logInUser(userA.getEmail(), userA.getPassword());
        assertTrue(result.isPresent());
        Assert.assertEquals(userA.getEmail(), result.get().email());
    }

    @Test
    public void logInUser_normal_onlineCheck() {
        userA.setOnlineStatus(OFFLINE);
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.of(userA));
        when(userProfileRepository.save(userA)).thenReturn(userA);
        Optional<UserProfileDTO> result = logInServiceImpl.logInUser(userA.getEmail(), userA.getPassword());
        Assert.assertEquals(AVAILABLE, userA.getOnlineStatus());
    }

    @Test
    public void logInUser_wrongPassword() {
        userA.setPassword("aaaa1A@a");
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.of(userA));
        when(userProfileRepository.save(userA)).thenReturn(userA);
        Optional<UserProfileDTO> result = logInServiceImpl.logInUser(userA.getEmail(), "bbbb2B@b");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void logInUser_userNotFound() {
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.of(userA));
        when(userProfileRepository.save(userA)).thenReturn(userA);
        Optional<UserProfileDTO> result = logInServiceImpl.logInUser(userB.getEmail(), userB.getPassword());
        Assert.assertTrue(result.isEmpty());
    }

    /**
     *  Test case for logOut
     */
    @Test
    public void logOut_normal_onlineCheck_online() {
        userA.setOnlineStatus(AVAILABLE);
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.of(userA));
        when(userProfileRepository.save(userA)).thenReturn(userA);
        logInServiceImpl.logOut(userA.getEmail());
        Assert.assertEquals(OFFLINE, userA.getOnlineStatus());
    }

    @Test
    public void logOut_normal_onlineCheck_offline() {
        userA.setOnlineStatus(OFFLINE);
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.of(userA));
        when(userProfileRepository.save(userA)).thenReturn(userA);
        logInServiceImpl.logOut(userA.getEmail());
        Assert.assertEquals(OFFLINE, userA.getOnlineStatus());
    }

    @Test
    public void logOut_userNotFound() {
        when(userProfileRepository.findById(userA.getEmail())).thenReturn(Optional.empty());
        logInServiceImpl.logOut(userA.getEmail());
        verify(userProfileRepository, never()).save(any(UserProfile.class));
    }
}