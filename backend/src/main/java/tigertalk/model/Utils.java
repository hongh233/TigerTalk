package tigertalk.model;

import java.util.regex.Pattern;

public final class Utils {
    public static final String CROSS_ORIGIN_HOST_NAME = "http://localhost:3000";
    public static final String COMPANY_EMAIL = "test@dal.ca";
    public static final String DEFAULT_PROFILE_PICTURE = "https://res.cloudinary.com/dp4j9a7ry/image/upload/v1719765852/rvfq7rtgnni1ahktelff.jpg";
    public static final String DEFAULT_GROUP_PICTURE = "https://mediaim.expedia.com/destination/7/bb1caab964e8be84036cd5ee7b05e787.jpg?impolicy=fcrop&w=1920&h=480&q=medium";
    public static final byte PASSWORD_TOKEN_EXPIRATION_MINUTES = 10;

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

    public static final class OnlineStatus {
        public static final String AVAILABLE = "available"; // Online
        public static final String BUSY = "busy";
        public static final String AWAY = "away";
        public static final String OFFLINE = "offline";     // Offline
    }

    public static final class TestGen {
        public static final int MAX_USERS = 26;
        public static final int MIN_USERS = 3;
    }

    public static final class RegexCheck {
        public static final Pattern PASSWORD_NORM_LENGTH = Pattern.compile("^.{8,}$");
        public static final Pattern PASSWORD_NORM_UPPERCASE = Pattern.compile("^(?=.*[A-Z]).+$");
        public static final Pattern PASSWORD_NORM_LOWERCASE = Pattern.compile("^(?=.*[a-z]).+$");
        public static final Pattern PASSWORD_NORM_NUMBER = Pattern.compile("^(?=.*[0-9]).+$");
        public static final Pattern PASSWORD_NORM_SPECIAL_CHARACTER = Pattern.compile("^(?=.*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\\\\\]^_`{|}~]).+$");
        public static final Pattern EMAIL_NORM = Pattern.compile("^[A-Za-z0-9]+" + "@dal\\.ca$");
        public static final Pattern NAME_NORM = Pattern.compile("^[A-Za-z]*$");

        /**
         * Performs an advanced search on the given strings.
         * This method checks if the input string is a substring of the string to match
         * or if it matches an IntelliJ IDEA-like search pattern in the string to match.
         *
         * @param stringToMatch       the string to be matched against.
         * @param inputString         the input string to search for.
         * @param intelijRegexPattern the regex pattern for IntelliJ IDEA-like search.
         * @return true if the input string is a substring or matches the IntelliJ experimental search pattern in the string to match.
         */
        public static boolean advancedSearch(String stringToMatch, String inputString, Pattern intelijRegexPattern) {
            return isSubString(stringToMatch, inputString) || intelij_search_experimental(stringToMatch, intelijRegexPattern);
        }

        /**
         * Checks if the input string is a substring of the string to match.
         * The search is case-insensitive.
         *
         * @param stringToMatch the string to be matched against.
         * @param inputString   the input string to search for.
         * @return true if the input string is a substring of the string to match, false otherwise.
         */
        public static boolean isSubString(String stringToMatch, String inputString) {
            return Pattern.compile(".*" + Pattern.quote(inputString) + ".*", Pattern.CASE_INSENSITIVE)
                    .matcher(stringToMatch).matches();
        }

        /**
         * Performs an IntelliJ IDEA-like experimental search on the given strings.
         * This method generates a regex pattern from the input string where each character
         * is matched case-insensitively, allowing for characters in between.
         *
         * @param stringToMatch       the string to be matched against.
         * @param intelijRegexPattern the regex pattern for IntelliJ IDEA-like search.
         * @return true if the input string matches the IntelliJ experimental search pattern in the string to match, false otherwise.
         */
        public static boolean intelij_search_experimental(String stringToMatch, Pattern intelijRegexPattern) {
            return intelijRegexPattern.matcher(stringToMatch).find();
        }

        /**
         * Generates a regex pattern for IntelliJ IDEA-like experimental search.
         * The generated pattern matches each character in the input string case-insensitively,
         * with any number of characters in between.
         *
         * @param string the input string to generate the pattern for.
         * @return the generated regex pattern.
         */
        public static Pattern generate_intelij_matcher_pattern(String string) {
            String regex = String.join(
                    ".*",
                    string.chars()
                            .mapToObj(x -> "[" + (char) x + "]")
                            .toArray(String[]::new)
            );
            return Pattern.compile(".*" + regex + ".*");
        }
    }
}
