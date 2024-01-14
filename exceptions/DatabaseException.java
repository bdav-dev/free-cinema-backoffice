package exceptions;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DatabaseException extends DisplayableException {

    public DatabaseException(String messages) {
        super("Datenbankfehler", messages);
    }

    public DatabaseException(SQLException sqlException) {
        this("SQLException: " + sqlException.getMessage() + "\n\nStacktrace:\n" + Arrays.stream(sqlException.getStackTrace()).map(a -> a.toString()).collect(Collectors.joining("\n")));
    }

}
