package services;

import java.util.Optional;

import db.model.Member;
import db.model.UserAccount;
import db.utility.HasDatabaseAccess;
import exceptions.DatabaseException;
import exceptions.WrongLoginCredentialsException;
import fcbo.datatypes.time.Timestamp;
import utility.CryptographicUtility;


public class LoginService implements HasDatabaseAccess {

    private static LoginService instance;

    private UserAccount loggedInUser;
    private Member associatedMember;

    private LoginService() {}

    public static LoginService getInstance() {
        if (instance == null)
            instance = new LoginService();

        return instance;
    }

    public void loginUser(String username, String password) throws DatabaseException, WrongLoginCredentialsException {
        String saltedHash = CryptographicUtility.getInstance().getSaltedPasswordHash(password);
        Optional<UserAccount> userAccountOptional = UserAccountService.getInstance().getUserAccount(username);

        if (userAccountOptional.isEmpty())
            throw new WrongLoginCredentialsException(
                    String.format("Der Benutzername %s existiert nicht", username));

        var userAccount = userAccountOptional.get();

        if (!saltedHash.equals(userAccount.getPasswordHash()))
            throw new WrongLoginCredentialsException("Falsches Passwort");

        var associatedMemberOptional = MemberService.getInstance().getMemberFromAccountUsername(userAccount.getUsername());

        if (associatedMemberOptional.isEmpty())
            throw new DatabaseException("Das zugehörige Mitglied zum Account wurde nicht gefunden");
        
        this.loggedInUser = userAccount;
        this.associatedMember = associatedMemberOptional.get();

        submitLoginLogEntry(loggedInUser.getID(), true);
    }

    public void logoutUser() throws DatabaseException {
        try {
            submitLoginLogEntry(loggedInUser.getID(), false);
        } catch (DatabaseException e) {
            throw new DatabaseException("Der Benutzer konnte nicht ausgeloggt werden.");
        }

        loggedInUser = null;
    }

    public boolean isUserLoggedIn() {
        return loggedInUser != null;
    }

    public Optional<UserAccount> getLoggedInUser() {
        return Optional.ofNullable(loggedInUser);
    }

    private void submitLoginLogEntry(int userAccountID, boolean loggedIn) throws DatabaseException {
        database().execute(
                ps -> {
                    ps.setInt(1, database().mandt);
                    ps.setInt(2, userAccountID);
                    ps.setString(3, Timestamp.now().toString());
                    ps.setInt(4, loggedIn ? 1 : 0);
                },
                """
                            INSERT INTO LoginLog
                            VALUES (?, ?, ?, ?)
                        """);
    }

}
