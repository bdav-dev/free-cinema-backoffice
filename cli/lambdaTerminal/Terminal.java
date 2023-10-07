package cli.lambdaTerminal;

import java.util.ArrayList;
import java.util.Optional;


import fcbo.FCBO;
import utility.CryptographicUtility;

/**
 * @author David Berezowski
 * @version 1.0
 */
public class Terminal implements TerminalUIConnector {
    private TerminalProcessor currentProcessor;
    private ArrayList<TerminalProcessor> processors;

    private TerminalProcessor internalCommandProcessor;

    private TerminalWindow window;

    private boolean typeIdentifierToAccessProcessorMode;

    private TerminalProcessor mainProcessor;
    private TerminalProcessor[] otherProcessors;

    private boolean locked = true;
    private String lastCommand;

    private String loginPasswordHash = "7be0e0e97de1ce33659b04c57f6ed0533d387acf680a1cc6ab778cc3b77d84752ca55c2b43e0f05ef5f82b08a15812ceea499c2c2d63b4a09d4fd0d7bfaf625e";

    /*
     * What does the user needs to set:
     * initial Processor (if none: use the main processor)
     * other ("pre") installed terminal processors
     * set internal commands
     */

    public Terminal(TerminalWindow window, TerminalProcessor mainProcessor, TerminalProcessor... otherProcessors) {
        processors = new ArrayList<>();

        internalCommandProcessor = new TerminalProcessor("") {
            @Override
            public void init() {
            }
        };
        internalCommandProcessor.setTerminal(this);
        addInternalCommands();

        this.mainProcessor = mainProcessor;
        this.otherProcessors = otherProcessors;

        this.window = window;
        window.setTerminalLogic(this);
        lastCommand = "";
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void launch() {
        window.launch();
        getTerminalWindow().log(getWelcomeMessage());
    }

    public void setInternalCommandProcessor(TerminalProcessor internalCommandProcessor) {
        this.internalCommandProcessor = internalCommandProcessor;
    }

    public void addInternalCommand(Command internalCommand) {
        internalCommandProcessor.addCommand(internalCommand);
    }

    public String getWelcomeMessage() {
        return getTerminalWindow().getName() + " " + getTerminalWindow().getVersion();
    }

    public void singleProcessorMode(boolean on) {
        window.setSingleProcessorMode(on);
    }

    public void typeIdentifierToAccessProcessorMode(boolean on) {
        typeIdentifierToAccessProcessorMode = on;
    }

    public void addInternalCommands() {
        internalCommandProcessor.addCommand(CommandBuilder.newCommand()
                .defaultArguments("help")
                .expectedNumberOfArguments(0)
                .action(args -> currentProcessor.printHelp())
                .showInHelp(false)
                .build());

        internalCommandProcessor.addCommand(CommandBuilder.newCommand()
                .defaultArguments("clear")
                .expectedNumberOfArguments(0)
                .action(args -> window.clear())
                .build());

        internalCommandProcessor.addCommand(CommandBuilder.newCommand()
                .defaultArguments("quit")
                .expectedNumberOfArguments(0)
                .action(args -> getTerminalWindow().onClose())
                .build());

        internalCommandProcessor.addCommand(CommandBuilder.newCommand()
                .defaultArguments("quit", "all")
                .expectedNumberOfArguments(0)
                .action(args -> FCBO.getInstance().exit())
                .build());

        internalCommandProcessor.addCommand(CommandBuilder.newCommand()
                .defaultArguments("last")
                .expectedNumberOfArguments(0)
                .action(args -> {
                    window.setCommandLineText(lastCommand);
                })
                .description("Recalls last executed command.")
                .build());
    }

    public void updateProcessorLabel() {
        if (currentProcessor == null)
            getTerminalWindow().setTopLeftText("");
        else
            getTerminalWindow().setTopLeftText(currentProcessor.getToolname() + ": " + currentProcessor.getIdentifier());

    }

    public boolean addTerminalProcessor(TerminalProcessor processor) {
        processor.setTerminal(this);

        if (processor.getIdentifier() == null || processor.getIdentifier().equals("")) {
            int number = 1;
            processor.setIdentifier(processor.getToolname().toLowerCase() + number);

            while (!addTerminalProcessor(processor)) {
                number++;
                processor.setIdentifier(processor.getToolname().toLowerCase() + number);
            }

            return true;
        }

        if (processors.stream().anyMatch(p -> p.getIdentifier().equals(processor.getIdentifier()))) { // if identifier is already present
            return false;
        }

        processors.add(processor);
        processor.init();
        return true;
    }

    @Override
    public void initProcessors() {
        currentProcessor = mainProcessor;
        addTerminalProcessor(currentProcessor);
        currentProcessor.show();

        for (TerminalProcessor otherProcessor : otherProcessors) {
            addTerminalProcessor(otherProcessor);
        }
    }

    @Override
    public void processInput(ArrayList<String> userInput) {
        if (userInput.size() == 0)
            return;

        ExecutionStatus status = internalCommandProcessor.runCommands(userInput);

        if (locked) {
            boolean loginTry = false;

            if (userInput.size() == 2 && userInput.get(0).equals("login")) {
                loginTry = true;

                if (CryptographicUtility.getInstance().getSaltedPasswordHash(userInput.get(1)).equals(loginPasswordHash)) {
                    locked = false;
                    window.log("Terminal unlocked.");
                } else {
                    window.log("Wrong password.");
                }
            }

            if (!loginTry)
                window.log("Terminal locked. Log in first using command 'login <password>'");

            window.setCommandLineText("");

            return;
        }

        if (typeIdentifierToAccessProcessorMode) {
            String identifier = userInput.get(0);

            Optional<TerminalProcessor> foundProcessor = processors.stream().filter(p -> p.getIdentifier().equals(identifier)).findFirst();

            if (foundProcessor.isPresent()) {
                changeCurrentProcessorTo(foundProcessor.get());
                window.setCommandLineText("");
                return;
            }

        }

        if (status != ExecutionStatus.EXECUTED) {
            currentProcessor.processUserInput(userInput);
        }

        // lastCommand = String.join(String.valueOf(getTerminalWindow().getDivider()), (CharSequence[]) userInput.toArray(String[]::new));
        lastCommand = window.getCommandLineText();

        if (!userInput.get(0).equals("last"))
            window.setCommandLineText("");
    }

    public void changeCurrentProcessorTo(int index) {
        changeCurrentProcessorTo(processors.get(index));
    }

    public void changeCurrentProcessorTo(TerminalProcessor processor) {
        currentProcessor.shift();
        currentProcessor = processor;
        currentProcessor.show();
    }

    @Override
    public void returnToMainProcessor() {
        if (currentProcessor != processors.get(0)) {
            changeCurrentProcessorTo(0);
        }
    }

    @Override
    public void rotate() {
        if (processors.size() == 1) {
            return;
        }

        int index = processors.indexOf(currentProcessor) + 1;

        if (index >= processors.size()) {
            index = 0;
        }

        changeCurrentProcessorTo(index);
    }

    public TerminalWindow getTerminalWindow() {
        return window;
    }



}