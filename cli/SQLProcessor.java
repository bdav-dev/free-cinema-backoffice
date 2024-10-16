package cli;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import cli.lambdaTerminal.Command;
import cli.lambdaTerminal.CommandBuilder;
import cli.lambdaTerminal.TerminalProcessor;
import cli.lambdaTerminal.TerminalWindow;
import db.Database;
import db.Database.DatabaseCredentials;
import db.utility.HasDatabaseAccess;
import exceptions.DatabaseException;
import utility.Concurrency;

public class SQLProcessor extends TerminalProcessor implements HasDatabaseAccess {

    private boolean editMode;

    private String defaultModeText;

    private ArrayList<Command> defaultModeCommands, editModeCommands;

    private HashMap<String, String> savedQueries;
    private String latestQuery;

    private int spaceCountOnNewLine = 2;

    private enum StatementType {
        QUERY, ANY;
    }

    public SQLProcessor(String toolname) {
        this(toolname, null);
    }

    public SQLProcessor(String toolname, String identifier) {
        super(toolname, identifier);
        editMode = false;
        latestQuery = null;
        defaultModeCommands = new ArrayList<>();
        editModeCommands = new ArrayList<>();
        defaultModeText = "";
        savedQueries = new HashMap<>();
    }

    @Override
    public void init() {
        defaultModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("editor", "load", "last")
                .expectedNumberOfArguments(0)
                .action((args) -> loadLatestQuery())
                .description("Loads the last executed query and enters editor mode.")
                .build());

        defaultModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("editor", "load")
                .expectedNumberOfArguments(1)
                .action((args) -> loadQueryByName(args[0]))
                .description("Loads a saved query and enters editor mode.\nFirst argument: Name of query")
                .build());

        defaultModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("editor", "new")
                .expectedNumberOfArguments(0)
                .action((args) -> switchToEditMode())
                .description("Opens blank editor to write a new query.")
                .build());

        defaultModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("exe", "last")
                .expectedNumberOfArguments(0)
                .action((args) -> executeLatest())
                .description("Directly executes the last executed query.")
                .build());

        defaultModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("exe", "d")
                .expectedNumberOfArguments(2)
                .action((args) -> executeDirectly(args))
                .description("Directly executes query and saves it under the specified name.\nFirst argument: Query\nSecond argument: Name of SQL-Statement (for save)")
                .build());

        defaultModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("exe", "d")
                .expectedNumberOfArguments(1)
                .action((args) -> executeDirectly(args))
                .description("Directly executes query.\nFirst argument: SQL-Statement")
                .build());

        defaultModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("exe")
                .expectedNumberOfArguments(1)
                .action((args) -> executeFromStorage(args[0]))
                .description("Executes query from storage.\nFirst argument: Name of saved query")
                .build());

        defaultModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("list")
                .expectedNumberOfArguments(0)
                .action((args) -> listQueries())
                .description("Lists the saved queries in storage")
                .build());

        defaultModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("dbcon")
                .expectedNumberOfArguments(0)
                .action(args -> {
                    listConnection();
                })
                .description("Lists information about the database connection")
                .build());

        editModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("load", "last")
                .expectedNumberOfArguments(0)
                .action((args) -> loadLatestQuery())
                .description("Loads the last executed query and discards current text in editor.")
                .build());

        editModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("load")
                .expectedNumberOfArguments(1)
                .action((args) -> loadQueryByName(args[0]))
                .description("Loads a saved query and discards current text in editor.\nFirst argument: Name of query")
                .build());

        editModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("exe")
                .expectedNumberOfArguments(0)
                .action((args) -> executeQuery())
                .description("Returns to default terminal mode and executes the current command.")
                .build());

        editModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("save")
                .expectedNumberOfArguments(1)
                .action((args) -> saveQuery(args[0]))
                .description("Saves the current query in the editor.\nFirst argument: Name of saved query")
                .build());

        editModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("new")
                .expectedNumberOfArguments(0)
                .action((args) -> {
                    getTerminal().getTerminalWindow().clear();
                    getTerminal().getTerminalWindow().switchFocus();
                })
                .description("Deletes all text in editor.")
                .build());

        editModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("discard")
                .expectedNumberOfArguments(0)
                .action((args) -> switchToDefaultMode())
                .description("Discards current text in editor and returns to default terminal mode.")
                .build());

        editModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("return")
                .expectedNumberOfArguments(0)
                .action((args) -> switchToDefaultMode())
                .description("Discards current text in editor and returns to default terminal mode.")
                .build());

        editModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("back")
                .expectedNumberOfArguments(0)
                .action((args) -> switchToDefaultMode())
                .description("Discards current text in editor and returns to default terminal mode.")
                .build());

        editModeCommands.add(CommandBuilder.newCommand()
                .defaultArguments("exit")
                .expectedNumberOfArguments(0)
                .action((args) -> switchToDefaultMode())
                .description("Discards current text in editor and returns to default terminal mode.")
                .build());

        switchToDefaultMode();
    }

    private void listQueries() {
        if (savedQueries.size() == 0) {
            getTerminal().getTerminalWindow().log("No queries saved yet.");
            return;
        }

        Set<String> savedQueriesKeySet = savedQueries.keySet();

        String[][] table = new String[2][savedQueriesKeySet.size()];

        int i = 0;
        for (String key : savedQueriesKeySet) {
            table[0][i] = key;
            table[1][i] = savedQueries.get(key);
            i++;
        }

        getTerminal().getTerminalWindow().list("Saved queries", buildListFromTable(table, 40), '|', false);
    }

    private void listConnection() {
        DatabaseCredentials c = Database.getInstance().getConnectionInfo();
        var list = new String[] {
                "Host: " + c.host(),
                "Port: " + c.port(),
                "Database: " + c.database(),
                "Username: " + c.username(),
                "Password: " + c.password()
        };

        getTerminal().getTerminalWindow().list("Database credentials", list, false);
    }

    private void executeFromStorage(String name) {
        String query = savedQueries.get(name);

        if (query == null) {
            getTerminal().getTerminalWindow().log("No query under this name found.");
            return;
        }

        executeSqlStatement(query);
    }

    private void executeDirectly(String[] args) {
        executeSqlStatement(args[0]);

        if (args.length >= 2)
            saveQuery(args[2], args[1]);
    }

    private void saveQuery(String query, String name) {
        savedQueries.put(name, query);
    }

    private void saveQuery(String name) {
        saveQuery(getTerminal().getTerminalWindow().getConsoleText(), name);
    }

    private void loadLatestQuery() {
        if (latestQuery == null) {
            getTerminal().getTerminalWindow().log("There is no latest query.");
            return;
        }

        loadQuery(latestQuery);
    }

    private void loadQuery(String queryName) {
        if (queryName != null) {
            if (!editMode)
                switchToEditMode();
            getTerminal().getTerminalWindow().setConsoleText(queryName);
        } else {
            getTerminal().getTerminalWindow().log("No query under this name found.");
        }
    }

    private void loadQueryByName(String name) {
        loadQuery(savedQueries.get(name));

    }

    private void executeQuery() {
        executeSqlStatement(getTerminal().getTerminalWindow().getConsoleText());
    }

    private void executeLatest() {
        if (latestQuery == null || latestQuery.equals("")) {
            getTerminal().getTerminalWindow().log("There is no latest query.");
            return;
        }

        executeSqlStatement(latestQuery);
    }

    private String transformToSingleLineQuery(String query) {
        String[] splittedQuery = query.split("\n");

        for (int i = 0; i < splittedQuery.length; i++)
            splittedQuery[i] = splittedQuery[i].trim();

        return String.join(" ", splittedQuery);
    }

    private void executeSqlStatement(String query) {
        if (query == null || query.equals(""))
            return;

        latestQuery = query;

        String singleLineQuery = transformToSingleLineQuery(query);

        if (editMode)
            switchToDefaultMode();

        StatementType statementType = singleLineQuery.toLowerCase().startsWith("select")
                ? StatementType.QUERY
                : StatementType.ANY;

        Concurrency.async(() -> {
            ResultSet resultSet = null;
            int affectedRows;

            try {

                if (statementType == StatementType.QUERY) {
                    resultSet = database().executeFetchResultSet(singleLineQuery);
                    printTable(resultSet);
                } else {
                    affectedRows = database().executeFetchAffectedRows(singleLineQuery);
                    printAffectedRows(affectedRows);
                }

            } catch (DatabaseException | SQLException dbException) {
                getTerminal().getTerminalWindow().list("While trying to executing following query, an error occured.", query.split("\n"), '│', false);
                getTerminal().getTerminalWindow().println("  " + dbException.getMessage());
                return;
            } finally {
                try {
                    if (statementType == StatementType.QUERY)
                        Database.getInstance().cleanup(resultSet);
                } catch (DatabaseException e) {
                    getTerminal().getTerminalWindow().list("While trying to executing following query, an error occured.", query.split("\n"), '│', false);
                    getTerminal().getTerminalWindow().println("  " + e.getMessage());
                }
            }
        });

    }

    private void printAffectedRows(int affectedRows) {
        getTerminal().getTerminalWindow().list("Your query", latestQuery.split("\n"), '│', false);
        getTerminal().getTerminalWindow().println("  affected " + affectedRows + " rows.");
    }

    private void switchToEditMode() {
        editMode = true;
        defaultModeText = getTerminal().getTerminalWindow().getConsoleText();
        getTerminal().getTerminalWindow().clear();
        getTerminal().getTerminalWindow().setConsoleEditable(true);
        getTerminal().getTerminalWindow().setSupressMessages(true);
        getTerminal().getTerminalWindow().setConsoleCaretVisible(true);
        getTerminal().getTerminalWindow().switchFocus();
        clearCommands();
        addCommands(editModeCommands);
    }

    private void switchToDefaultMode() {
        editMode = false;
        getTerminal().getTerminalWindow().setConsoleText(defaultModeText);
        getTerminal().getTerminalWindow().setConsoleEditable(false);
        getTerminal().getTerminalWindow().setSupressMessages(false);
        getTerminal().getTerminalWindow().setConsoleCaretVisible(false);
        clearCommands();
        addCommands(defaultModeCommands);
    }

    private void printTable(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        ArrayList<String[]> table = new ArrayList<>();

        int columns = metaData.getColumnCount();
        String[] columnNames = new String[columns];
        int[] maxLengths = new int[columns];

        for (int i = 0; i < columns; i++)
            columnNames[i] = metaData.getColumnLabel(i + 1);

        table.add(columnNames);

        while (resultSet.next()) {
            String[] dataset = new String[columns];

            for (int i = 0; i < columns; i++)
                dataset[i] = resultSet.getString(i + 1).replaceAll("[\\t\\n\\r]+", " ");

            table.add(dataset);
        }

        for (int i = 0; i < columns; i++) {
            final int index = i;
            maxLengths[i] = table.stream().mapToInt(line -> line[index].length()).max().orElse(0);
        }

        //////////////

        boolean printIndices = true;
        char horizontalDivider = '│';
        StringBuilder line = new StringBuilder();

        ArrayList<String> output = new ArrayList<>();

        int maxRowNumberDigits = String.valueOf(table.size() - 1).length();

        int[] tableFormat;

        if (printIndices)
            tableFormat = new int[columns + 2];
        else
            tableFormat = new int[columns + 1];

        tableFormat[0] = 0;
        int counter = 0;
        int tableFormatIndexAddition = 1;

        if (printIndices) {
            tableFormat[1] = 3 + maxRowNumberDigits;
            counter = tableFormat[1];
            tableFormatIndexAddition = 2;
        }

        for (int i = 0; i < columns; i++) {
            counter += 3 + maxLengths[i];
            tableFormat[i + tableFormatIndexAddition] = counter;
        }

        line.append(" ".repeat(spaceCountOnNewLine));

        if (printIndices) {
            line.append(horizontalDivider);
            line.append(" ".repeat(maxRowNumberDigits + 2));
        }

        line.append(horizontalDivider);
        for (int i = 0; i < columns; i++) {
            line.append(" ");
            line.append(columnNames[i]);
            line.append(" ".repeat(maxLengths[i] - columnNames[i].length() + 1));
            line.append(horizontalDivider);
        }

        output.add(getHorizontalDivider(tableFormat, 0));
        output.add(line.toString());
        output.add(getHorizontalDivider(tableFormat, 1));


        for (int i = 1; i < table.size(); i++) {
            String[] entry = table.get(i);

            line = new StringBuilder();
            line.append(" ".repeat(spaceCountOnNewLine));

            if (printIndices) {
                line.append(horizontalDivider + " ");
                line.append(i);
                line.append(" ".repeat(maxRowNumberDigits - String.valueOf(i).length() + 1));
            }

            line.append(horizontalDivider);
            for (int a = 0; a < columns; a++) {
                line.append(" ");
                line.append(entry[a]);
                line.append(" ".repeat(maxLengths[a] - entry[a].length() + 1));
                line.append(horizontalDivider);
            }
            output.add(line.toString());
        }

        output.add(getHorizontalDivider(tableFormat, 2));

        getTerminal().getTerminalWindow().list("Your query", latestQuery.split("\n"), '│', false);
        getTerminal().getTerminalWindow().println("  resulted in following table:");

        TerminalWindow w = getTerminal().getTerminalWindow();
        for (String outputLine : output) {
            w.println(outputLine);
        }

    }

    private String getHorizontalDivider(int[] formatting, int pos) {
        // pos =
        // 0 - Top
        // 1 - Middle
        // 2 - Bottom

        int length = formatting[formatting.length - 1];

        StringBuilder divider = new StringBuilder();

        char left;
        char middle;
        char right;
        char def = '─';

        switch (pos) {
            case 0:
                left = '┌';
                middle = '┬';
                right = '┐';
                break;
            case 2:
                left = '└';
                middle = '┴';
                right = '┘';
                break;
            default:
                left = '├';
                middle = '┼';
                right = '┤';
                break;
        }

        divider.append(" ".repeat(spaceCountOnNewLine));
        divider.append(left);

        int lookAtIndex = 1;
        for (int i = 1; i < length; i++) {

            if (i == formatting[lookAtIndex]) {
                divider.append(middle);
                lookAtIndex++;
            } else {
                divider.append(def);
            }

        }

        divider.append(right);

        return divider.toString();
    }

    @Override
    public void printHelp() {
        String[] helpForEditMode = getHelpLines(editModeCommands);
        String[] helpForDefaultMode = getHelpLines(defaultModeCommands);

        String commonTitle = "All commands from " + getToolname() + ": " + getIdentifier();

        getTerminal().getTerminalWindow().list(commonTitle + " - Default", helpForDefaultMode, '│', false);
        getTerminal().getTerminalWindow().list(commonTitle + " - Editor", helpForEditMode, '│', false);
    }

    @Override
    public void shift() {
        if (editMode) {
            getTerminal().getTerminalWindow().setConsoleEditable(false);
            getTerminal().getTerminalWindow().setSupressMessages(false);
            getTerminal().getTerminalWindow().setConsoleCaretVisible(false);
        }
        super.shift();
    }

    @Override
    public void show() {
        if (editMode) {
            getTerminal().getTerminalWindow().setConsoleEditable(true);
            getTerminal().getTerminalWindow().setSupressMessages(true);
            getTerminal().getTerminalWindow().setConsoleCaretVisible(true);
        }
        super.show();
    }

}
