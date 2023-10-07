package fcbo.datatype.entries;

public class ExpenseEntry extends Entry {
    private String title;
    private String description;


    @Override
    public double getRevenue() {
        return -1 * getRevenue();
    }

    public String getTitle() {
        return title;
    }

    public ExpenseEntry setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ExpenseEntry setDescription(String description) {
        this.description = description;
        return this;
    }

}
