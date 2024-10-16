package db.utility;

import db.Database;

public interface HasDatabaseAccess {
    default Database database() {
        return Database.getInstance();
    }
}
