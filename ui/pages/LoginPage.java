package ui.pages;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

import assets.AssetManager.Images;
import cli.TerminalPage;
import exceptions.DisplayableException;
import fcbo.FCBO;
import free_ui.Page;
import free_ui.UIDesigner;
import free_ui.components.Image;
import free_ui.components.LabeledPasswordField;
import free_ui.components.primitives.Button;
import free_ui.components.primitives.Label;
import free_ui.components.primitives.Labels;
import free_ui.components.support.FrameConstants;
import free_ui.components.LabeledTextField;
import free_ui.stacking.Spacer;
import free_ui.stacking.StackManager;
import free_ui.stacking.VStack;
import free_ui.theme.AppTheme;
import utility.Concurrency;

public class LoginPage extends Page {
    private Label appName;
    private Label logo;

    private LabeledTextField usernameField;
    private LabeledPasswordField passwordField;

    private JButton login;

    public LoginPage() {
        super();
    }

    @Override
    public void launch() {
        setSize(602, 500);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("FREE CINEMA Backoffice: Login");
        setResizable(false);

        getContentPane().setBackground(AppTheme.get().background());

        setResetOnClose(true);

        appName = Labels.headline();
        appName.setText("Backoffice");

        logo = new Image(Images.FcLogo, 0.5f);

        KeyAdapter enterAction = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10)
                    login.doClick();
            }
        };

        usernameField = new LabeledTextField();
        usernameField.setLabelText("Benutzername");
        usernameField.setSize(500, 60);
        usernameField.addKeyListener(enterAction);

        passwordField = new LabeledPasswordField();
        passwordField.setText("Passwort");
        passwordField.setSize(500, 60);
        passwordField.addKeyListener(enterAction);

        login = new Button();
        login.setText("Login");
        login.setSize(180, 40);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButtonClicked();
            }
        });

        var cliButton = new Button("CLI");
        cliButton.setSize(35, 25);
        cliButton.addActionListener(e -> {
            launchCLI();
        });
        attachComponentToCorner(cliButton, 8, FrameConstants.BOTTOM_LEFT);

        VStack vs1 = new VStack();
        vs1.add(new Spacer(5));
        vs1.add(logo);
        vs1.add(new Spacer(5));
        vs1.add(appName);
        vs1.add(new Spacer());
        vs1.add(usernameField);
        vs1.add(new Spacer(0.3f));
        vs1.add(passwordField);
        vs1.add(new Spacer());
        vs1.add(login);
        vs1.add(new Spacer(5));

        new StackManager(vs1, 50, 0).build(this);

        setVisible(true);
    }

    public void loginButtonClicked() {
        loginUser();
    }

    private void loginUser() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        UIDesigner.block(login);
        Concurrency.async(() -> {
            try {
                FCBO.getInstance().performLoginFromLoginPanel(username, password);
            } catch (DisplayableException e) {
                addErrorPanel(e);
            } finally {
                UIDesigner.unblock(login);
            }
        });
    }

    private void launchCLI() {
        if (TerminalPage.isInitialized())
            TerminalPage.getTerminalPanel().requestFocus();
        else
            getUIManager().addIndependentPanel(() -> TerminalPage.getTerminalPanel());
    }

    @Override
    public void reset() {
        usernameField.setText("");
        passwordField.setFieldText("");
    }

    @Override
    public void onClose() {
        super.onClose();

    }

}
