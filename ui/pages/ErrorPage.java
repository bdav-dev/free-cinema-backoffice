package ui.pages;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import exceptions.DisplayableException;
import free_ui.Page;
import free_ui.UIDesigner;
import free_ui.components.primitives.Button;
import free_ui.components.primitives.Label;
import free_ui.components.primitives.Labels;
import free_ui.components.primitives.ScrollPane;
import free_ui.components.primitives.TextArea;
import free_ui.stacking.Spacer;
import free_ui.stacking.StackManager;
import free_ui.stacking.VStack;

public class ErrorPage extends Page {
    Label errorSymbol;
    Label errorTitle;
    TextArea errorDescriptionTextArea;
    ScrollPane errorDescriptonScrollPane;
    Button exitButton;

    String errorDescriptionText;
    String errorTitleText;

    public ErrorPage(String title, String description) {
        super();

        this.errorTitleText = title;
        this.errorDescriptionText = description;
    }

    public ErrorPage(DisplayableException exception) {
        this(exception.getTitle(), exception.getDescription());
    }

    @Override
    public void launch() {
        setSize(450, 400);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("FREE CINEMA Backoffice: Fehler");
        setResizable(false);

        errorDescriptionTextArea = new TextArea();
        errorDescriptonScrollPane = new ScrollPane(errorDescriptionTextArea);
        errorDescriptionTextArea.setEditable(false);
        errorDescriptionTextArea.setText(errorDescriptionText);

        getUIParent().setEnabled(false);

        errorSymbol = Labels.xlText().centered();
        errorSymbol.setFont(errorSymbol.getFont().deriveFont(35f));
        errorSymbol.setSize(60, 60);
        errorSymbol.setText("!");
        errorSymbol.setBorder(UIDesigner.getThinBorder());

        this.errorTitle = Labels.xlBoldText().centered();
        this.errorTitle.setText(errorTitleText);
        UIDesigner.setHeight(this.errorTitle, 35);

        

        exitButton = new Button();
        exitButton.setText("OK");
        exitButton.setSize(180, 35);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        });
        exitButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    exitButton.doClick();
                }
            }
        });

        VStack mainStack = new VStack();
        mainStack.add(errorSymbol);
        mainStack.add(this.errorTitle);
        mainStack.add(new Spacer(0.1f));
        mainStack.add(errorDescriptonScrollPane);
        mainStack.add(new Spacer(0.1f));
        mainStack.add(exitButton);

        StackManager manager = new StackManager(mainStack, 50, 6);
        manager.build(this);

        setVisible(true);
    }

    @Override
    public void reset() {

    }

    @Override
    public void onClose() {
        getUIParent().setEnabled(true);
        super.onClose();
    }

}
