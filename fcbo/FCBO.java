package fcbo;

import utility.CryptographicUtility;

import java.sql.ResultSet;
import java.util.ArrayList;

import appsettings.AppSettings;
import cli.TerminalPage;
import db.Database;
import exceptions.DatabaseException;
import exceptions.DisplayableException;
import exceptions.OldPasswordDoesNotMatchException;
import exceptions.PasswordEmptyException;
import exceptions.PasswordsDoNotMatchException;
import exceptions.UsernameAlreadyExistsException;
import exceptions.WrongLoginCredentialsException;
import fcbo.datatype.entries.FilmShowingEntry;
import fcbo.datatypes.*;
import free_ui.UI;
import free_ui.Page;
import ui.pages.LoginPage;
import ui.pages.MainMenuPage;

public class FCBO {
    private UI uiManager;
    private Database db;

    private UserAccount currentUser;
    private Member associatedMember;

    public static final String VERSION = "1.0dev";

    private static FCBO instance;

    private FCBO() {
        uiManager = UI.getInstance();
        db = Database.getInstance();

        // Page.setShift(0, 0, -13, -38); // Settings for Ubuntu

        Page.setShift(
                AppSettings.getInstance().layoutCorrections().x().get(),
                AppSettings.getInstance().layoutCorrections().y().get(),
                AppSettings.getInstance().layoutCorrections().width().get(),
                AppSettings.getInstance().layoutCorrections().height().get());

        currentUser = null;

        uiManager.launchPanel(() -> new LoginPage());
    }

    public static FCBO getInstance() {
        if (instance == null)
            instance = new FCBO();

        return instance;
    }

    public void createMember(Member member) {

    }

    // --- LOGIN / LOGOUT METHODS ---

    public void performLoginFromLoginPanel(String username, char[] password)
            throws DatabaseException, WrongLoginCredentialsException {
        loginUser(username, password);
        TerminalPage.setLocked(false);
        uiManager.launchPanel(() -> new MainMenuPage());
    }

    private void loginUser(String username, char[] password) throws DatabaseException, WrongLoginCredentialsException {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : password)
            stringBuilder.append(c);

        String saltedHash = CryptographicUtility.getInstance().getSaltedPasswordHash(stringBuilder.toString());
        UserAccount acc = db.getUserAccount(username, saltedHash);

        if (acc == null)
            throw new WrongLoginCredentialsException();

        currentUser = acc;

        associatedMember = db.getMemberFromAccountUsername(acc.getUsername());

        if (associatedMember == null)
            throw new DatabaseException("Das zugehörige Mitglied zum Account wurde nicht gefunden");

        db.submitLoginLogEntry(currentUser.getID(), true);
    }

    public void logoutUser() throws DatabaseException {
        try {
            db.submitLoginLogEntry(currentUser.getID(), false);
        } catch (DatabaseException e) {
            throw new DatabaseException("Der Benutzer konnte nicht ausgeloggt werden.");
        }
        TerminalPage.setLocked(true);
        currentUser = null;
    }

    public String getCurrentUsername() {
        if (currentUser == null)
            return null;

        return currentUser.getUsername();
    }

    public UserAccount getCurrentUser() {
        return currentUser;
    }

    public void exit() {
        try {
            if (currentUser != null)
                logoutUser();
        } catch (DatabaseException e) {
        }

        System.exit(0);
    }

    public void changeUsername(String newUsername) throws DisplayableException, DatabaseException {
        if (newUsername.equals("") ||
                newUsername.equals(currentUser.getUsername())) {
            return;
        }

        var usernames = db.getAllUsernames();

        if (usernames.contains(newUsername))
            throw new UsernameAlreadyExistsException(newUsername);

        db.changeUsername(currentUser.getUsername(), newUsername);

        currentUser.setUsername(newUsername);
    }

    public void changePassword(String oldPassword, String newPassword, String newPasswordConfim) throws DatabaseException, OldPasswordDoesNotMatchException, PasswordsDoNotMatchException, PasswordEmptyException {
        var oldPasswordHash = CryptographicUtility.getInstance().getSaltedPasswordHash(oldPassword);

        if (oldPassword.equals(""))
            return;

        if (!oldPasswordHash.equals(currentUser.getPasswordHash()))
            throw new OldPasswordDoesNotMatchException();

        if (!newPassword.equals(newPasswordConfim))
            throw new PasswordsDoNotMatchException();

        if (newPassword.equals(""))
            throw new PasswordEmptyException();

        var newPasswordHash = CryptographicUtility.getInstance().getSaltedPasswordHash(newPassword);

        db.changePasswordHash(currentUser.getUsername(), newPasswordHash);

        currentUser.setPasswordHash(newPasswordHash);
    }

    public ResultSet executeQuery(String... query) throws DatabaseException {
        return db.executeFetchResultSet(query);
    }

    public int executeAny(String... query) throws DatabaseException {
        return db.executeFetchAffectedRows(query);
    }

    public ArrayList<FilmShowing> getActiveFilmShowings() throws DatabaseException {
        return db.getActiveFilmShowings();
    }

    public void refreshFilmShowingEntries(FilmShowing filmShowing) {
        var entries = new ArrayList<FilmShowingEntry>();

        // entries = db.getFilmShowingEntries(filmShowing.getID());


    }

}
