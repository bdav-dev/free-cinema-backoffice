package fcbo.datatype.entries;

public enum EntryType {
    FilmShowingAndBarRevenue("Kino- & Bareinnahmen"),

    Sponsoring("Sponsoring"),

    Donation("Spende"),

    RoomRental("Raummiete"),

    Coupon("Gutschein"),

    OtherRevenue("sonstige Einnahme"),

    Expense("Ausgabe");



    private String displayName;

    private EntryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return " " + displayName;
    }

}
