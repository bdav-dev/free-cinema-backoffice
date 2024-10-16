package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Arrays;
import java.util.Optional;

import appsettings.AppSettings;
import db.utility.PreparedStatementConsumer;
import db.utility.ResultSetConsumer;
import db.utility.ResultSetFunction;
import exceptions.DatabaseException;

public class Database {
    private static Database instance;

    private final DatabaseCredentials credentials;

    private Connection connection;
    private PreparedStatement currentStatement;

    public final int mandt = AppSettings.getInstance().mandt().get();

    private Database() {
        credentials = new DatabaseCredentials(
                AppSettings.getInstance().database().database().get(),
                AppSettings.getInstance().database().host().get(),
                AppSettings.getInstance().database().port().get(),
                AppSettings.getInstance().database().username().get(),
                AppSettings.getInstance().database().password().get());
    }

    public static Database getInstance() {
        if (instance == null)
            instance = new Database();

        return instance;
    }

    public ResultSet executeFetchResultSet(String query) throws DatabaseException {
        return executeFetchResultSet(param -> {
        }, formatQuery(query));
    }

    public ResultSet executeFetchResultSet(PreparedStatementConsumer setParameter, String query) throws DatabaseException {
        String sqlQuery = formatQuery(query);

        PreparedStatement ps;
        ResultSet resultSet;

        connect();

        try {
            ps = connection.prepareStatement(sqlQuery);
            this.currentStatement = ps;
            setParameter.accept(ps);
            resultSet = ps.executeQuery();
        } catch (SQLTimeoutException timeoutE) {
            throw new DatabaseException(
                    "Die SQL Abfrage: " + query + " konnte nicht ausgeführt werden. Es liegt ein Timeout vor.");
        } catch (SQLException sqlE) {
            throw new DatabaseException(sqlE);
        }

        return resultSet;
    }

    public int executeFetchAffectedRows(String statement) throws DatabaseException {
        return executeFetchAffectedRows(ps -> {
        }, statement);
    }

    public int executeFetchAffectedRows(PreparedStatementConsumer setParameter, String statement)
            throws DatabaseException {
        String sqlStatement = formatQuery(statement);
        PreparedStatement ps;
        int affectedRows;

        connect();

        try {
            ps = connection.prepareStatement(sqlStatement);
            currentStatement = ps;
            if (setParameter != null)
                setParameter.accept(ps);
            affectedRows = ps.executeUpdate();
        } catch (SQLTimeoutException timeoutE) {
            throw new DatabaseException("Die SQL Abfrage: '" + sqlStatement
                    + "' konnte nicht ausgeführt werden. Es liegt ein Timeout vor.");

        } catch (SQLException sqlE) {
            throw new DatabaseException(sqlE);

        } finally {
            cleanup(null);

        }

        return affectedRows;
    }

    public void execute(String statement) throws DatabaseException {
        execute(ps -> {
        }, statement);
    }

    public void execute(PreparedStatementConsumer setParameter, String statement) throws DatabaseException {
        executeFetchAffectedRows(setParameter, statement);
    }


    // --- UTILITY METHODS ---

    public void cleanup(ResultSet resultSet) throws DatabaseException {
        try {
            if (resultSet != null)
                resultSet.close();

            if (currentStatement != null)
                currentStatement.close();

            if (connection != null)
                disconnect();

        } catch (SQLException s) {
            throw new DatabaseException(
                    "Das ResultSet, PreparedStatement oder die Connection konnte nicht geschlossen werden");
        }
    }

    public void forEach(ResultSet resultSet, ResultSetConsumer action) throws DatabaseException {
        try {
            while (resultSet.next()) {
                action.accept(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            cleanup(resultSet);
        }
    }

    public <T> Optional<T> single(ResultSet resultSet, ResultSetFunction<T> action) throws DatabaseException {
        try {
            while (resultSet.next())
                return Optional.of(action.apply(resultSet));
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            cleanup(resultSet);
        }

        return Optional.empty();
    }


    // --- DATABASE CONNECTION METHODS ---

    private void connect() throws DatabaseException {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + credentials.host + ":" + credentials.port + "/" + credentials.database,
                    credentials.username,
                    credentials.password);
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Es konnte keine Verbindung mit der Datenbank hergestellt werden. Bitte überprüfe die Internetverbindung.");
        }
    }

    private void disconnect() throws DatabaseException {
        try {
            if (connection != null)
                connection.close();
        } catch (Exception e) {
            throw new DatabaseException("Die Verbindung zur Datenbank konnte nicht getrennt werden.");
        }
    }

    private String formatQuery(String query) {
        String[] formattedLines = Arrays.stream(query.split("\\r?\\n"))
                .map(s -> s.trim())
                .filter(s -> !s.isBlank())
                .toArray(String[]::new);

        return String.join(" ", formattedLines);
    }

    public DatabaseCredentials getConnectionInfo() {
        return credentials;
    }

    public static record DatabaseCredentials(
            String database,
            String host, String port,
            String username,
            String password) {}


}
