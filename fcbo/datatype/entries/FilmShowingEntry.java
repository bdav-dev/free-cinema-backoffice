package fcbo.datatype.entries;

public class FilmShowingEntry extends Entry {
    private int rollStart;
    private int rollEnd;
    private double ticketPrice;

    public FilmShowingEntry() {
        super();
    }

    public long getRollStart() {
        return rollStart;
    }

    public FilmShowingEntry setRollStart(int rollStart) {
        this.rollStart = rollStart;
        return this;
    }

    public long getRollEnd() {
        return rollEnd;
    }

    public FilmShowingEntry setRollEnd(int rollEnd) {
        this.rollEnd = rollEnd;
        return this;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public FilmShowingEntry setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
        return this;
    }

}
