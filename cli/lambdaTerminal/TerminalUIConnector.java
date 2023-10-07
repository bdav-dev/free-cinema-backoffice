package cli.lambdaTerminal;

import java.util.ArrayList;

/**
 * @author David Berezowski
 * @version 1.0
 */
public interface TerminalUIConnector {
    void initProcessors();

    void processInput(ArrayList<String> userInput);

    void rotate();

    void returnToMainProcessor();
}