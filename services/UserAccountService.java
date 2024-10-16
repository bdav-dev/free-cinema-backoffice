package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import db.model.UserAccount;
import db.utility.HasDatabaseAccess;
import exceptions.DatabaseException;
import exceptions.OldPasswordDoesNotMatchException;
import exceptions.PasswordEmptyException;
import exceptions.PasswordsDoNotMatchException;
import exceptions.UsernameAlreadyExistsException;
import utility.CryptographicUtility;

public class UserAccountService implements HasDatabaseAccess {

    private static UserAccountService instance;

    private UserAccountService() {}

    public static UserAccountService getInstance() {
        if (instance == null)
            instance = new UserAccountService();

        return instance;
    }


    public Optional<UserAccount> getUserAccount(String username) throws DatabaseException {
        ResultSet resultSet = database().executeFetchResultSet(
                ps -> {
                    ps.setString(1, username);
                    ps.setInt(2, database().mandt);
                },
                """
                        SELECT *
                        FROM UserAccount
                        WHERE username = ?
                        AND mandt = ?
                                """);


        return database().single(resultSet, rs -> {
            UserAccount account = new UserAccount();

            account.setID(resultSet.getInt("userAccountID"))
                    .setUsername(resultSet.getString("username"))
                    .setPrivilegeLevel(resultSet.getInt("privilegeLevel"))
                    .setPasswordHash(resultSet.getString("passwordHash"));

            return account;
        });
    }


    public UserAccount fromResultSet(ResultSet resultSet) throws DatabaseException {
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


    private void changePasswordHash(String username, String newPasswordHash) throws DatabaseException {
        database().executeFetchAffectedRows(
                ps -> {
                    ps.setString(1, newPasswordHash);
                    ps.setString(2, username);
                    ps.setInt(3, database().mandt);
                },
                """
                        UPDATE UserAccount
                        SET passwordHash = ?
                        WHERE username = ?
                        AND mandt = ?
                        """);
    }

    public List<String> getAllUsernames() throws DatabaseException {
        var usernames = new ArrayList<String>();

        var resultSet = database().executeFetchResultSet(
                ps -> ps.setInt(1, database().mandt),
                """
                        SELECT username
                        FROM UserAccount
                        WHERE mandt = ? """);

        database().forEach(resultSet, rs -> {
            usernames.add(rs.getString("username"));
        });

        return usernames;
    }


    public void changeUsername(UserAccount userAccount, String newUsername) throws UsernameAlreadyExistsException, DatabaseException {
        changeUsername(userAccount.getUsername(), newUsername);
        userAccount.setUsername(newUsername);
    }

    private void changeUsername(String currentUsername, String newUsername) throws UsernameAlreadyExistsException, DatabaseException {
        var usernames = getAllUsernames();

        if (usernames.contains(newUsername))
            throw new UsernameAlreadyExistsException(newUsername);

        database().execute(
                ps -> {
                    ps.setString(1, newUsername);
                    ps.setString(2, currentUsername);
                    ps.setInt(3, database().mandt);
                },
                """
                        UPDATE UserAccount
                        SET username = ?
                            WHERE username = ?
                        AND mandt = ?
                        """);
    }


    public void changePassword(UserAccount userAccount, String oldPassword, String newPassword, String newPasswordConfirm)
            throws OldPasswordDoesNotMatchException, PasswordsDoNotMatchException, PasswordEmptyException, DatabaseException {

        var oldPasswordHash = CryptographicUtility.getInstance().getSaltedPasswordHash(oldPassword);

        if (!oldPasswordHash.equals(userAccount.getPasswordHash()))
            throw new OldPasswordDoesNotMatchException();

        if (!newPassword.equals(newPasswordConfirm))
            throw new PasswordsDoNotMatchException();

        if (newPassword.isBlank())
            throw new PasswordEmptyException();

        changePassword(userAccount, newPassword);
    }


    private void changePassword(UserAccount userAccount, String newPassword)
            throws DatabaseException {

        var newPasswordHash = CryptographicUtility.getInstance().getSaltedPasswordHash(newPassword);

        changePasswordHash(userAccount.getUsername(), newPasswordHash);
        userAccount.setPasswordHash(newPasswordHash);
    }

}
