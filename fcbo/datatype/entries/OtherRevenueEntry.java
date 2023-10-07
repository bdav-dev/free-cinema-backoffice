package fcbo.datatype.entries;

public class OtherRevenueEntry extends Entry {
    private String title, description;


    public String getTitle() {
        return title;
    }

    public OtherRevenueEntry setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OtherRevenueEntry setDescription(String description) {
        this.description = description;
        return this;
    }

}
