package db.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.DatabaseException;

@FunctionalInterface
public interface ResultSetFunction<R> {
    R apply(ResultSet rs) throws SQLException, DatabaseException;
}
