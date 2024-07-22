package com.group2.Tiger_Talks.backend.service._implementation.Search;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.model.User.UserProfileDTO;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserSearchServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserSearchServiceImpl userSearchService;

    private UserProfile userA;
    private UserProfile userB;
    private UserProfile userC;

    @BeforeEach
    public void setUp() {
        userA = new UserProfile(
                "Along", "Aside", 22, "Male", "userA", "a@dal.ca", "aaaa1A@a",
                new String[]{"1", "2", "3"},
                new String[]{
                        "What was your favourite book as a child?",
                        "In what city were you born?",
                        "What is the name of the hospital where you were born?"
                }
        );

        userB = new UserProfile(
                "Beach", "Boring", 21, "Male", "userB", "b@dal.ca", "aaaa1A@a",
                new String[]{"1", "2", "3"},
                new String[]{
                        "What was your favourite book as a child?",
                        "In what city were you born?",
                        "What is the name of the hospital where you were born?"
                }
        );

        userC = new UserProfile(
                "Charlie", "Chaplin", 30, "Male", "userC", "c@dal.ca", "cccc1C@c",
                new String[]{"4", "5", "6"},
                new String[]{
                        "What was the name of your first pet?",
                        "What was the make and model of your first car?",
                        "What elementary school did you attend?"
                }
        );
    }

    @Test
    public void testSearch_NullQueryOrEmail() {
        List<UserProfileDTO> result = userSearchService.search(null, "a@dal.ca");
        assertEquals(Collections.emptyList(), result);

        result = userSearchService.search("query", null);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSearch_NoMatches() {
        when(userProfileRepository.findAll()).thenReturn(Arrays.asList(userA, userB, userC));

        List<UserProfileDTO> result = userSearchService.search("Non-matching query", "a@dal.ca");
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSearch_ExcludeSelf() {
        when(userProfileRepository.findAll()).thenReturn(Arrays.asList(userA, userB, userC));

        List<UserProfileDTO> result = userSearchService.search("userA", "a@dal.ca");
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSearch_ByName() {
        when(userProfileRepository.findAll()).thenReturn(Arrays.asList(userA, userB, userC));

        List<UserProfileDTO> result = userSearchService.search("Along", "c@dal.ca");
        assertEquals(1, result.size());
        assertEquals("a@dal.ca", result.get(0).email());
    }

    @Test
    public void testSearch_ByEmail() {
        when(userProfileRepository.findAll()).thenReturn(Arrays.asList(userA, userB, userC));

        List<UserProfileDTO> result = userSearchService.search("b@dal.ca", "c@dal.ca");
        assertEquals(1, result.size());
        assertEquals("b@dal.ca", result.get(0).email());
    }

    @Test
    public void testSearch_ByUsername() {
        when(userProfileRepository.findAll()).thenReturn(Arrays.asList(userA, userB, userC));

        List<UserProfileDTO> result = userSearchService.search("userB", "c@dal.ca");
        assertEquals(1, result.size());
        assertEquals("b@dal.ca", result.get(0).email());
    }

    @Test
    public void testSearch_MultipleMatches() {
        when(userProfileRepository.findAll()).thenReturn(Arrays.asList(userA, userB, userC));

        List<UserProfileDTO> result = userSearchService.search("user", "c@dal.ca");
        assertEquals(2, result.size());
    }
}
