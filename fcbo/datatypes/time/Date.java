package fcbo.datatypes.time;

import java.time.LocalDate;

public class Date {
    private int year, month, day;

    // Format: "yyyy-mm-dd"
    public Date(String dateString) {
        String[] yearMonthDay = dateString.split("-");

        year = Integer.parseInt(yearMonthDay[0]);
        month = Integer.parseInt(yearMonthDay[1]);
        day = Integer.parseInt(yearMonthDay[2]);
    }

    public static Date fromNullableString(String dateString) {
        if (dateString == null)
            return null;

        return new Date(dateString);
    }

    public Date() {

    }

    public static Date now() {
        LocalDate localDate = LocalDate.now();

        Date now = new Date();
        now.setDay(localDate.getDayOfMonth());
        now.setMonth(localDate.getMonthValue());
        now.setYear(localDate.getYear());

        return now;
    }

    @Override
    public String toString() {
        return digitsToString(year, 4) + "-" + digitsToString(month, 2) + "-" + digitsToString(day, 2);
    }

    private String digitsToString(int value, int digits) {
        int requiredZeroDigits = digits - String.valueOf(value).length();
        return "0".repeat(requiredZeroDigits) + value;
    }

    public int getYear() {
        return year;
    }

    public Date setYear(int year) {
        this.year = year;
        return this;
    }

    public int getMonth() {
        return month;
    }

    public Date setMonth(int month) {
        this.month = month;
        return this;
    }

    public int getDay() {
        return day;
    }

    public Date setDay(int day) {
        this.day = day;
        return this;
    }


}
