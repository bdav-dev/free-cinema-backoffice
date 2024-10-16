package utility;

import java.util.Optional;

public class Utility {

    private final static Utility instance = new Utility();

    private Utility() {

    }

    public static Utility getInstance() {
        return instance;
    }

    public static String charArrayToString(char[] charArray) {
        StringBuilder stringBuilder = new StringBuilder();

        for (char c : charArray)
            stringBuilder.append(c);

        return stringBuilder.toString();
    }

    public static int parseInt(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return fallback;
        }
    }

    public static Optional<Integer> parseInt(String value) {
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
        }

        return Optional.empty();
    }


    public static double parseDouble(String value, double fallback) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return fallback;
        }
    }

}
