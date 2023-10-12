package fcbo.datatypes;

import java.util.ArrayList;

import fcbo.datatype.entries.FilmShowingEntry;
import fcbo.datatypes.time.Date;

public class FilmShowing extends Category {
    private String movieName; // Filmname
    private double mwst; // Mehrwertsteuer (0,07)
    private double minimumGuarantee; // Mindestgarantie
    private double rentalRate; // Mietsatz
    private double advertisingCost; // Reklame
    private double spioCost; // Spio
    private double otherCost; // andere Kosten
    private boolean active; // aktiv?
    private Date moneyTransferred; // Geld überwiesen

    private ArrayList<FilmShowingEntry> filmShowingEntries;

    public FilmShowing() {
        super();
    }

    @Override
    public String toString() {
        return getMovieName();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FilmShowing fs && fs.getID() == this.getID())
            return true;

        return false;
    }

    /* Getters and Setters */

    public String getMovieName() {
        return movieName;
    }

    public FilmShowing setMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public FilmShowing setActive(boolean active) {
        this.active = active;
        return this;
    }

    public double getMinimumGuarantee() {
        return minimumGuarantee;
    }

    public FilmShowing setMinimumGuarantee(double minimumGuarantee) {
        this.minimumGuarantee = minimumGuarantee;
        return this;
    }

    public double getRentalRate() {
        return rentalRate;
    }

    public FilmShowing setRentalRate(double rentalRate) {
        this.rentalRate = rentalRate;
        return this;
    }

    public double getAdvertisingCost() {
        return advertisingCost;
    }

    public FilmShowing setAdvertisingCost(double advertisingCost) {
        this.advertisingCost = advertisingCost;
        return this;
    }

    public double getSpioCost() {
        return spioCost;
    }

    public FilmShowing setSpioCost(double spioCost) {
        this.spioCost = spioCost;
        return this;
    }

    public double getOtherCost() {
        return otherCost;
    }

    public FilmShowing setOtherCost(double otherCost) {
        this.otherCost = otherCost;
        return this;
    }

    public Date getDateMoneyTransferred() {
        return moneyTransferred;
    }

    public FilmShowing setDateMoneyTransferred(Date moneyTransferred) {
        this.moneyTransferred = moneyTransferred;
        return this;
    }

    public double getMwst() {
        return mwst;
    }

    public FilmShowing setMwst(double mwst) {
        this.mwst = mwst;
        return this;
    }

    public Date getMoneyTransferred() {
        return moneyTransferred;
    }

    public FilmShowing setMoneyTransferred(Date moneyTransferred) {
        this.moneyTransferred = moneyTransferred;
        return this;
    }

    public ArrayList<FilmShowingEntry> getFilmShowingEntries() {
        return filmShowingEntries;
    }

    public void setFilmShowingEntries(ArrayList<FilmShowingEntry> filmShowingEntries) {
        this.filmShowingEntries = filmShowingEntries;
    }



}
