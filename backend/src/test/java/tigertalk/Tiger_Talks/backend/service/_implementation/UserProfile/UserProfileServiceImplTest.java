package tigertalk.Tiger_Talks.backend.service._implementation.UserProfile;

import tigertalk.model.User.UserProfile;
import tigertalk.model.User.UserProfileDTO;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service._implementation.UserProfile.UserProfileServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserProfileServiceImplTest {

    private static MockedStatic<UserProfile> mockedStaticUserProfile;
    @Mock
    private UserProfileRepository userProfileRepository;
    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @BeforeAll
    public static void setUp() {
        mockedStaticUserProfile = mockStatic(UserProfile.class);
    }

    @AfterAll
    public static void tearDown() {
        mockedStaticUserProfile.close();
    }

    /**
     * Test case for getAllUserProfiles
     */
    @Test
    public void getAllUserProfiles_success() {
        UserProfile userProfile = new UserProfile();
        when(userProfileRepository.findAll()).thenReturn(List.of(userProfile));
        List<UserProfileDTO> result = userProfileService.getAllUserProfiles();
        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.size(), "There should be one user profile");
        verify(userProfileRepository, times(1)).findAll();
    }

    @Test
    public void getAllUserProfiles_EmptyList() {
        when(userProfileRepository.findAll()).thenReturn(Collections.emptyList());
        List<UserProfileDTO> result = userProfileService.getAllUserProfiles();
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result list should be empty");
        verify(userProfileRepository, times(1)).findAll();
    }

    @Test
    public void getAllUserProfiles_Exception() {
        when(userProfileRepository.findAll()).thenThrow(new RuntimeException("Database error"));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userProfileService.getAllUserProfiles();
        });
        assertEquals("Database error", exception.getMessage(), "Exception message should match");
        verify(userProfileRepository, times(1)).findAll();
    }

    /**
     * Test case for getUserProfileByEmail
     */
    @Test
    public void getUserProfileByEmail_Success() {
        UserProfile userProfile = new UserProfile();
        when(userProfileRepository.findUserProfileByEmail("test@dal.ca")).thenReturn(Optional.of(userProfile));
        Optional<UserProfileDTO> result = userProfileService.getUserProfileByEmail("test@dal.ca");
        assertTrue(result.isPresent(), "Result should be present");
        assertNotNull(result.get(), "UserProfileDTO should not be null");
        verify(userProfileRepository, times(1)).findUserProfileByEmail("test@dal.ca");
    }

    @Test
    public void getUserProfileByEmail_NotFound() {
        when(userProfileRepository.findUserProfileByEmail("test@dal.ca")).thenReturn(Optional.empty());
        Optional<UserProfileDTO> result = userProfileService.getUserProfileByEmail("test@dal.ca");
        assertFalse(result.isPresent(), "Result should not be present");
        verify(userProfileRepository, times(1)).findUserProfileByEmail("test@dal.ca");
    }

    @Test
    public void getUserProfileByEmail_Exception() {
        when(userProfileRepository.findUserProfileByEmail("test@dal.ca")).thenThrow(new RuntimeException("Database error"));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userProfileService.getUserProfileByEmail("test@dal.ca");
        });
        assertEquals("Database error", exception.getMessage(), "Exception message should match");
        verify(userProfileRepository, times(1)).findUserProfileByEmail("test@dal.ca");
    }

    /**
     * Test case for deleteUserProfileByEmail
     */
    @Test
    public void deleteUserProfileByEmail_success() {
        UserProfile userProfile = new UserProfile();
        when(userProfileRepository.findById("test@dal.ca")).thenReturn(Optional.of(userProfile));
        userProfileService.deleteUserProfileByEmail("test@dal.ca");
        verify(userProfileRepository, times(1)).findById("test@dal.ca");
        verify(userProfileRepository, times(1)).deleteById("test@dal.ca");
    }

    @Test
    public void deleteUserProfileByEmail_notFound() {
        when(userProfileRepository.findById("test@dal.ca")).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userProfileService.deleteUserProfileByEmail("test@dal.ca");
        });
        assertEquals("User profile with email test@dal.ca not found.", exception.getMessage(), "Exception message should match");
        verify(userProfileRepository, times(1)).findById("test@dal.ca");
        verify(userProfileRepository, times(0)).deleteById("test@dal.ca");
    }

    @Test
    public void deleteUserProfileByEmail_exception() {
        when(userProfileRepository.findById("test@dal.ca")).thenThrow(new RuntimeException("Database error"));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userProfileService.deleteUserProfileByEmail("test@dal.ca");
        });
        assertEquals("Database error", exception.getMessage(), "Exception message should match");
        verify(userProfileRepository, times(1)).findById("test@dal.ca");
        verify(userProfileRepository, times(0)).deleteById("test@dal.ca");
    }

    /**
     * Test case for updateUserProfile
     */
    @Test
    public void updateUserProfile_successfulUpdate() {
        UserProfile userA = new UserProfile("Along", "Aside", 22, "Male", "userA", "a@dal.ca", "aaaa1A@a", new String[]{"1", "2", "3"}, new String[]{"What was your favourite book as a child?", "In what city were you born?", "What is the name of the hospital where you were born?"});
        when(userProfileRepository.save(userA)).thenReturn(userA);

        when(UserProfile.verifyBasics(userA, userProfileRepository, false)).thenReturn(Optional.empty());
        Optional<String> result = userProfileService.updateUserProfile(userA);
        assertTrue(result.isEmpty(), "The updateUserProfile method should return an empty optional if successful");
        verify(userProfileRepository, times(1)).save(userA);
    }

    @Test
    public void updateUserProfile_invalidEmail() {
        UserProfile invalidEmailUserProfile = new UserProfile("Along", "Aside", 22, "Male", "userA", "a@dal.c", "aaaa1A@a", new String[]{"1", "2", "3"}, new String[]{"What was your favourite book as a child?", "In what city were you born?", "What is the name of the hospital where you were born?"});
        when(UserProfile.verifyBasics(invalidEmailUserProfile, userProfileRepository, false)).thenReturn(Optional.of("Invalid email address. Please use dal email address!"));

        Optional<String> result = userProfileService.updateUserProfile(invalidEmailUserProfile);
        assertTrue(result.isPresent(), "An error message should be present");
        assertEquals("Invalid email address. Please use dal email address!", result.get(),
                "The error message should be the same");
        verify(userProfileRepository, times(0)).save(invalidEmailUserProfile);
    }

    @Test
    public void updateUserProfile_invalidAge() {
        UserProfile invalidAgeUserProfile = new UserProfile("Along", "Aside", -1, "Male", "userA", "a@dal.ca", "aaaa1A@a", new String[]{"1", "2", "3"}, new String[]{"What was your favourite book as a child?", "In what city were you born?", "What is the name of the hospital where you were born?"});
        when(UserProfile.verifyBasics(invalidAgeUserProfile, userProfileRepository, false)).thenReturn(Optional.of("Age must be greater than 0"));

        Optional<String> result = userProfileService.updateUserProfile(invalidAgeUserProfile);
        assertTrue(result.isPresent(), "An error message should be present");
        assertEquals("Age must be greater than 0", result.get(), "The error message should be the same");
        verify(userProfileRepository, times(0)).save(invalidAgeUserProfile);
    }

}

