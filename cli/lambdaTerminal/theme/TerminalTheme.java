package cli.lambdaTerminal.theme;

import java.awt.Color;

public interface TerminalTheme {
    Color panelBackgroundColor();
    Color consoleBackgroundColor();
    Color commandLineBackgroundColor();
    Color textColor();
    
    Color scrollBarThumbColor();
    Color scrollBarTrackColor();
}
