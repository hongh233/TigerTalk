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

import java.util.Optional;

import static com.group2.Tiger_Talks.backend.model.Utils.OnlineStatus.AVAILABLE;
import static com.group2.Tiger_Talks.backend.model.Utils.OnlineStatus.OFFLINE;
import static com.group2.Tiger_Talks.backend.service._implementation.UserTemplate.USER_A;
import static com.group2.Tiger_Talks.backend.service._implementation.UserTemplate.USER_B;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class LogInServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private LogInServiceImpl logInServiceImpl;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     *  Test case for logInUser
     */
    @Test
    public void logInUser_normal_resultExist() {
        when(userProfileRepository.findById(USER_A.getEmail())).thenReturn(Optional.of(USER_A));
        when(userProfileRepository.save(USER_A)).thenReturn(USER_A);
        Optional<UserProfileDTO> result = logInServiceImpl.logInUser(USER_A.getEmail(), USER_A.getPassword());
        Assert.assertTrue(result.isPresent());
    }

    @Test
    public void logInUser_normal_resultCorrect() {
        when(userProfileRepository.findById(USER_A.getEmail())).thenReturn(Optional.of(USER_A));
        when(userProfileRepository.save(USER_A)).thenReturn(USER_A);
        Optional<UserProfileDTO> result = logInServiceImpl.logInUser(USER_A.getEmail(), USER_A.getPassword());
        assertTrue(result.isPresent());
        Assert.assertEquals(USER_A.getEmail(), result.get().email());
    }

    @Test
    public void logInUser_normal_onlineCheck() {
        USER_A.setOnlineStatus(OFFLINE);
        when(userProfileRepository.findById(USER_A.getEmail())).thenReturn(Optional.of(USER_A));
        when(userProfileRepository.save(USER_A)).thenReturn(USER_A);
        Optional<UserProfileDTO> result = logInServiceImpl.logInUser(USER_A.getEmail(), USER_A.getPassword());
        Assert.assertEquals(AVAILABLE, USER_A.getOnlineStatus());
    }

    @Test
    public void logInUser_wrongPassword() {
        USER_A.setPassword("aaaa1A@a");
        when(userProfileRepository.findById(USER_A.getEmail())).thenReturn(Optional.of(USER_A));
        when(userProfileRepository.save(USER_A)).thenReturn(USER_A);
        Optional<UserProfileDTO> result = logInServiceImpl.logInUser(USER_A.getEmail(), "bbbb2B@b");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void logInUser_userNotFound() {
        when(userProfileRepository.findById(USER_A.getEmail())).thenReturn(Optional.of(USER_A));
        when(userProfileRepository.save(USER_A)).thenReturn(USER_A);
        Optional<UserProfileDTO> result = logInServiceImpl.logInUser(USER_B.getEmail(), USER_B.getPassword());
        Assert.assertTrue(result.isEmpty());
    }

    /**
     *  Test case for logOut
     */
    @Test
    public void logOut_normal_onlineCheck_online() {
        USER_A.setOnlineStatus(AVAILABLE);
        when(userProfileRepository.findById(USER_A.getEmail())).thenReturn(Optional.of(USER_A));
        when(userProfileRepository.save(USER_A)).thenReturn(USER_A);
        logInServiceImpl.logOut(USER_A.getEmail());
        Assert.assertEquals(OFFLINE, USER_A.getOnlineStatus());
    }

    @Test
    public void logOut_normal_onlineCheck_offline() {
        USER_A.setOnlineStatus(OFFLINE);
        when(userProfileRepository.findById(USER_A.getEmail())).thenReturn(Optional.of(USER_A));
        when(userProfileRepository.save(USER_A)).thenReturn(USER_A);
        logInServiceImpl.logOut(USER_A.getEmail());
        Assert.assertEquals(OFFLINE, USER_A.getOnlineStatus());
    }

    @Test
    public void logOut_userNotFound() {
        when(userProfileRepository.findById(USER_A.getEmail())).thenReturn(Optional.empty());
        logInServiceImpl.logOut(USER_A.getEmail());
        verify(userProfileRepository, never()).save(any(UserProfile.class));
    }
}