package fcbo.datatype.entries;

public class DonationEntry extends Entry {
    private String donor;
    private String description;


    public String getDonor() {
        return donor;
    }

    public DonationEntry setDonor(String donor) {
        this.donor = donor;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DonationEntry setDescription(String description) {
        this.description = description;
        return this;
    }

}
