package dev.bdav.fcbo.util;

public class StringUtils {
    private StringUtils() {
    }

    public static boolean isBlank(String string) {
        if (string == null) {
            return true;
        }

        return string.isBlank();
    }

}
