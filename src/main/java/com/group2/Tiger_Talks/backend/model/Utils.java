package com.group2.Tiger_Talks.backend.model;

import java.util.regex.Pattern;

public final class Utils {
    public static final String CROSS_ORIGIN_HOST_NAME = "http://localhost:3000";
    public static final String COMPANY_EMAIL = "test@dal.ca";
    public static final String DEFAULT_PROFILE_PICTURE = "https://res.cloudinary.com/dp4j9a7ry/image/upload/v1719765852/rvfq7rtgnni1ahktelff.jpg";
    public static final String DEFAULT_GROUP_PICTURE = "https://mediaim.expedia.com/destination/7/bb1caab964e8be84036cd5ee7b05e787.jpg?impolicy=fcrop&w=1920&h=480&q=medium";

    public static final class ProfileAccessLevel {
        public static final String PUBLIC = "public";          // Everyone can see your profile
        public static final String PROTECTED = "protected";    // Only friends can see your profile
        public static final String PRIVATE = "private";        // No one can see your profile
    }

    public static final class UserLevel {
        public static final String ADMIN = "admin";
        public static final String USER = "user";
    }

    public static final class Role { // TODO: USe in admin to change role
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

    public static final class OnlineStatus {
        public static final String AVAILABLE = "available"; // Online
        public static final String BUSY = "busy";
        public static final String AWAY = "away";
        public static final String OFFLINE = "offline";     // Offline
    }

    public static final class TestGen {
        public static final int MAX_USERS = 26;     // Offline
        public static final int MIN_USERS = 3;     // Offline
    }

    public static final class RegexCheck {
        public static final Pattern PASSWORD_NORM_LENGTH = Pattern.compile("^.{8,}$");
        public static final Pattern PASSWORD_NORM_UPPERCASE = Pattern.compile("^(?=.*[A-Z]).+$");
        public static final Pattern PASSWORD_NORM_LOWERCASE = Pattern.compile("^(?=.*[a-z]).+$");
        public static final Pattern PASSWORD_NORM_NUMBER = Pattern.compile("^(?=.*[0-9]).+$");
        public static final Pattern PASSWORD_NORM_SPECIAL_CHARACTER = Pattern.compile("^(?=.*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\\\\\]^_`{|}~]).+$");
        public static final Pattern EMAIL_NORM = Pattern.compile("^[A-Za-z0-9]+" + "@dal\\.ca$");
        public static final Pattern NAME_NORM = Pattern.compile("^[A-Za-z]*$");

        public static boolean advancedSearch(String stringToMatch, String inputString) {
            return isSubString(stringToMatch, inputString) || intelij_search_experimental(stringToMatch, inputString);
        }

        public static boolean isSubString(String stringToMatch, String inputString) {
            return Pattern.compile(".*" + Pattern.quote(inputString) + ".*", Pattern.CASE_INSENSITIVE)
                    .matcher(stringToMatch).matches();
        }

        // inputstring => RSIT =>generate_intelij_matcher_regex(x)  => "*[R,r]*[S,s]*[I,i]*[T,t]*"
        public static boolean intelij_search_experimental(String stringToMatch, String inputString) {
            return Pattern.compile(generate_intelij_matcher_regex(inputString), Pattern.CASE_INSENSITIVE)
                    .matcher(stringToMatch)
                    .find();
        }

        private static String generate_intelij_matcher_regex(String string) {
            String regex = String.join(
                    ".*",
                    string.chars()
                            .mapToObj(x -> "[" + Character.toLowerCase((char) x) + Character.toUpperCase((char) x) + "]")
                            .toArray(String[]::new)
            );
            return ".*" + regex + ".*";
        }

    }
}
