package fcbo;

import appsettings.AppSettings;
import exceptions.DatabaseException;
import free_ui.UI;
import services.LoginService;
import free_ui.Page;
import ui.pages.LoginPage;
import utility.CryptographicUtility;

public class FreeCinemaBackoffice {
    private UI ui;

    public static final String VERSION = "1.0dev";

    private static FreeCinemaBackoffice instance;

    private FreeCinemaBackoffice() {
        ui = UI.getInstance();
        // Page.setShift(0, 0, -13, -38); // Settings for Ubuntu
    }

    public static FreeCinemaBackoffice getInstance() {
        if (instance == null)
            instance = new FreeCinemaBackoffice();

        return instance;
    }

    public void start() {
        Page.setShift(
                AppSettings.getInstance().layoutCorrections().x().get(),
                AppSettings.getInstance().layoutCorrections().y().get(),
                AppSettings.getInstance().layoutCorrections().width().get(),
                AppSettings.getInstance().layoutCorrections().height().get());

        ui.launchPanel(() -> new LoginPage());

        System.out.println(CryptographicUtility.getInstance().getSaltedPasswordHash("return"));
    }

    public void exit() {
        try {
            if (LoginService.getInstance().isUserLoggedIn())
                LoginService.getInstance().logoutUser();

        } catch (DatabaseException e) {
        }

        System.exit(0);
    }

}
