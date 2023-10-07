package cli;

import cli.lambdaTerminal.Terminal;
import cli.lambdaTerminal.TerminalWindow;
import free_ui.Page;

public class TerminalPage {

    private static Terminal instance;
    private static boolean locked = true;

    private TerminalPage() {

    }

    public static void setLocked(boolean locked) {
        TerminalPage.locked = locked;

        if (instance != null) {
            instance.setLocked(locked);
        }
    }

    public static Page getTerminalPanel() {
        if (instance == null)
            initTerminal();

        return instance.getTerminalWindow();
    }

    public static boolean isInitialized() {
        return instance != null;
    }

    public static void deinitialize() {
        instance = null;
    }

    private static void initTerminal() {
        instance = new Terminal(new TerminalWindow(), new MainProcessor("Main", "main"), new SQLProcessor("SQL", "sql")) {
            @Override
            public String getWelcomeMessage() {
                return "FREE CINEMA Backoffice CLI V.1.0";
            }
        };
        instance.typeIdentifierToAccessProcessorMode(true);
        instance.singleProcessorMode(false);
        instance.setLocked(locked);
        instance.launch();
    }

}
