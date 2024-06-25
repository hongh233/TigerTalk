package com.group2.Tiger_Talks.backend.model;

public final class Utils {
    public static final String CROSS_ORIGIN_HOST_NAME = "http://localhost:3000";

    public static final class B00 {
        public static final int MIN = 0;
        public static final int MAX = 999999;
    }


    public static final class UserLevel {
        public static final String ADMIN = "admin";
        public static final String USER = "user";
    }

    public static final class Role {
        public static final String DEFAULT = "default";
        public static final String STUDENT = "student";
        public static final String INSTRUCTOR = "instructor";
        public static final String EMPLOYEE = "employee";
    }

    public static final class UserStatus {
        public static final String BLOCKED = "blocked";
        public static final String PENDING = "pending";
        public static final String ACTIVE = "active";
    }


}
