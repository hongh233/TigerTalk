package tigertalk.model;

import java.util.regex.Pattern;

public final class Utils {

    public static final class RegexCheck {

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
