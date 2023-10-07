package cli.lambdaTerminal;

import java.util.function.Consumer;

/**
 * @author David Berezowski
 * @version 1.0
 */
public class CommandBuilder {
    Command command;

    private CommandBuilder() {
    command = new Command();
    }

    public static CommandBuilder newCommand() {
    return new CommandBuilder();
    }

    public CommandBuilder description(String description) {
    command.setDescription(description);
    return this;
    }

    public CommandBuilder defaultArguments(String... defaultArguments) {
    command.setDefaultArguments(defaultArguments);
    return this;
    }

    public CommandBuilder expectedNumberOfArguments(int expectedNumberOfArguments) {
    command.setExpectedNumberOfArguments(expectedNumberOfArguments);
    return this;
    }

    public CommandBuilder action(Consumer<String[]> args) {
    command.setAction(args);
    return this;
    }
    
    public CommandBuilder active(boolean active) {
    command.setActive(active);
    return this;
    }
    
    public CommandBuilder showInHelp(boolean showInHelp) {
    command.setShowInHelp(showInHelp);
    return this;
    }

    public Command build() {
    if(command.getDefaultArguments() == null) {
        throw new InvalidCommandException("Default arguments not set.");
    }

    if(command.getAction() == null) {
        throw new InvalidCommandException("Action not set.");
    }

    return command;
    }

    @SuppressWarnings("serial")
    public static class InvalidCommandException extends RuntimeException {
    public InvalidCommandException() {
        super();
    }

    public InvalidCommandException(String message) {
        super(message);
    }
    }

}
