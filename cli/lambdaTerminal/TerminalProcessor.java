package cli.lambdaTerminal;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author David Berezowski
 * @version 1.0
 */
public abstract class TerminalProcessor {
    private Terminal terminal;
    private boolean neverShown;
    private ArrayList<Command> commands;

    private String identifier;
    private String toolname;

    private String consoleStatus;
    private String consoleContent;

    public TerminalProcessor(String toolname, String identifer) {
        this.toolname = toolname;
        this.identifier = identifer;

        commands = new ArrayList<>();
        neverShown = true;
    }

    public TerminalProcessor(String toolname) {
        this(toolname, null);
    }

    public abstract void init();

    public void show() {
        terminal.getTerminalWindow().setConsoleText(consoleContent);
        terminal.getTerminalWindow().setTopRightText(consoleStatus);
        terminal.updateProcessorLabel();
        terminal.getTerminalWindow().updateTopTextSize();

        if (neverShown) {
            neverShown = false;
            firstAppearance();
        }
    }

    public void shift() {
        consoleContent = terminal.getTerminalWindow().getConsoleText();
    }

    public ExecutionStatus runCommands(ArrayList<String> userInput) {
        ExecutionStatus status = ExecutionStatus.NO_MATCH;

        for (Command command : commands) {
            ExecutionStatus currentStatus = command.tryToExecute(userInput);

            if (currentStatus == ExecutionStatus.EXECUTED) {
                return currentStatus;
            }

            status = ExecutionStatus.returnHigher(status, currentStatus);
        }

        return status;
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void clearCommands() {
        commands.clear();
    }

    public void addCommands(Command... commands) {
        for (Command c : commands) {
            this.commands.add(c);
        }
    }

    public void addCommands(ArrayList<Command> commands) {
        this.commands.addAll(commands);
    }

    public void processUserInput(ArrayList<String> userInput) {
        switch (runCommands(userInput)) {
            case ARGUMENT_LIST_NOT_MATCHING:
                getTerminal().getTerminalWindow().log("argument list of command »" + userInput.get(0) + "« does not match with user input");
                break;

            case NO_MATCH:
                getTerminal().getTerminalWindow().log("command »" + userInput.get(0) + "« does not exist");
                break;

            case NOT_ACTIVE:
                getTerminal().getTerminalWindow().log("command »" + userInput.get(0) + "« is not active");
                break;

            default:
                break;
        }
    }

    public void firstAppearance() {

    }

    public Terminal getTerminal() {
        return terminal;
    }

    protected void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getToolname() {
        return toolname;
    }

    public void printHelp() {
        getTerminal().getTerminalWindow().list("All commands from " + getToolname() + ": " + getIdentifier(), getHelpLines(), '|', false);
    }

    public String[] getHelpLines() {
        return getHelpLines(commands);
    }

    public String[] getHelpLines(ArrayList<Command> commands) {
        char consoleDelimiter = getTerminal().getTerminalWindow().getDivider();
        String argumentWildcard = "... ";
        int wrapAt = 50; // Amount of characters per line until, after the next space, a line break is done.

        int amountOfCommands = (int) commands.stream().filter(c -> c.showInHelp()).count();

        String[][] helpAsArray = new String[2][amountOfCommands];

        for (int i = 0; i < commands.size(); i++) {
            Command c = commands.get(i);
            if (!c.showInHelp())
                continue;

            helpAsArray[0][i] = String.join(String.valueOf(consoleDelimiter), c.getDefaultArguments()) + consoleDelimiter + argumentWildcard.repeat(c.getExpectedNumberOfArguments());
            helpAsArray[1][i] = c.getDescription();
        }

        return buildListFromTable(helpAsArray, wrapAt);
    }

    protected String[] buildListFromTable(String[][] table, int wrapAt) {
        ArrayList<String> output = new ArrayList<>();

        StringBuilder b = new StringBuilder();

        for (int i = 0; i < table[0].length; i++) {
            String s = table[0][i];
            if (s.length() == 0)
                continue;
            if (s.charAt(s.length() - 1) != ' ')
                table[0][i] += " ";
        }

        int width = Arrays.stream(table[0]).mapToInt((s) -> s.length()).max().orElse(0);

        for (int i = 0; i < table[0].length; i++) {
            b.append(table[0][i]);

            if (table[1][i] == null) {
                output.add(b.toString());
                b = new StringBuilder();
                continue;
            }

            b.append(" ".repeat(width - table[0][i].length()));
            b.append("— ");

            String[] descriptionLines = splitByLength(table[1][i], wrapAt);
            b.append(descriptionLines[0]);

            output.add(b.toString());
            b = new StringBuilder();

            for (int lineIndex = 1; lineIndex < descriptionLines.length; lineIndex++) {
                b.append(" ".repeat(width + 2));
                b.append(descriptionLines[lineIndex]);

                output.add(b.toString());
                b = new StringBuilder();
            }
        }

        return output.toArray(String[]::new);
    }

    private String[] splitByLength(String string, int splitAt) {
        ArrayList<String> fragments = new ArrayList<>();

        StringBuilder currentFragment = new StringBuilder();
        boolean splitNext = false;

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);

            if (currentFragment.length() == splitAt)
                splitNext = true;

            if (splitNext && c == ' ' || c == '\n') {
                fragments.add(currentFragment.toString());
                currentFragment = new StringBuilder();
                splitNext = false;
            } else {
                currentFragment.append(c);
            }
        }

        if (!currentFragment.isEmpty()) {
            fragments.add(currentFragment.toString());
        }

        return fragments.toArray(String[]::new);
    }

}
