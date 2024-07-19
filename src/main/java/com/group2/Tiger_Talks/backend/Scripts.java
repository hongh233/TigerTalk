package com.group2.Tiger_Talks.backend;

import com.group2.Tiger_Talks.backend.controller.Friend.FriendshipRequestController;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.model.Utils.UserLevel;
import com.group2.Tiger_Talks.backend.model.Utils.UserStatus;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import com.group2.Tiger_Talks.backend.service.Authentication.SignUpService;
import com.group2.Tiger_Talks.backend.service.Friend.FriendshipRequestService;
import com.group2.Tiger_Talks.backend.service.Group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.group2.Tiger_Talks.backend.model.Utils.TestGen.MAX_USERS;
import static com.group2.Tiger_Talks.backend.model.Utils.TestGen.MIN_USERS;

@RestController
@RequestMapping("/api/scripts")
public class Scripts {
    private static final int NUM_OF_USERS = 26;
    private static final int NUM_OF_GROUPS = 10;
    @Autowired
    private SignUpService signUpService;
    @Autowired
    private FriendshipRequestService friendshipRequestService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserProfileRepository userProfileRepository;

    @PostMapping("/setUp")
    public String run() {
        bulkSignUp();
        bulkFriends();
        bulkGroup();
        return ((char) Impl.getAdminUser(NUM_OF_USERS)) + "@dal.ca";
    }

    /**
     * Test method for signing up user profiles.
     */
    public void bulkSignUp() {
        userProfileRepository.saveAll(Impl.genUsers(NUM_OF_USERS));
    }

    public void bulkFriends() {
        FriendshipRequestController fReqController = new FriendshipRequestController(friendshipRequestService);

        Impl.createFriends(
                NUM_OF_USERS,
                friendshipRequestService,
                fReqController::acceptFriendRequest,
                fReqController::sendFriendRequest
        );
    }

    public void bulkGroup() {
        List<UserProfile> allUsers = userProfileRepository.findAll();

        int ADMIN_USER = Impl.getAdminUser(NUM_OF_USERS);
        for (int i = 0; i < NUM_OF_GROUPS; i++) {
            var groupName = "Group 31" + i;
            if (i > (NUM_OF_GROUPS / 2)) {
                groupService.createGroup(groupName, "b@dal.ca", true);
            } else {
                groupService.createGroup(groupName, ((char) ADMIN_USER) + "@dal.ca", i % 2 == 0);
            }
        }

    }


    public static class Impl {
        private final static String[] securityQuestions = {
                "What was your favourite book as a child?",
                "In what city were you born?",
                "What is the name of the hospital where you were born?"};
        private final static String[] securityQuestionAnswers = {"1", "1", "1"};

        public static List<UserProfile> genUsers(int numOfUsers) {
            ArrayList<UserProfile> userProfiles = new ArrayList<>();
            for (int i = 0; i < numOfUsers; i++) {
                userProfiles.add(new UserProfile(
                        "user" + alpha(i),
                        "number",
                        12,
                        (i % 3 == 0) ? "Male" : "Female",
                        "user" + alpha(i),
                        (char) (alpha(i) ^ 32) + "@dal.ca", // Flip case
                        "aaaa1A@a",
                        securityQuestionAnswers,
                        securityQuestions
                ));
            }

            customize(userProfiles);

            return userProfiles;
        }

        /**
         * Sets up group members in prod
         * - Tyson (s@dal.ca)
         * - Hongh (a@dal.ca)
         * - Raphael (n@dal.ca)
         * - Shuqiang (z@dal.ca)
         * - Benjamin (b@dal.ca)
         */
        private static void customize(ArrayList<UserProfile> userProfiles) {
            assert userProfiles.size() == 26;

            UserProfile Benjamin = userProfiles.get(1);
            Benjamin.setUserLevel(UserLevel.ADMIN);
            Benjamin.setValidated(true);
            Benjamin.setStatus(UserStatus.ACTIVE);

            UserProfile Shuqiang = userProfiles.get(25);
            Shuqiang.setUserLevel(UserLevel.ADMIN);
            Shuqiang.setValidated(true);
            Shuqiang.setStatus(UserStatus.ACTIVE);

            UserProfile Raphael = userProfiles.get(13);
            Raphael.setUserLevel(UserLevel.ADMIN);
            Raphael.setValidated(true);
            Raphael.setStatus(UserStatus.ACTIVE);

            UserProfile Tyson = userProfiles.get(18);
            Tyson.setUserLevel(UserLevel.ADMIN);
            Tyson.setValidated(true);
            Tyson.setStatus(UserStatus.ACTIVE);

            UserProfile Hongh = userProfiles.get(0);
            Hongh.setUserLevel(UserLevel.ADMIN);
            Hongh.setValidated(true);
            Hongh.setStatus(UserStatus.ACTIVE);
        }

        private static int getAdminUser(int numOfUsers) {
            return alpha((numOfUsers / 2)) ^ 32;
        }

        public static char alpha(int n) {
            return (char) ('A' + n);
        }

        public static void createFriends(int numOfUsers,
                                         FriendshipRequestService friendshipRequestService,
                                         Function<Integer, ResponseEntity<String>> acceptFriendRequest,
                                         BiFunction<String, String, ResponseEntity<String>> sendFriendRequest) {
            if (numOfUsers > MAX_USERS || MIN_USERS > numOfUsers)
                throw new IllegalArgumentException("Cannot generate more than 26 and less than 3 users as the names are A-Z check impl to change this behaviour");

            int MAIN_USER = getAdminUser(numOfUsers);
            Queue<Integer> queue = new LinkedList<>(List.of(MAIN_USER));
            HashSet<Integer> friends = new HashSet<>();

            while (!queue.isEmpty()) {
                int curr = queue.remove();
                if (friends.contains(curr) || curr < (alpha(0) ^ 32) || curr > (alpha(numOfUsers) ^ 32))
                    continue;
                friends.add(curr);

                int LF = curr - 2; // Left Friend
                if (LF >= (alpha(0) ^ 32)) {
                    createFriendshipReturningRequestID(
                            (char) MAIN_USER,
                            queue,
                            (char) curr,
                            (char) LF,
                            sendFriendRequest
                    );
                }

                int RF = curr + 2;  // Right Friend
                if (RF < (alpha(numOfUsers) ^ 32)) {
                    createFriendshipReturningRequestID(
                            (char) MAIN_USER,
                            queue,
                            (char) curr,
                            (char) RF,
                            sendFriendRequest
                    );
                }
            }


            var numOfTotalRequests = friendshipRequestService.findNumOfTotalRequests();
            for (int i = 0; i < numOfTotalRequests; ) {
                acceptFriendRequest.apply(++i);
            }
        }

        private static void createFriendshipReturningRequestID(char MAIN_USER,
                                                               Queue<Integer> queue,
                                                               char curr,
                                                               char friendNum,
                                                               BiFunction<String, String, ResponseEntity<String>> sendFriendRequest) {
            sendFriendRequest.apply(
                    "%c@dal.ca".formatted(curr),
                    "%c@dal.ca".formatted(friendNum)
            );

            if (MAIN_USER != friendNum) {
                sendFriendRequest.apply(
                        "%c@dal.ca".formatted(MAIN_USER),
                        "%c@dal.ca".formatted(friendNum)
                );
            }

            queue.add((int) friendNum);
        }
    }
}
