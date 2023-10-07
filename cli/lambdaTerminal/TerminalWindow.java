package cli.lambdaTerminal;

import javax.swing.*;

import cli.TerminalPage;
import cli.lambdaTerminal.theme.Theme;
import cli.lambdaTerminal.theme.Theme.Themes;
import free_ui.Page;
import free_ui.UIDesigner;
import free_ui.components.ScrollBarFactory;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * @author David Berezowski
 * @version 1.0
 */
public class TerminalWindow extends Page {
    private JTextArea console = new JTextArea();
    private JTextField commandLine = new JTextField();
    private JScrollPane consoleScrollPane = new JScrollPane(console);
    private JLabel leftTextLabel = new JLabel();
    private JLabel rightTextLabel = new JLabel();
    private JLabel terminalInSymbol = new JLabel();

    private final int defaultHeight = 480;
    private final int defaultWidth = 854;

    private final int heightAddition = 38; // 38
    private final int widthAddition = 0; // 14

    private String name = "Terminal";
    private String version = "1.0";

    private int height = defaultHeight;
    private int width = defaultWidth;

    private int minX = 400; // 300
    private int minY = 300; // 200

    private Image icon;

    private boolean supressMessages;

    // FONT
    private Font font = UIDesigner.getMonoMd();

    // UI SYMBOLS
    private char divider = ' ';
    private char terminalOutputSymbol = '»';
    private char listSymbol = '*';
    private char terminalInputSymbol = '»';
    private char encloseSymbol = '"';

    // KEY PRESSED
    private boolean strgDown = false;
    private boolean shiftDown = false;

    private boolean processorSwitchable = true;

    private boolean launched = false;

    private TerminalUIConnector terminalLogic;

    final int windowBorder = 12;
    // This is the padding / distance between the left and top window border and UI components
    // Padding / distance between right and bottom and UI components is windowBorder + width / height additions

    final int commandLineHeight = 29;
    // Height of the command line (user input)

    final int terminalInSymbolWidth = 14;
    // Width of the "terminalInSymbol" UI component (left of the command line)

    final int distanceTerminalInSymbolCommandLine = 5;
    // Distance between the terminalInSymbol and the command line (user input)

    final int distanceConsoleCommandLine = 10;
    // Distance between the console and the command line (user input)

    final int labelsHeight = 20;
    // Height of the left and right text labels (above the console)

    final int distanceTopTextLabelsConsole = 0;
    // Distance between the two top text labels (left and right) and the console

    final int terminalInSymbolOffsetTop = 2;
    // Offset (the bigger the number, the more offset to the top)

    final int terminalInSymbolOffsetRight = 2;
    // Offset (the bigger the number, the more offset to the right)

    public TerminalWindow() {
        super();
        supressMessages = false;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(true);
    }

    @Override
    public void onClose() {
        TerminalPage.deinitialize();
        getUIManager().removeIndependentPanel(this);
    }

    public void setTerminalLogic(TerminalUIConnector logicHandler) {
        this.terminalLogic = logicHandler;
    }

    public void setConsoleCaretVisible(boolean visible) {
        console.getCaret().setVisible(visible);
    }

    public void updateTheme() {
        ScrollBarFactory.modifyScrollBar(consoleScrollPane.getHorizontalScrollBar(), Theme.get().scrollBarThumbColor(), Theme.get().scrollBarTrackColor(), Theme.get().textColor());
        ScrollBarFactory.modifyScrollBar(consoleScrollPane.getVerticalScrollBar(), Theme.get().scrollBarThumbColor(), Theme.get().scrollBarTrackColor(), Theme.get().textColor());
        console.setSelectedTextColor(Theme.get().consoleBackgroundColor());
        console.setSelectionColor(Theme.get().textColor());
        console.setCaretColor(Theme.get().textColor());
        terminalInSymbol.setBackground(Theme.get().panelBackgroundColor());
        terminalInSymbol.setForeground(Theme.get().textColor());
        consoleScrollPane.setBackground(Theme.get().consoleBackgroundColor());

        commandLine.setCaretColor(Theme.get().textColor());

        commandLine.setSelectedTextColor(Theme.get().commandLineBackgroundColor());
        commandLine.setSelectionColor(Theme.get().textColor());

        consoleScrollPane.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2.0f), Theme.get().consoleBackgroundColor()));
        commandLine.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2.0f), Theme.get().commandLineBackgroundColor()));

        console.setBackground(Theme.get().consoleBackgroundColor());
        console.setForeground(Theme.get().textColor());

        commandLine.setBackground(Theme.get().commandLineBackgroundColor());
        commandLine.setForeground(Theme.get().textColor());

        leftTextLabel.setForeground(Theme.get().textColor());

        rightTextLabel.setForeground(Theme.get().textColor());

        getContentPane().setBackground(Theme.get().panelBackgroundColor());
    }

    public void launch() {
        if (launched)
            return;

        launched = true;

        setSize(width + widthAddition, height + heightAddition);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle(name);
        setMinimumSize(new Dimension(minX + widthAddition, minY + heightAddition));

        if (icon != null) {
            setIconImage(icon);
        }

        ComponentListener componentListener = new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateUserInterface();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        };
        addComponentListener(componentListener);

        MouseWheelListener mouseWheelListener = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                scrollWheelMoved(e);
            }
        };

        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                /*
                 * Keycodes:
                 * 17 - STRG
                 * 18 - ALT
                 * 16 - SHIFT
                 * 65406 - ALT GR
                 * 81 - Q
                 * 9 - TAB
                 * 66 - B
                 */
                switch (e.getKeyCode()) {
                    case 10: // ENTER
                        terminalLogic.processInput(messageToArrayList(commandLine.getText()));
                        break;
                    case 17: // STRG
                        strgDown = true;
                        break;
                    case 16: // SHIFT
                        shiftDown = true;
                        break;
                    case 66: // B
                        if (strgDown) {
                            commandLine.setText(commandLine.getText() + console.getSelectedText());
                            switchFocus();
                        }
                        break;
                    case 9: // TAB
                        if (strgDown && processorSwitchable) {
                            terminalLogic.rotate();
                        }
                        break;

                    case 18: // ALT
                        if (strgDown) {
                            terminalLogic.returnToMainProcessor();
                        }
                        break;

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                switch (e.getKeyCode()) {
                    case 17: // STRG
                        strgDown = false;
                        break;
                    case 16: // SHIFT
                        shiftDown = false;
                        break;
                }
            }
        };

        leftTextLabel.setFont(font);
        addUIComponent(leftTextLabel);


        rightTextLabel.setFont(font.deriveFont(Font.PLAIN, 12));
        rightTextLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        rightTextLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        addUIComponent(rightTextLabel);

        console.setFont(font);
        console.setLineWrap(false);
        console.setEditable(false);
        console.setFocusTraversalKeysEnabled(false);
        console.addKeyListener(keyAdapter);
        console.addMouseWheelListener(mouseWheelListener);
        addUIComponent(consoleScrollPane);



        terminalInSymbol.setFont(font);
        terminalInSymbol.setText(String.valueOf(terminalInputSymbol));
        terminalInSymbol.setVerticalTextPosition(SwingConstants.CENTER);
        terminalInSymbol.setHorizontalTextPosition(SwingConstants.CENTER);
        terminalInSymbol.setVerticalAlignment(SwingConstants.CENTER);
        terminalInSymbol.setHorizontalAlignment(SwingConstants.CENTER);
        terminalInSymbol.setAlignmentX(SwingConstants.CENTER);
        terminalInSymbol.setAlignmentY(SwingConstants.CENTER);
        addUIComponent(terminalInSymbol);


        commandLine.setFont(font);
        commandLine.setEnabled(true);
        commandLine.setFocusTraversalKeysEnabled(false);
        commandLine.addKeyListener(keyAdapter);
        commandLine.getCaret().setBlinkRate(0);

        updateTheme();

        addUIComponent(commandLine);

        updateUserInterface();

        terminalLogic.initProcessors();

        setVisible(true);
        switchFocus();
    }

    public void setSingleProcessorMode(boolean on) {
        setTopTextEnabled(!on);
        processorSwitchable = !on;
    }

    public void setSupressMessages(boolean supress) {
        supressMessages = supress;
    }

    public boolean getSupressMessages() {
        return supressMessages;
    }

    public void changeTheme(Themes newTheme) {
        Theme.set(newTheme);
        updateTheme();
    }

    public void updateUserInterface() {
        final int localWidth = getWidth() - widthAddition;
        final int localHeight = getHeight() - heightAddition;

        final int consoleWidth = localWidth - 2 * windowBorder;

        int consoleHeight = localHeight - 2 * windowBorder - commandLineHeight - distanceConsoleCommandLine;

        if (anyTopTextEnabled())
            consoleHeight -= labelsHeight - distanceTopTextLabelsConsole;

        UICursor cursor = new UICursor(windowBorder, localHeight - windowBorder - commandLineHeight);
        cursor.placeComponent(terminalInSymbol, terminalInSymbolWidth, commandLineHeight);

        cursor.toTopRight(terminalInSymbol);
        cursor.addX(distanceTerminalInSymbolCommandLine);
        cursor.placeComponent(commandLine, localWidth - terminalInSymbolWidth - 2 * windowBorder - distanceTerminalInSymbolCommandLine, commandLineHeight);

        cursor.setXY(windowBorder, windowBorder);

        if (anyTopTextEnabled()) {
            cursor.placeComponent(leftTextLabel, consoleWidth / 2, labelsHeight);

            cursor.setX(consoleWidth / 2 + windowBorder);
            cursor.placeComponent(rightTextLabel, consoleWidth / 2, labelsHeight);

            cursor.toBottomLeft(leftTextLabel);
            cursor.addY(distanceTopTextLabelsConsole);

            cursor.placeComponent(consoleScrollPane, consoleWidth, consoleHeight);
        } else {
            cursor.setXY(windowBorder, windowBorder);
            cursor.placeComponent(consoleScrollPane, consoleWidth, consoleHeight);
        }

        updateTopTextSize();

        terminalInSymbol.setBounds(terminalInSymbol.getX() + terminalInSymbolOffsetRight, terminalInSymbol.getY() - terminalInSymbolOffsetTop, terminalInSymbol.getWidth(), terminalInSymbol.getHeight());

        consoleScrollPane.updateUI();
    }

    public void setConsoleEditable(boolean editable) {
        console.setEditable(editable);
    }

    public void setCommandLineText(String text) {
        commandLine.setText(text);
    }

    public void setConsoleText(String text) {
        console.setText(text);
    }

    public int getScreenSizeX() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return d.width;
    }

    public int getScreenSizeY() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return d.height;
    }

    public Font getConsoleFont() {
        return font;
    }

    public void setTopRightText(String s) {
        if (s == null) {
            rightTextLabel.setText("");
        } else {
            rightTextLabel.setText(s);
        }
        updateTopTextSize();
    }

    public void setTopLeftText(String s) {
        if (s == null) {
            leftTextLabel.setText("");
        } else {
            leftTextLabel.setText(s);
        }
        updateTopTextSize();
    }

    public void setTopTextEnabled(boolean enabled) {
        leftTextLabel.setEnabled(enabled);
        leftTextLabel.setVisible(enabled);
        rightTextLabel.setEnabled(enabled);
        rightTextLabel.setVisible(enabled);
        updateUserInterface();
    }

    public void setTopLeftTextEnabled(boolean enabled) {
        leftTextLabel.setEnabled(enabled);
        leftTextLabel.setVisible(enabled);
        updateUserInterface();
    }

    public void setTopRightTextEnabled(boolean enabled) {
        rightTextLabel.setEnabled(enabled);
        rightTextLabel.setVisible(enabled);
        updateUserInterface();
    }

    public boolean anyTopTextEnabled() {
        if (leftTextLabel.isEnabled()) {
            return true;
        }
        return rightTextLabel.isEnabled();
    }

    public void list(String title, String[] list, char listSymbol, boolean printIndex) {
        if (!(title == null || title.equals(""))) {
            log(title);
        }

        for (int i = 0; i < list.length; i++) {
            print("   " + listSymbol + " ");
            if (printIndex) {
                print(i + " - ");
            }
            println(list[i]);
        }
    }

    public void list(String title, String[] list, boolean printIndex) {
        list(title, list, listSymbol, printIndex);
    }

    public void log(String s) {
        if (!(s == null || s.equals(""))) {
            println(terminalOutputSymbol + " " + s);
        }
    }

    public void close() {
        dispose();
    }

    public void clear() {
        console.setText("");
    }

    public void println(String message) {
        if (!supressMessages)
            console.append(message + "\n");
    }

    public void println() {
        if (!supressMessages)
            console.append("\n");
    }

    public void print(String message) {
        if (!supressMessages)
            console.append(message);
    }

    private void adjustFontSize(int size, boolean set) {
        final int lowerBound = 8;
        final int upperBound = 150;

        if (set) {
            if (size >= lowerBound && size <= upperBound) {
                setConsoleFontSize((float) size);
            }
        } else {
            final int currSize = console.getFont().getSize();
            final int newSize = currSize + size;
            if (newSize >= lowerBound && newSize <= upperBound) {
                setConsoleFontSize((float) newSize);
            }
        }
    }

    public void updateTopTextSize() {
        final int upperBound = 18;

        if (rightTextLabel.getText().length() > 0) {
            final int additionConstant = 4; // was 7
            int finalSize = (rightTextLabel.getWidth() / (int) (0.59 * (rightTextLabel.getText().length() + additionConstant)));

            if (finalSize > upperBound) {
                finalSize = upperBound;
            }

            rightTextLabel.setFont(rightTextLabel.getFont().deriveFont(Font.PLAIN, finalSize));
        } else {
            rightTextLabel.setFont(rightTextLabel.getFont().deriveFont(Font.PLAIN, 1));
        }

        if (leftTextLabel.getText().length() > 0) {
            final int additionConstant = 4; // was 7
            int finalSize = (leftTextLabel.getWidth() / (int) (0.59 * (leftTextLabel.getText().length() + additionConstant)));
            if (finalSize > upperBound) {
                finalSize = upperBound;
            }
            leftTextLabel.setFont(leftTextLabel.getFont().deriveFont(Font.PLAIN, finalSize));
        } else {
            leftTextLabel.setFont(leftTextLabel.getFont().deriveFont(Font.PLAIN, 1));
        }

    }

    private void scrollWheelMoved(MouseWheelEvent e) {
        final int textSizeMultiplier = 2;
        final int horizontalScrollMultiplier = 50;
        final int verticalScrollMultiplier = 50;

        if (strgDown) {
            adjustFontSize(e.getWheelRotation() * -1 * textSizeMultiplier, false);
        } else if (shiftDown) {
            JScrollBar horizontalScrollBar = consoleScrollPane.getHorizontalScrollBar();
            horizontalScrollBar.setValue(horizontalScrollBar.getValue() + e.getWheelRotation() * horizontalScrollMultiplier);
        } else {
            JScrollBar verticalScrollBar = consoleScrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getValue() + e.getWheelRotation() * verticalScrollMultiplier);
        }
    }

    private void setConsoleFontSize(float size) {
        console.setFont(console.getFont().deriveFont(size));
    }

    public void switchFocus() {
        if (commandLine.isFocusOwner()) {
            console.grabFocus();
        } else {
            commandLine.grabFocus();
        }
    }

    private ArrayList<String> messageToArrayList(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> args = new ArrayList<>();
        boolean ignoreDivider = false;

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);

            if (c == divider && !ignoreDivider) {
                if (!stringBuilder.toString().equals("")) {
                    args.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                }
            } else {
                if (c == encloseSymbol) {
                    ignoreDivider = !ignoreDivider;
                } else {
                    stringBuilder.append(c);
                }

            }
        }

        if (!stringBuilder.toString().equals("")) {
            args.add(stringBuilder.toString());
        }

        return args;
    }

    public char getDivider() {
        return divider;
    }

    public char getTerminalOutputSymbol() {
        return terminalOutputSymbol;
    }

    public char getListSymbol() {
        return listSymbol;
    }

    public char getTerminalInputSymbol() {
        return terminalInputSymbol;
    }

    public char getEncloseSymbol() {
        return encloseSymbol;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public int getHeight() {
        return super.getHeight();
    }

    public int getWidth() {
        return super.getWidth();
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public Image getIcon() {
        return icon;
    }

    public Font getFont() {
        return font;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setHeight(int height) {
        this.height = height;

        if (launched) {
            setBounds(getX(), getY(), getWidth(), height);
            updateUserInterface();
        }
    }

    public void setWidth(int width) {
        this.width = width;

        if (launched) {
            setBounds(getX(), getY(), width, getHeight());
            updateUserInterface();
        }
    }

    public void setMinX(int minX) {
        this.minX = minX;

        if (launched) {
            updateUserInterface();
        }
    }

    public void setMinY(int minY) {
        this.minY = minY;

        if (launched) {
            updateUserInterface();
        }
    }

    public void setIcon(Image icon) {
        this.icon = icon;

        if (launched) {
            updateUserInterface();
        }
    }

    public void setTerminalFont(Font font) {
        this.font = font.deriveFont(this.font.getSize());

        if (launched) {
            updateUserInterface();
            commandLine.setFont(font.deriveFont(commandLine.getFont().getSize()));
            console.setFont(font.deriveFont(console.getFont().getSize()));
            terminalInSymbol.setFont(font.deriveFont(terminalInSymbol.getFont().getSize()));
        }
    }

    public void setDivider(char divider) {
        this.divider = divider;

        if (launched) {
            updateUserInterface();
        }
    }

    public void setEncloseSymbol(char encloseSymbol) {
        this.encloseSymbol = encloseSymbol;

        if (launched) {
            updateUserInterface();
        }
    }

    public void setTerminalOutputSymbol(char terminalOutputSymbol) {
        this.terminalOutputSymbol = terminalOutputSymbol;

        if (launched) {
            updateUserInterface();
        }
    }

    public void setListSymbol(char listSymbol) {
        this.listSymbol = listSymbol;

        if (launched) {
            updateUserInterface();
        }
    }

    public void setTerminalInputSymbol(char terminalInputSymbol) {
        this.terminalInputSymbol = terminalInputSymbol;

        if (launched) {
            updateUserInterface();
        }
    }

    public String getConsoleText() {
        return console.getText();
    }

    public String getCommandLineText() {
        return commandLine.getText();
    }

    public class UICursor {
        private int x;
        private int y;

        public UICursor() {

        }

        public UICursor(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void setXY(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void placeComponent(Component component, int width, int height) {
            component.setBounds(x, y, width, height);
        }

        public void addX(int summand) {
            x = x + summand;
        }

        public void addY(int summand) {
            y = y + summand;
        }

        public void toTopLeft(Component uiComponent) {
            x = uiComponent.getX();
            y = uiComponent.getY();
        }

        public void toTopRight(Component uiComponent) {
            x = uiComponent.getX() + uiComponent.getWidth();
            y = uiComponent.getY();
        }

        public void toBottomRight(Component uiComponent) {
            x = uiComponent.getX() + uiComponent.getWidth();
            y = uiComponent.getY() + uiComponent.getHeight();
        }

        public void toBottomLeft(Component uiComponent) {
            x = uiComponent.getX();
            y = uiComponent.getY() + uiComponent.getHeight();
        }

        public void toCenter(Component uiComponent) {
            x = uiComponent.getX() + uiComponent.getWidth() / 2;
            y = uiComponent.getY() + uiComponent.getHeight() / 2;
        }

    }

}