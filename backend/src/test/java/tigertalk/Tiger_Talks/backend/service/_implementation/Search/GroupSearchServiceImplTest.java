package tigertalk.Tiger_Talks.backend.service._implementation.Search;

import tigertalk.model.Group.Group;
import tigertalk.model.Group.GroupDTO;
import tigertalk.model.Group.GroupMembership;
import tigertalk.model.User.UserProfile;
import tigertalk.repository.Group.GroupRepository;
import tigertalk.service._implementation.Search.GroupSearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GroupSearchServiceImplTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupSearchServiceImpl groupSearchService;

    private Group group1;
    private Group group2;
    private Group group3;
    private UserProfile userA;
    private UserProfile userB;

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

        GroupMembership membershipA = new GroupMembership();
        membershipA.setUserProfile(userA);

        GroupMembership membershipB = new GroupMembership();
        membershipB.setUserProfile(userB);

        group1 = new Group("Public Group 1", false, "music");
        group1.setGroupId(1);
        group1.setGroupMemberList(Collections.singletonList(membershipA));

        group2 = new Group("Private Group 2", true, "games");
        group2.setGroupId(2);
        group2.setGroupMemberList(new ArrayList<>());

        group3 = new Group("Public Group 3", false, "study");
        group3.setGroupId(3);
        group3.setGroupMemberList(new ArrayList<>());
    }

    /**
     * Test case for search
     */

}
