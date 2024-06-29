package com.group2.Tiger_Talks.backend.model;

public final class Test {

    public Test() {
    }

    private final String[] securityQuestions = {
            "What was your favourite book as a child?",
            "In what city were you born?",
            "What is the name of the hospital where you were born?"};
    private final String[] securityQuestionAnswers = {"1", "1", "1"};

    private UserProfile user1 = new UserProfile(
            "Hong", "Huang", 12, "Male", "foo",
            "1@dal.ca", "aaaa1A@a", securityQuestionAnswers, securityQuestions
    );
    private UserProfile user2 = new UserProfile(
            "Hong", "Huang", 12, "Male", "bar",
            "2@dal.ca", "aaaa1A@a", securityQuestionAnswers, securityQuestions
    );
    private UserProfile user3 = new UserProfile(
            "Hong", "Huang", 12, "Male", "cha",
            "3@dal.ca", "aaaa1A@a", securityQuestionAnswers, securityQuestions
    );
    public UserProfile user4 = new UserProfile(
            "Hong", "Huang", 12, "Male", "hey",
            "4@dal.ca", "aaaa1A@a", securityQuestionAnswers, securityQuestions
    );
    public UserProfile user5 = new UserProfile(
            "Hong", "Huang", 12, "Male", "hot",
            "5@dal.ca", "aaaa1A@a", securityQuestionAnswers, securityQuestions
    );


    public UserProfile getUser1() {
        return user1;
    }

    public void setUser1(UserProfile user1) {
        this.user1 = user1;
    }

    public UserProfile getUser2() {
        return user2;
    }

    public void setUser2(UserProfile user2) {
        this.user2 = user2;
    }

    public UserProfile getUser3() {
        return user3;
    }

    public void setUser3(UserProfile user3) {
        this.user3 = user3;
    }

    public UserProfile getUser4() {
        return user4;
    }

    public void setUser4(UserProfile user4) {
        this.user4 = user4;
    }

    public UserProfile getUser5() {
        return user5;
    }

    public void setUser5(UserProfile user5) {
        this.user5 = user5;
    }
}
