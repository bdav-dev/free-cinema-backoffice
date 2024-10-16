package db.model;

public class UserAccount {
    private String username, passwordHash;
    private int id, privilegeLevel;

    public UserAccount() {

    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UserAccount ua && ua.getID() == this.id)
            return true;

        return false;
    }

    public String getUsername() {
        return username;
    }

    public UserAccount setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserAccount setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public int getPrivilegeLevel() {
        return privilegeLevel;
    }

    public UserAccount setPrivilegeLevel(int privilegeLevel) {
        this.privilegeLevel = privilegeLevel;
        return this;
    }

    public int getID() {
        return id;
    }

    public UserAccount setID(int id) {
        this.id = id;
        return this;
    }



}
