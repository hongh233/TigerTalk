package com.group2.Tiger_Talks.backend.model;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.model.Utils.UserStatus;
import com.group2.Tiger_Talks.backend.service.Friend.FriendshipRequestService;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.group2.Tiger_Talks.backend.model.Utils.TestGen.MAX_USERS;
import static com.group2.Tiger_Talks.backend.model.Utils.TestGen.MIN_USERS;

public final class Test {

    private final static String[] securityQuestions = {
            "What was your favourite book as a child?",
            "In what city were you born?",
            "What is the name of the hospital where you were born?"};
    private final static String[] securityQuestionAnswers = {"1", "1", "1"};

    public static List<UserProfile> genUsers(int numOfUsers) {
        ArrayList<UserProfile> userProfiles = new ArrayList<>();
        int ADMIN_USER = Test.alpha((numOfUsers / 2)) ^ 32;
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
            if (ADMIN_USER == (alpha(i) ^ 32)) {
                UserProfile admin = userProfiles.get(userProfiles.size() - 1);
                admin.setUserLevel(Utils.UserLevel.ADMIN);
                admin.setValidated(true);
                admin.setStatus(UserStatus.ACTIVE);
            }
        }
        return userProfiles;
    }

    public static char alpha(int n) {
        return (char) ('A' + n);
    }

    public static String createFriends(int numOfUsers,
                                       FriendshipRequestService friendshipRequestService,
                                       Function<Integer, ResponseEntity<String>> acceptFriendRequest,
                                       BiFunction<String, String, ResponseEntity<String>> sendFriendRequest) {
        if (numOfUsers > MAX_USERS || MIN_USERS > numOfUsers)
            throw new IllegalArgumentException("Cannot generate more than 26 and less than 3 users as the names are A-Z check impl to change this behaviour");

        int MAIN_USER = Test.alpha((numOfUsers / 2)) ^ 32;
        Queue<Integer> queue = new LinkedList<>(List.of(MAIN_USER));
        HashSet<Integer> friends = new HashSet<>();

        final String email = "%c@dal.ca";

        while (!queue.isEmpty()) {
            int curr = queue.remove();
            if (friends.contains(curr) || curr < (Test.alpha(0) ^ 32) || curr > (Test.alpha(numOfUsers) ^ 32))
                continue;
            friends.add(curr);

            int LF = curr - 2; // Left Friend
            if (LF >= (Test.alpha(0) ^ 32)) {
                createFriendshipReturningRequestID(
                        (char) MAIN_USER,
                        queue,
                        (char) curr,
                        (char) LF,
                        sendFriendRequest
                );
            }

            int RF = curr + 2;  // Right Friend
            if (RF < (Test.alpha(numOfUsers) ^ 32)) {
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

        System.out.println("DONE");

        return email.formatted(MAIN_USER);
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
