package exceptions;

import java.sql.SQLException;

public class DatabaseException extends DisplayableException {

    public DatabaseException(String messages) {
        super("Datenbankfehler", messages);
    }

    public DatabaseException(SQLException sqlException) {
        this("SQLException " + sqlException.getMessage() + " " + sqlException.getStackTrace());
    }

}
