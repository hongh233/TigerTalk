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
import org.springframework.jdbc.core.JdbcTemplate;
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
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/setUp")
    public void run() {
        bulkSignUp();
        bulkFriends();
        bulkGroup();
    }

    @PostMapping("/dropTables")
    public ResponseEntity<String> dropTables() {
        String[] dropTablesSql = {
                "SET foreign_key_checks = 0;",
                "DROP TABLE IF EXISTS password_token_impl;",
                "DROP TABLE IF EXISTS friendship_message;",
                "DROP TABLE IF EXISTS friendship;",
                "DROP TABLE IF EXISTS friendship_request;",
                "DROP TABLE IF EXISTS notification;",
                "DROP TABLE IF EXISTS group_post_comment;",
                "DROP TABLE IF EXISTS group_post;",
                "DROP TABLE IF EXISTS user_group;",
                "DROP TABLE IF EXISTS group_membership;",
                "DROP TABLE IF EXISTS post_like;",
                "DROP TABLE IF EXISTS post_comment;",
                "DROP TABLE IF EXISTS post;",
                "DROP TABLE IF EXISTS user_profile;",
                "SET foreign_key_checks = 1;"
        };
        try {
            for (String sql : dropTablesSql) {
                jdbcTemplate.execute(sql);
            }
            return ResponseEntity.ok("Tables dropped successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error dropping tables");
        }
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
        final String[] interests = {"music", "games", "study", "memes"};
        int ADMIN_USER = Impl.getAdminUser(NUM_OF_USERS);
        for (int i = 0; i < NUM_OF_GROUPS; i++) {
            var groupName = "Group 31" + i;
            var interest = interests[i % interests.length];
            if (i > (NUM_OF_GROUPS / 2)) {
                groupService.createGroup(groupName + interest, "b@dal.ca", true, interest);
            } else {
                groupService.createGroup(groupName + interest, ((char) ADMIN_USER) + "@dal.ca", i % 2 == 0, interest);
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

            initMembers(userProfiles);

            return userProfiles;
        }

        /**
         * Sets up group members
         * <p>
         * - Tyson (s@dal.ca)
         * <p>
         * - Hongh (a@dal.ca)
         * <p>
         * - Raphael (n@dal.ca)
         * <p>
         * - Shuqiang (z@dal.ca)
         * <p>
         * - Benjamin (b@dal.ca)
         */
        private static void initMembers(ArrayList<UserProfile> userProfiles) {
            assert userProfiles.size() == 26;

            UserProfile Benjamin = userProfiles.get(1);
            Benjamin.setUserLevel(UserLevel.ADMIN);
            Benjamin.setValidated(true);
            Benjamin.setStatus(UserStatus.ACTIVE);
            Benjamin.setGender("Male");
            Benjamin.setUserName("Benjamin");

            UserProfile Shuqiang = userProfiles.get(25);
            Shuqiang.setProfilePictureUrl("https://res.cloudinary.com/dp4j9a7ry/image/upload/v1721170651/lcwcalggdsyvifoo0grn.jpg");
            Shuqiang.setUserLevel(UserLevel.ADMIN);
            Shuqiang.setValidated(true);
            Shuqiang.setStatus(UserStatus.ACTIVE);
            Shuqiang.setGender("Male");
            Shuqiang.setUserName("Shuqiang");

            UserProfile Raphael = userProfiles.get(13);
            Raphael.setProfilePictureUrl("https://res.cloudinary.com/dp4j9a7ry/image/upload/v1721167087/pctpb2ean1jv6ojuz7mm.jpg");
            Raphael.setUserLevel(UserLevel.ADMIN);
            Raphael.setValidated(true);
            Raphael.setStatus(UserStatus.ACTIVE);
            Raphael.setGender("Male");
            Raphael.setUserName("Homelander");
            Raphael.setFirstName("Not");
            Raphael.setLastName("UrBusiness");
            Raphael.setBiography("Spreading Freedom to the world. Especially those with Oil");
            Raphael.setAge(25);
            Raphael.setGender("USA");

            UserProfile Tyson = userProfiles.get(18);
            Tyson.setProfilePictureUrl("https://res.cloudinary.com/dp4j9a7ry/image/upload/v1721167221/xndgh9diat4bqcrcmrq7.jpg");
            Tyson.setUserLevel(UserLevel.ADMIN);
            Tyson.setValidated(true);
            Tyson.setStatus(UserStatus.ACTIVE);
            Tyson.setGender("Male");
            Tyson.setUserName("Tyson");

            UserProfile Hongh = userProfiles.get(0);
            Hongh.setProfilePictureUrl("https://res.cloudinary.com/dp4j9a7ry/image/upload/v1721418379/unaog1gkx1tlauh1bckw.jpg");
            Hongh.setUserLevel(UserLevel.ADMIN);
            Hongh.setValidated(true);
            Hongh.setStatus(UserStatus.ACTIVE);
            Hongh.setGender("Male");
            Hongh.setUserName("Hongh");
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
