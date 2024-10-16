package cli;

import appsettings.AppSettings;
import cli.lambdaTerminal.CommandBuilder;
import cli.lambdaTerminal.TerminalProcessor;
import cli.lambdaTerminal.theme.Theme;
import cli.lambdaTerminal.theme.Theme.Themes;
import fcbo.FreeCinemaBackoffice;
import localstorage.LocalStorage;
import utility.CryptographicUtility;
import pvt.PrivateKeys;

public class MainProcessor extends TerminalProcessor {

    FreeCinemaBackoffice fcbo = FreeCinemaBackoffice.getInstance();

    public MainProcessor(String toolname) {
        super(toolname);
    }

    public MainProcessor(String toolname, String identifier) {
        super(toolname, identifier);
    }

    @Override
    public void init() {
        addCommand(CommandBuilder.newCommand()
                .defaultArguments("theme", "list")
                .expectedNumberOfArguments(0)
                .action(args -> listThemes())
                .description("Lists all available terminal themes.")
                .build());

        addCommand(CommandBuilder.newCommand()
                .defaultArguments("theme")
                .expectedNumberOfArguments(1)
                .action(args -> changeThemeByName(args[0]))
                .description("Changes the terminal theme.\nFirst argument: Name of theme")
                .build());

        addCommand(CommandBuilder.newCommand()
                .defaultArguments("pwhash")
                .expectedNumberOfArguments(1)
                .action(args -> getTerminal().getTerminalWindow().log(CryptographicUtility.getInstance().getSaltedPasswordHash(args[0])))
                .description("Returns the hash of the given input")
                .build());

        addCommand(CommandBuilder.newCommand()
                .defaultArguments("localStorage", "set")
                .expectedNumberOfArguments(2)
                .action(args -> {
                    LocalStorage.getInstance().set(args[0], args[1]);
                    getTerminal().getTerminalWindow().log("Set.");
                })
                .description("LocalStorage API: set(key, value)")
                .build());

        addCommand(CommandBuilder.newCommand()
                .defaultArguments("localStorage", "get")
                .expectedNumberOfArguments(1)
                .action(args -> {
                    getTerminal().getTerminalWindow().log(LocalStorage.getInstance().get(args[0]).orElse("No value present."));
                })
                .description("LocalStorage API: get(key)")
                .build());

        addCommand(CommandBuilder.newCommand()
                .defaultArguments("database", "useDefDbCred")
                .expectedNumberOfArguments(0)
                .action(args -> {
                    setDefaultDatabaseCredentials();
                    getTerminal().getTerminalWindow().log("Default database credentials have been set.");
                })
                .description("Loads the default database credentials to local storage.")
                .build());

        addCommand(CommandBuilder.newCommand()
                .defaultArguments("mandt")
                .expectedNumberOfArguments(0)
                .action(args -> {
                    getTerminal().getTerminalWindow().log(
                            String.valueOf(AppSettings.getInstance().mandt().get()));
                })
                .description("Displays the application mandt.")
                .build());

        addCommand(CommandBuilder.newCommand()
                .defaultArguments("mandt", "set")
                .expectedNumberOfArguments(1)
                .action(args -> {
                    AppSettings.getInstance().mandt().setStringOnlySavingToLocalStorage(args[0]);

                    getTerminal().getTerminalWindow().log(
                            "Saved mandt " + AppSettings.getInstance().mandt().getValueInLocalStorage() + " to LocalStorage. Restart required.");
                })
                .description("Displays the application mandt.")
                .build());

        addCommand(CommandBuilder.newCommand()
                .defaultArguments("uidebug")
                .expectedNumberOfArguments(0)
                .action(args -> {
                    getTerminal().getTerminalWindow().log(
                            String.valueOf(AppSettings.getInstance().uidebug().get()));
                })
                .description("f")
                .build());

        addCommand(CommandBuilder.newCommand()
                .defaultArguments("uidebug", "set")
                .expectedNumberOfArguments(1)
                .action(args -> {
                    AppSettings.getInstance().uidebug().setStringOnlySavingToLocalStorage(args[0]);

                    getTerminal().getTerminalWindow().log(
                            "Saved uidebug " + AppSettings.getInstance().uidebug().getValueInLocalStorage() + " to LocalStorage. Restart required.");
                })
                .description("d")
                .build());
    }



    private void listThemes() {
        String[] themeNames = new String[Themes.values().length];

        for (int i = 0; i < themeNames.length; i++) {
            themeNames[i] = Themes.values()[i].toString();
        }

        getTerminal().getTerminalWindow().list("Available Themes:", themeNames, false);
    }

    private void changeThemeByName(String themeName) {
        for (Themes theme : Themes.values()) {
            if (theme.toString().toUpperCase().equals(themeName.toUpperCase())) {
                if (theme == Theme.getCurrentThemeAsEnumType()) {
                    getTerminal().getTerminalWindow().log("Theme " + theme + " is already selected.");
                    return;
                }
                getTerminal().getTerminalWindow().changeTheme(theme);
                getTerminal().getTerminalWindow().log("Successfully changed to " + theme + " theme");
                return;
            }
        }
        getTerminal().getTerminalWindow().log("There is no theme with the name " + themeName);
    }

    private void setDefaultDatabaseCredentials() {
        AppSettings.getInstance().database().setAll(
                PrivateKeys.DB_DATABASE,
                PrivateKeys.DB_HOST,
                PrivateKeys.DB_PORT,
                PrivateKeys.DB_USERNAME,
                PrivateKeys.DB_PASSWORD);
    }
}
