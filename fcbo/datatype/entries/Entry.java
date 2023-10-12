package fcbo.datatype.entries;

import java.util.ArrayList;

import fcbo.datatypes.Category;
import fcbo.datatypes.Member;
import fcbo.datatypes.time.Date;

public abstract class Entry extends Category {
    private double revenue;
    private Date date;
    private ArrayList<Member> membersInvolved;

    public Entry setRevenue(double revenue) {
        this.revenue = revenue;
        return this;
    }

    public Entry setDate(Date date) {
        this.date = date;
        return this;
    }

    public Entry setMembersInvolved(ArrayList<Member> membersInvolved) {
        this.membersInvolved = membersInvolved;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<Member> getMembersInvolved() {
        return membersInvolved;
    }

    public double getRevenue() {
        return revenue;
    }
}
