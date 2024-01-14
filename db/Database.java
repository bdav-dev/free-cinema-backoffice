package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;

import appsettings.AppSettings;
import db.utility.PreparedStatementConsumer;
import db.utility.ResultSetConsumer;
import db.utility.ResultSetFunction;
import exceptions.DatabaseException;
import fcbo.datatype.entries.FilmShowingEntry;
import fcbo.datatypes.FilmShowing;
import fcbo.datatypes.Member;
import fcbo.datatypes.UserAccount;
import fcbo.datatypes.time.Date;
import fcbo.datatypes.time.Timestamp;

/**
 * This class is the database management system of this project.
 * With it's public methods, the main class (FCBO) can retrieve and manipulate
 * information from the database.
 * These are called "Interface Methods" because they are the interface between
 * this class and the FCBO class.
 * Every "Interface Method" calls a "Datatype Query Method" during it's
 * lifetime.
 * These methods always return one specific datatype (located in
 * fcbo.datatypes).
 * There is a option to alter the standard query (SELECT * FROM [Table(s)]) with
 * the use of the
 * "conditions: String" parameter. Conditions such as "WHERE active = 1" can be
 * added to limit results.
 * "Datatype Query Methods" interpret the ResultSet from the executed "Generic
 * Query Method"
 * and returns the converted datatype.
 * Every "Datatype Query Method" calls a "Generic Query Method" during it's
 * lifetime
 * A "Generic Query Method" executes the handed over query from a "Datatype
 * Query Method"
 * and always returns the query result as a generic ResultSet which the
 * "Datatype Query Method"
 * further interprets.
 * Interface Method -> Datatype Query Method -> Generic Query Method
 *
 * @author David Berezowski
 */
public class Database {
    private final DatabaseCredentials credentials;

    private Connection connection;
    private PreparedStatement currentStatement;

    private final int mandt = AppSettings.getInstance().mandt().get();

    private static Database instance;

    public static record DatabaseCredentials(String database, String host, String port, String username, String password) {}

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



    // --- INTERFACE METHODS ---

    public void submitLoginLogEntry(int userAccountID, boolean loggedIn) throws DatabaseException {
        executeFetchAffectedRows(
                ps -> {
                    ps.setInt(1, mandt);
                    ps.setInt(2, userAccountID);
                    ps.setString(3, Timestamp.now().toString());
                    ps.setInt(4, loggedIn ? 1 : 0);
                },
                "INSERT INTO LoginLog",
                "VALUES (?, ?, ?, ?)");
    }

    public Member getMemberFromAccountUsername(String username) throws DatabaseException {

        ResultSet resultSet = executeFetchResultSet(
                ps -> {
                    ps.setString(1, username);
                },
                "SELECT m.*",
                "FROM UserAccount AS ua",
                "JOIN Member AS m",
                "ON ua.memberID = m.memberID",
                "AND ua.mandt = " + mandt,
                "WHERE ua.username = ?",
                "AND m.mandt = " + mandt);

        Member member = single(resultSet, rs -> {
            Member m = new Member();

            m.setMemberID(resultSet.getInt("memberID"))
                    .setFirstName(resultSet.getString("firstName"))
                    .setLastName(resultSet.getString("lastName"))
                    .setDisplayName(resultSet.getString("displayName"))
                    .setActive(resultSet.getInt("active") == 1);

            return m;
        });

        return member;
    }

    public UserAccount getUserAccount(String username, String saltedPasswordHash) throws DatabaseException {
        ResultSet resultSet = executeFetchResultSet(
                ps -> {
                    ps.setString(1, username);
                    ps.setString(2, saltedPasswordHash);
                },
                "SELECT *",
                "FROM UserAccount",
                "WHERE username = ?",
                "AND passwordHash = ?",
                "AND mandt = " + mandt);

        UserAccount userAccount = single(resultSet, rs -> {
            UserAccount account = new UserAccount();

            account.setID(resultSet.getInt("userAccountID"))
                    .setUsername(resultSet.getString("username"))
                    .setPrivilegeLevel(resultSet.getInt("privilegeLevel"))
                    .setPasswordHash(resultSet.getString("passwordHash"));

            return account;
        });

        return userAccount;
    }

    public void changePasswordHash(String username, String newPasswordHash) throws DatabaseException {
        executeFetchAffectedRows(
                ps -> {
                    ps.setString(1, newPasswordHash);
                    ps.setString(2, username);
                },
                "UPDATE UserAccount",
                "SET passwordHash = ?",
                "WHERE username = ?",
                "AND mandt = " + mandt);
    }

    public void changeUsername(String username, String newUsername) throws DatabaseException {
        executeFetchAffectedRows(
                ps -> {
                    ps.setString(1, newUsername);
                    ps.setString(2, username);
                },
                "UPDATE UserAccount",
                "SET username = ?",
                "WHERE username = ?",
                "AND mandt = " + mandt);
    }

    public ArrayList<String> getAllUsernames() throws DatabaseException {
        var usernames = new ArrayList<String>();

        var resultSet = executeFetchResultSet(
                "SELECT username",
                "FROM UserAccount",
                "WHERE mandt = " + mandt);

        forEach(resultSet, rs -> {
            usernames.add(rs.getString("username"));
        });

        return usernames;
    }

    public ArrayList<FilmShowing> getActiveFilmShowings() throws DatabaseException {
        var filmShowings = new ArrayList<FilmShowing>();

        var resultSet = executeFetchResultSet(
                "SELECT *",
                "FROM FilmShowing AS fs",
                "JOIN UserAccount AS ua",
                "ON ua.userAccountID = fs.createdByUserAccount",
                "AND ua.mandt = " + mandt,
                "WHERE fs.active = 1",
                "AND fs.mandt = " + mandt);

        forEach(resultSet, rs -> {
            filmShowings.add(getFilmShowing(rs));
        });

        return filmShowings;
    }

    public ArrayList<FilmShowingEntry> getFilmShowingEntries(int id) throws DatabaseException {
        var filmShowingEntries = new ArrayList<FilmShowingEntry>();

        var resultSet = executeFetchResultSet(
                ps -> {
                    ps.setInt(1, id);
                },
                "SELECT *",
                "FROM FilmShowingEntry",
                "WHERE filmShowingID = ?",
                "AND mandt = " + mandt);

        forEach(resultSet, rs -> {
            filmShowingEntries.add(getFilmShowingEntry(rs));
        });

        return filmShowingEntries;
    }

    private FilmShowingEntry getFilmShowingEntry(ResultSet rs) {
        return null;
    }



    // --- CONVERTER METHODS ---

    private FilmShowing getFilmShowing(ResultSet resultSet) throws DatabaseException {
        FilmShowing filmShowing = new FilmShowing();

        try {
            filmShowing
                    .setMovieName(resultSet.getString("movieName"))
                    .setActive(resultSet.getBoolean("active"))
                    .setMwst(resultSet.getDouble("mwst"))
                    .setMinimumGuarantee(resultSet.getDouble("minimumGuarantee"))
                    .setRentalRate(resultSet.getDouble("rentalRate"))
                    .setAdvertisingCost(resultSet.getDouble("advertisingCost"))
                    .setSpioCost(resultSet.getDouble("spioCost"))
                    .setOtherCost(resultSet.getDouble("otherCost"))
                    .setMoneyTransferred(Date.fromNullableString(resultSet.getString("moneyTransferred")))
                    .setOtherNotes(resultSet.getString("otherNotes"))
                    .setTimeOfCreation(new Timestamp(resultSet.getString("timeOfCreation")))
                    .setCreatedBy(getUserAccount(resultSet))
                    .setID(resultSet.getInt("filmShowingID"));
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        return filmShowing;
    }

    private UserAccount getUserAccount(ResultSet resultSet) throws DatabaseException {
        UserAccount userAccount = new UserAccount();

        try {
            userAccount
                    .setID(resultSet.getInt("userAccountID"))
                    .setUsername(resultSet.getString("username"))
                    .setPasswordHash(resultSet.getString("passwordHash"))
                    .setPrivilegeLevel(resultSet.getInt("privilegeLevel"));
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        return userAccount;
    }



    // --- GENERIC QUERY METHODS ---

    public ResultSet executeFetchResultSet(String... query) throws DatabaseException {
        return executeFetchResultSet(null, query);
    }

    public ResultSet executeFetchResultSet(PreparedStatementConsumer setParameter, String... query) throws DatabaseException {
        String sqlQuery = convertMultilineQuery(query);

        PreparedStatement ps;
        ResultSet resultSet;

        connect();

        try {
            ps = connection.prepareStatement(sqlQuery);
            currentStatement = ps;
            if (setParameter != null)
                setParameter.accept(ps);
            resultSet = ps.executeQuery();
        } catch (SQLTimeoutException timeoutE) {
            throw new DatabaseException("Die SQL Abfrage: " + query + " konnte nicht ausgeführt werden. Es liegt ein Timeout vor.");
        } catch (SQLException sqlE) {
            throw new DatabaseException(sqlE);
        }

        return resultSet;
    }

    public int executeFetchAffectedRows(String... statement) throws DatabaseException {
        return executeFetchAffectedRows(null, statement);
    }

    public int executeFetchAffectedRows(PreparedStatementConsumer setParameter, String... statement) throws DatabaseException {
        String sqlStatement = convertMultilineQuery(statement);
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
            throw new DatabaseException("Die SQL Abfrage: '" + sqlStatement + "' konnte nicht ausgeführt werden. Es liegt ein Timeout vor.");
        } catch (SQLException sqlE) {
            throw new DatabaseException(sqlE);
        } finally {
            cleanup(null);
        }

        return affectedRows;
    }



    // --- UTILITY METHODS ---

    private String convertMultilineQuery(String... query) throws DatabaseException {
        if (query.length == 0)
            throw new DatabaseException("Es wurde keine SQL Abfrage übergeben.");

        if (query.length > 0)
            return String.join(" ", query);
        else
            return query[0];
    }

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

    public DatabaseCredentials getConnectionInfo() {
        return credentials;
    }

    private void forEach(ResultSet resultSet, ResultSetConsumer action) throws DatabaseException {
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

    private <T> T single(ResultSet resultSet, ResultSetFunction<T> action) throws DatabaseException {
        try {
            while (resultSet.next())
                return action.apply(resultSet);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            cleanup(resultSet);
        }

        return null;
    }


    // --- DATABASE CONNECTION METHODS ---

    public void connect() throws DatabaseException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + credentials.host + ":" + credentials.port + "/" + credentials.database, credentials.username,
                    credentials.password);
        } catch (SQLException e) {
            throw new DatabaseException("Es konnte keine Verbindung mit der Datenbank hergestellt werden. Bitte überprüfe die Internetverbindung.");
        }
    }

    public void disconnect() throws DatabaseException {
        try {
            if (connection != null)
                connection.close();
        } catch (Exception e) {
            throw new DatabaseException("Die Verbindung zur Datenbank konnte nicht getrennt werden.");
        }
    }

}
