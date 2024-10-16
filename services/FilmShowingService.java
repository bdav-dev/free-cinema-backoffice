package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.model.FilmShowing;
import db.utility.HasDatabaseAccess;
import exceptions.DatabaseException;
import fcbo.datatype.entries.FilmShowingEntry;
import fcbo.datatypes.time.Date;
import fcbo.datatypes.time.Timestamp;

public class FilmShowingService implements HasDatabaseAccess {
    private static FilmShowingService instance;

    private UserAccountService userAccountService;

    private FilmShowingService() {
        userAccountService = UserAccountService.getInstance();
    }

    public static FilmShowingService getInstance() {
        if (instance == null)
            instance = new FilmShowingService();

        return instance;
    }

    public List<FilmShowing> getActiveFilmShowings() throws DatabaseException {
        var filmShowings = new ArrayList<FilmShowing>();

        var resultSet = database().executeFetchResultSet(
                ps -> {
                    ps.setInt(1, database().mandt);
                    ps.setInt(2, database().mandt);
                },
                """
                            SELECT *
                                FROM FilmShowing AS fs
                                JOIN UserAccount AS ua
                                    ON ua.userAccountID = fs.createdByUserAccount
                                    AND ua.mandt = ?
                                WHERE fs.active = 1
                                    AND fs.mandt = ?
                        """);

        database().forEach(resultSet, rs -> {
            filmShowings.add(fromResultSet(rs));
        });

        return filmShowings;
    }

    public List<FilmShowingEntry> getFilmShowingEntries(int filmShowingID) throws DatabaseException {
        var filmShowingEntries = new ArrayList<FilmShowingEntry>();

        var resultSet = database().executeFetchResultSet(
                query -> {
                    query.setInt(1, filmShowingID);
                    query.setInt(2, database().mandt);
                },
                """
                            SELECT *
                            FROM FilmShowingEntry
                            WHERE filmShowingID = ?
                                AND mandt = ?
                        """);

        database().forEach(resultSet, rs -> {
            filmShowingEntries.add(getFilmShowingEntry(rs));
        });

        return filmShowingEntries;
    }

    private FilmShowingEntry getFilmShowingEntry(ResultSet rs) {
        return null; // TODO
    }

    private FilmShowing fromResultSet(ResultSet resultSet) throws DatabaseException {
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
                    .setMoneyTransferred(
                            Date.fromNullableString(resultSet.getString("moneyTransferred")))
                    .setOtherNotes(resultSet.getString("otherNotes"))
                    .setTimeOfCreation(new Timestamp(resultSet.getString("timeOfCreation")))
                    .setCreatedBy(userAccountService.fromResultSet(resultSet))
                    .setID(resultSet.getInt("filmShowingID"));
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        return filmShowing;
    }
}
