package db.model;

public class Tag {
    private int tagID;
    private String title, description;

    public Tag() {

    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tag t && t.getTagID() == this.getTagID())
            return true;

        return false;
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
