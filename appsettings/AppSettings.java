package appsettings;

import appsettings.subsettings.Database;
import appsettings.subsettings.DefaultValues;
import appsettings.subsettings.LayoutCorrections;

public class AppSettings {

    private static final AppSettings instance = new AppSettings();

    // Global settings
    private LocalStorageSetting<Integer> mandt;
    private LocalStorageSetting<Boolean> uidebug;

    private AppSettings() {
        mandt = new LocalStorageSetting<Integer>(
            "application_mandt",
            701,
            FromString.Primitives.INTEGER);

        uidebug = new LocalStorageSetting<Boolean>(
            "ui_debug",
            false,
            FromString.Primitives.BOOLEAN);
    }

    public static AppSettings getInstance() {
        return instance;
    }

    // Global settings
    public LocalStorageSetting<Integer> mandt() {
        return mandt;
    }

    public LocalStorageSetting<Boolean> uidebug() {
        return uidebug;
    }

    // Subsettings
    public Database database() {
        return Database.getInstance();
    }

    public DefaultValues defaultValues() {
        return DefaultValues.getInstance();
    }

    public LayoutCorrections layoutCorrections() {
        return LayoutCorrections.getInstance();
    }

}
