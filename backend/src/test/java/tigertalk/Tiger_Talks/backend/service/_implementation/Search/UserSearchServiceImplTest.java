package tigertalk.Tiger_Talks.backend.service._implementation.Search;

import tigertalk.model.User.UserProfile;
import tigertalk.model.User.UserProfileDTO;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service._implementation.Search.UserSearchServiceImpl;
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

        userC = new UserProfile();
        userC.setFirstName("Charlie");
        userC.setLastName("Chaplin");
        userC.setBirthday("1980-01-01");
        userC.setGender("Male");
        userC.setUserName("userC");
        userC.setEmail("c@dal.ca");
        userC.setPassword("cccc1C@c");
        userC.setSecurityQuestion("4");
        userC.setSecurityQuestionAnswer("What was the name of your first pet?");
    }

    /**
     * Test case for search
     */
    @Test
    public void search_nullQueryOrEmail() {
        List<UserProfileDTO> result = userSearchService.search(null, "a@dal.ca");
        assertEquals(Collections.emptyList(), result);

        result = userSearchService.search("query", null);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void search_noMatches() {
        when(userProfileRepository.findAll()).thenReturn(Arrays.asList(userA, userB, userC));

        List<UserProfileDTO> result = userSearchService.search("Non-matching query", "a@dal.ca");
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void search_excludeSelf() {
        when(userProfileRepository.findAll()).thenReturn(Arrays.asList(userA, userB, userC));

        List<UserProfileDTO> result = userSearchService.search("userA", "a@dal.ca");
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void search_byName() {
        when(userProfileRepository.findAll()).thenReturn(Arrays.asList(userA, userB, userC));

        List<UserProfileDTO> result = userSearchService.search("Along", "c@dal.ca");
        assertEquals(1, result.size());
        assertEquals("a@dal.ca", result.get(0).email());
    }

    @Test
    public void search_byEmail() {
        when(userProfileRepository.findAll()).thenReturn(Arrays.asList(userA, userB, userC));

        List<UserProfileDTO> result = userSearchService.search("b@dal.ca", "c@dal.ca");
        assertEquals(1, result.size());
        assertEquals("b@dal.ca", result.get(0).email());
    }

    @Test
    public void search_byUsername() {
        when(userProfileRepository.findAll()).thenReturn(Arrays.asList(userA, userB, userC));

        List<UserProfileDTO> result = userSearchService.search("userB", "c@dal.ca");
        assertEquals(1, result.size());
        assertEquals("b@dal.ca", result.get(0).email());
    }

    @Test
    public void search_multipleMatches() {
        when(userProfileRepository.findAll()).thenReturn(Arrays.asList(userA, userB, userC));

        List<UserProfileDTO> result = userSearchService.search("user", "c@dal.ca");
        assertEquals(2, result.size());
    }
}
