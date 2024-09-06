package tigertalk.Tiger_Talks.backend.service._implementation.Friend;

import tigertalk.model.User.UserProfile;
import tigertalk.model.User.UserProfileDTO;
import tigertalk.repository.Friend.FriendshipRepository;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service._implementation.Friend.FriendshipRecommendationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FriendshipRecommendationServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private FriendshipRecommendationServiceImpl friendshipRecommendationService;

    @Mock
    private FriendshipRepository friendshipRepository;

    private UserProfile userA;
    private UserProfile userB;
    private UserProfile userC;
    private UserProfile userD;

    @BeforeEach
    public void setUp() {
        userA = Mockito.mock(UserProfile.class);
        userB = new UserProfile();
        userB.setFirstName("Blong");
        userB.setLastName("Bside");
        userB.setBirthday("1980-01-01");
        userB.setGender("Female");
        userB.setUserName("userB");
        userB.setEmail("b@dal.ca");
        userB.setPassword("bbbb1B@b");
        userB.setSecurityQuestion("1");
        userB.setSecurityQuestionAnswer("What was your favourite book as a child?");

        userC = new UserProfile();
        userC.setFirstName("Clong");
        userC.setLastName("Cside");
        userC.setBirthday("1980-01-01");
        userC.setGender("Male");
        userC.setUserName("userC");
        userC.setEmail("c@dal.ca");
        userC.setPassword("cccc1C@c");
        userC.setSecurityQuestion("1");
        userC.setSecurityQuestionAnswer("What was your favourite book as a child?");

        userD = new UserProfile();
        userD.setFirstName("Dlong");
        userD.setLastName("Dside");
        userD.setBirthday("1980-01-01");
        userD.setGender("Female");
        userD.setUserName("userD");
        userD.setEmail("d@dal.ca");
        userD.setPassword("dddd1D@d");
        userD.setSecurityQuestion("1");
        userD.setSecurityQuestionAnswer("What was your favourite book as a child?");

        lenient().when(userA.getEmail()).thenReturn("a@dal.ca");
        lenient().when(friendshipRepository.findAllFriendsByEmail(userA.getEmail())).thenReturn(List.of(userB));
    }

    /**
     * Test case for recommendFriends
     */
    @Test
    public void recommendFriends_userNotFound() {
        when(userProfileRepository.findById("k@dal.ca")).thenReturn(Optional.empty());
        List<UserProfileDTO> recommendations = friendshipRecommendationService.recommendFriends("k@dal.ca", 3);

        assertEquals(0, recommendations.size());
    }

    @Test
    public void recommendFriends_noPotentialFriends() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findAll()).thenReturn(List.of(userA, userB));

        List<UserProfileDTO> recommendations = friendshipRecommendationService.recommendFriends("a@dal.ca", 3);

        assertEquals(0, recommendations.size());
    }

    @Test
    public void recommendFriends_limitedPotentialFriends() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findAll()).thenReturn(List.of(userA, userB, userC));

        List<UserProfileDTO> recommendations = friendshipRecommendationService.recommendFriends("a@dal.ca", 3);

        assertEquals(1, recommendations.size());
        assertEquals("c@dal.ca", recommendations.get(0).email());
    }

    @Test
    public void recommendFriends_multiplePotentialFriends() {
        when(userProfileRepository.findById("a@dal.ca")).thenReturn(Optional.of(userA));
        when(userProfileRepository.findAll()).thenReturn(List.of(userA, userB, userC, userD));

        List<UserProfileDTO> recommendations = friendshipRecommendationService.recommendFriends("a@dal.ca", 2);

        assertEquals(2, recommendations.size());
        List<String> recommendedEmails = List.of(recommendations.get(0).email(), recommendations.get(1).email());
        assert (recommendedEmails.contains("c@dal.ca"));
        assert (recommendedEmails.contains("d@dal.ca"));
    }
}
