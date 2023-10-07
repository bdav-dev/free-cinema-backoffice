package fcbo.datatypes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {
    private LocalDate date;

    public Date() {
        date = null;
    }

    public Date(String dateString) {
        //TODO
    }

    public Date(LocalDate date) {
        this.date = date;
    }

    public Date(int day, int month, int year) {
        date = LocalDate.of(year, month, day);
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getYear() {
        return date.getYear();
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    private LocalDate getLocalDate() {
        return date;
    }

    public boolean isValid() {
        return date != null;
    }

    public boolean setDate(String dateAsString) {
        Date date = stringToDate(dateAsString);

        if (date != null) {
            this.date = date.getLocalDate();
            return true;
        }

        return false;
    }

    @Override
    public String toString() {

        if (date == null) {
            return "null";
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        return dtf.format(date);

    }

    public static Date stringToDate(String dateAsString) {
        String[] dateAsStringArray = new String[3];

        for (int i = 0; i < dateAsStringArray.length; i++) {
            dateAsStringArray[i] = "";
        }

        int currentDateIndex = 0;

        for (int i = 0; i < dateAsString.length(); i++) {
            if (currentDateIndex == 3) {
                break;
            }

            char tempChar = dateAsString.charAt(i);

            if (tempChar == '.') {
                currentDateIndex++;
            } else {
                dateAsStringArray[currentDateIndex] += String.valueOf(tempChar);
            }
        }

        int[] dateAsInt = new int[3];

        try {
            dateAsInt[0] = Integer.parseInt(dateAsStringArray[0]);
            dateAsInt[1] = Integer.parseInt(dateAsStringArray[1]);
            dateAsInt[2] = Integer.parseInt(dateAsStringArray[2]);
        } catch (Exception e) {
            return null;
        }

        LocalDate date;

        try {
            date = LocalDate.of(dateAsInt[2], dateAsInt[1], dateAsInt[0]);
        } catch (Exception e) {
            return null;
        }

        boolean fail = false;

        if (dateAsInt[0] != date.getDayOfMonth()) {
            fail = true;
        }

        if (dateAsInt[1] != date.getMonthValue()) {
            fail = true;
        }

        if (dateAsInt[2] != date.getYear()) {
            fail = true;
        }

        if (!fail) {
            return new Date(date);
        }

        return null;
    }

}
