package fcbo.datatypes.time;

public class Timestamp {

    private Time time;
    private Date date;

    // Format: "yyyy-mm-dd hh:mm:ss"
    public Timestamp(String timestampString) {
        String[] dateTime = timestampString.split(" ");
        
        date = new Date(dateTime[0]);
        time = new Time(dateTime[1]);
    }

    public Timestamp(Date date, Time time) {
        this.time = time;
        this.date = date;
    }

    public static Timestamp now() {
        return new Timestamp(Date.now(), Time.now());
    }

    @Override
    public String toString() {
        return date + " " + time;
    }

}
