package com.group2.Tiger_Talks.backend.model;

import java.util.regex.Pattern;

public final class Utils {
    public static final String CROSS_ORIGIN_HOST_NAME = "http://localhost:3000";
    public static final String COMPANY_EMAIL = "test@dal.ca";

    public static final class ProfileAccessLevel {
        public static final String PUBLIC = "public";          // Everyone can see your profile
        public static final String PROTECTED = "protected";    // Only friends can see your profile
        public static final String PRIVATE = "private";        // No one can see your profile
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

    public static final class RegexCheck {
        public static final Pattern PASSWORD_NORM_LENGTH = Pattern.compile("^.{8,}$");
        public static final Pattern PASSWORD_NORM_UPPERCASE = Pattern.compile("^(?=.*[A-Z]).+$");
        public static final Pattern PASSWORD_NORM_LOWERCASE = Pattern.compile("^(?=.*[a-z]).+$");
        public static final Pattern PASSWORD_NORM_NUMBER = Pattern.compile("^(?=.*[0-9]).+$");
        public static final Pattern PASSWORD_NORM_SPECIAL_CHARACTER = Pattern.compile("^(?=.*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\\\\\]^_`{|}~]).+$");
        public static final Pattern EMAIL_NORM = Pattern.compile("^[A-Za-z0-9]+" + "@dal\\.ca$");
        public static final Pattern NAME_NORM = Pattern.compile("^[A-Za-z]*$");
    }
}
