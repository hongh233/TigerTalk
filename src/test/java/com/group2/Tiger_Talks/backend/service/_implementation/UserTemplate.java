package com.group2.Tiger_Talks.backend.service._implementation;

import com.group2.Tiger_Talks.backend.model.User.UserProfile;

public class UserTemplate {

    private final static String[] securityQuestions = {
            "What was your favourite book as a child?",
            "In what city were you born?",
            "What is the name of the hospital where you were born?"};
    private final static String[] securityQuestionAnswers = {"1", "2", "3"};

    public static final UserProfile USER_A = new UserProfile(
            "Along",
            "Aside",
            22,
            "Male",
            "userA",
            "a@dal.ca",
            "aaaa1A@a",
            securityQuestionAnswers,
            securityQuestions
    );

    public static final UserProfile USER_B = new UserProfile(
            "Beach",
            "Boring",
            21,
            "Male",
            "userB",
            "b@dal.ca",
            "aaaa1A@a",
            securityQuestionAnswers,
            securityQuestions
    );

    public static final UserProfile USER_C = new UserProfile(
            "Cargo",
            "Charles",
            24,
            "Female",
            "userC",
            "c@dal.ca",
            "aaaa1A@a",
            securityQuestionAnswers,
            securityQuestions
    );

    public static final UserProfile USER_D = new UserProfile(
            "Double",
            "Doc",
            27,
            "Male",
            "userD",
            "d@dal.ca",
            "aaaa1A@a",
            securityQuestionAnswers,
            securityQuestions
    );

    public static final UserProfile USER_E = new UserProfile(
            "Enable",
            "Eric",
            20,
            "Male",
            "userE",
            "e@dal.ca",
            "aaaa1A@a",
            securityQuestionAnswers,
            securityQuestions
    );

    public static final UserProfile USER_F = new UserProfile(
            "Famous",
            "Fork",
            23,
            "Male",
            "userF",
            "f@dal.ca",
            "aaaa1A@a",
            securityQuestionAnswers,
            securityQuestions
    );

}
