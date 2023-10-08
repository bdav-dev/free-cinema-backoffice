package fcbo.datatypes;

public class Tag {
    private int tagID;
    private String title, description;

    public Tag() {

    }

    public String getTitle() {
        return title;
    }

    public Tag setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Tag setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getTagID() {
        return tagID;
    }

    public Tag setTagID(int tagID) {
        this.tagID = tagID;
        return this;
    }

}
