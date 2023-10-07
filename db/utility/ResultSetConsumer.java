package db.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.DatabaseException;

@FunctionalInterface
public interface ResultSetConsumer {
    void accept(ResultSet resultSet) throws SQLException, DatabaseException;
}
