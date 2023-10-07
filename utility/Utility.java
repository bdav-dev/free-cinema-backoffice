package utility;

public class Utility {

    private final static Utility instance = new Utility();

    private Utility() {

    }

    public static Utility getInstance() {
        return instance;
    }


    public static int parseInt(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return fallback;
        }
    }

    public static double parseDouble(String value, double fallback) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return fallback;
        }
    }

}
