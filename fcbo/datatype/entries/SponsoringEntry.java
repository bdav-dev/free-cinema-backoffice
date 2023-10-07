package fcbo.datatype.entries;

public class SponsoringEntry extends Entry {
    private String sponsor;
    private String description;


    public String getSponsor() {
        return sponsor;
    }

    public SponsoringEntry setSponsor(String sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SponsoringEntry setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public double getRevenue() {
        return getRevenue();
    }

}