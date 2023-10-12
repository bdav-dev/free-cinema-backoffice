package fcbo.datatypes;

public class Member {
    private int memberID;
    private String firstName, lastName, displayName;
    private boolean active;

    public Member() {

    }

    public Member(int memberID, String firstName, String lastName, String displayName, boolean active) {
        this.memberID = memberID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Member m && m.getMemberID() == this.getMemberID())
            return true;

        return false;
    }

    public int getMemberID() {
        return memberID;
    }

    public Member setMemberID(int memberID) {
        this.memberID = memberID;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Member setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Member setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Member setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public Member setActive(boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
