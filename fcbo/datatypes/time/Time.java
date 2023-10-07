package fcbo.datatypes.time;

import java.time.LocalTime;

public class Time {
    private int hours, minutes, seconds;

    // Format: "hh:mm:ss"
    public Time(String timeString) {
        String[] hoursMinutesSeconds = timeString.split(":");
        
        hours = Integer.parseInt(hoursMinutesSeconds[0]);
        minutes = Integer.parseInt(hoursMinutesSeconds[1]);
        seconds = Integer.parseInt(hoursMinutesSeconds[2]);
    }

    public Time() {

    }

    public static Time now() {
        LocalTime localTime = LocalTime.now();

        Time now = new Time();
        now.setHours(localTime.getHour());
        now.setMinutes(localTime.getMinute());
        now.setSeconds(localTime.getSecond());

        return now;
    }

    @Override
    public String toString() {
        return digitsToString(hours) + ":" + digitsToString(minutes) + ":" + digitsToString(seconds);
    }

    private String digitsToString(int number) {
        return String.valueOf(number).length() == 1 ? "0" + number : String.valueOf(number);
    }

    public int getHours() {
        return hours;
    }

    public Time setHours(int hours) {
        this.hours = hours;
        return this;
    }

    public int getMinutes() {
        return minutes;
    }

    public Time setMinutes(int minutes) {
        this.minutes = minutes;
        return this;
    }

    public int getSeconds() {
        return seconds;
    }

    public Time setSeconds(int seconds) {
        this.seconds = seconds;
        return this;
    }

    

}
