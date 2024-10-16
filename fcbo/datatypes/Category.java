package fcbo.datatypes;

import java.util.ArrayList;

import db.model.Tag;
import db.model.UserAccount;
import fcbo.datatypes.time.Timestamp;

public abstract class Category {
    private int id;
    private String otherNotes;
    private UserAccount createdBy;
    private Timestamp timeOfCreation;
    private ArrayList<Tag> tags;

    public String getOtherNotes() {
        return otherNotes;
    }

    public Category() {

    }

    public Category(int id) {
        setID(id);
    }

    public Category setOtherNotes(String otherNotes) {
        this.otherNotes = otherNotes;
        return this;
    }

    public int getID() {
        return id;
    }

    public Category setID(int id) {
        this.id = id;
        return this;
    }

    public UserAccount getCreatedBy() {
        return createdBy;
    }

    public Category setCreatedBy(UserAccount createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Timestamp getTimeOfCreation() {
        return timeOfCreation;
    }

    public Category setTimeOfCreation(Timestamp timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
        return this;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public Category setTags(ArrayList<Tag> tags) {
        this.tags = tags;
        return this;
    }

}
