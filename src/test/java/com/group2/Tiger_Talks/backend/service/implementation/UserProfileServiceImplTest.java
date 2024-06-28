package com.group2.Tiger_Talks.backend.service.implementation;

import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserProfileServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    private UserProfile userProfile;

    @BeforeEach
    void setUp() {
        userProfile = new UserProfile();
        userProfile.setEmail("test@dal.ca");
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");
    }

    @Test
    void createUserProfileSuccess() {
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        UserProfile createdUserProfile = userProfileService.createUserProfile(userProfile);

        assertNotNull(createdUserProfile, "The saved user profile should not be null.");
        assertEquals("test@dal.ca", createdUserProfile.getEmail(), "The email should match.");
        verify(userProfileRepository, times(1)).save(userProfile);
    }

    @Test
    void getAllUserProfilesSuccess() {
        when(userProfileRepository.findAll()).thenReturn(List.of(userProfile));

        List<UserProfile> userProfiles = userProfileService.getAllUserProfiles();

        assertFalse(userProfiles.isEmpty(), "The list of user profiles should not be empty.");
        assertEquals(1, userProfiles.size(), "The size of the list should be 1.");
        verify(userProfileRepository, times(1)).findAll();
    }

    @Test
    void getUserProfileByEmailSuccess() {
        when(userProfileRepository.findUserProfileByEmail(anyString())).thenReturn(Optional.of(userProfile));

        Optional<UserProfile> foundUserProfile = userProfileService.getUserProfileByEmail("test@dal.ca");

        assertTrue(foundUserProfile.isPresent(), "The user profile should be found.");
        assertEquals("test@dal.ca", foundUserProfile.get().getEmail(), "The email should match.");
        verify(userProfileRepository, times(1)).findUserProfileByEmail("test@dal.ca");
    }

    @Test
    void getUserProfileByEmailNotFound() {
        when(userProfileRepository.findUserProfileByEmail(anyString())).thenReturn(Optional.empty());

        Optional<UserProfile> foundUserProfile = userProfileService.getUserProfileByEmail("notfound@dal.ca");

        assertFalse(foundUserProfile.isPresent(), "The user profile should not be found.");
        verify(userProfileRepository, times(1)).findUserProfileByEmail("notfound@dal.ca");
    }

    @Test
    void deleteUserProfileByEmailSuccess() {
        when(userProfileRepository.findById(anyString())).thenReturn(Optional.of(userProfile));
        doNothing().when(userProfileRepository).deleteById(anyString());

        userProfileService.deleteUserProfileByEmail("test@dal.ca");

        verify(userProfileRepository, times(1)).findById("test@dal.ca");
        verify(userProfileRepository, times(1)).deleteById("test@dal.ca");
    }

    @Test
    void deleteUserProfileByEmailNotFound() {
        when(userProfileRepository.findById(anyString())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userProfileService.deleteUserProfileByEmail("notfound@dal.ca");
        });

        assertEquals("User profile with email notfound@dal.ca not found.", exception.getMessage(),
                "The exception message should match.");
        verify(userProfileRepository, times(1)).findById("notfound@dal.ca");
        verify(userProfileRepository, times(0)).deleteById("notfound@dal.ca");
    }

    @Test
    void updateUserProfileSuccess() {
        Mockito.mockStatic(UserProfile.class);
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        Optional<String> result = userProfileService.updateUserProfile(userProfile);

        assertTrue(result.isEmpty(), "The result should be empty, indicating a successful update.");
        verify(userProfileRepository, times(1)).save(userProfile);
    }

    @Test
    void updateUserProfileVerificationFailed() {
        when(UserProfile.verifyBasics(any(UserProfile.class), any(UserProfileRepository.class), eq(false)))
                .thenReturn(Optional.of("Verification failed"));

        Optional<String> result = userProfileService.updateUserProfile(userProfile);

        assertTrue(result.isPresent(), "The result should be present, indicating a verification failure.");
        assertEquals("Verification failed", result.get(), "The error message should match.");
        verify(userProfileRepository, times(0)).save(userProfile);
    }
}

