package com.group2.Tiger_Talks.backend.model;

import com.group2.Tiger_Talks.backend.model.Utils.UserStatus;

import java.util.ArrayList;
import java.util.List;

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
}
