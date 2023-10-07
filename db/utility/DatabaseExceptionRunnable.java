package db.utility;

import exceptions.DatabaseException;

@FunctionalInterface
public interface DatabaseExceptionRunnable {
    void run() throws DatabaseException;
}
