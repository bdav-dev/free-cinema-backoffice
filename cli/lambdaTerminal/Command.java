package cli.lambdaTerminal;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * @author David Berezowski
 * @version 1.0
 */
public class Command {
    private int expectedNumberOfArguments;
    private String description;
    private String[] defaultArguments;
    private Consumer<String[]> action;
    private boolean active;
    private boolean showInHelp;

    protected Command() {
        active = true;
        showInHelp = true;
        expectedNumberOfArguments = -1;
    }

    public ExecutionStatus tryToExecute(ArrayList<String> args) {
        ArrayList<String> localArgs = new ArrayList<String>();
        args.forEach(s -> localArgs.add(s));

        int numberOfArguments = args.size();
        int defaultNumberOfArguments = defaultArguments.length;

        if (numberOfArguments < defaultNumberOfArguments) {
            return ExecutionStatus.NO_MATCH;
        }

        for (int i = 0; i < defaultArguments.length; i++) {
            if (!defaultArguments[i].equals(args.get(i))) {
                return ExecutionStatus.NO_MATCH;
            } else {
                localArgs.remove(0);
            }
        }

        if (expectedNumberOfArguments != localArgs.size() && expectedNumberOfArguments != -1) {
            return ExecutionStatus.ARGUMENT_LIST_NOT_MATCHING;
        }

        if (!active) {
            return ExecutionStatus.NOT_ACTIVE;
        }

        execute(localArgs.toArray(String[]::new));
        return ExecutionStatus.EXECUTED;
    }

    public String getDescription() {
        return description;
    }

    public void execute(String[] args) {
        action.accept(args);
    }

    protected void setExpectedNumberOfArguments(int expectedNumberOfArguments) {
        this.expectedNumberOfArguments = expectedNumberOfArguments;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected void setDefaultArguments(String[] defaultArguments) {
        this.defaultArguments = defaultArguments;
    }

    protected void setAction(Consumer<String[]> action) {
        this.action = action;
    }

    public int getExpectedNumberOfArguments() {
        return expectedNumberOfArguments;
    }

    public String[] getDefaultArguments() {
        return defaultArguments;
    }

    public Consumer<String[]> getAction() {
        return action;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean showInHelp() {
        return showInHelp;
    }

    public void setShowInHelp(boolean showInHelp) {
        this.showInHelp = showInHelp;
    }
}
