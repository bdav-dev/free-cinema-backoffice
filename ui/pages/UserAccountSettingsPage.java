package ui.pages;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.SwingConstants;

import db.model.UserAccount;
import exceptions.DisplayableException;
import free_ui.DatatypePage;
import free_ui.UIDesigner;
import free_ui.UI;
import free_ui.components.HorizontalLine;
import free_ui.components.LabeledPasswordField;
import free_ui.components.LabeledTextField;
import free_ui.components.primitives.Button;
import free_ui.components.primitives.Label;
import free_ui.components.primitives.Labels;
import free_ui.components.support.FrameConstants;
import free_ui.stacking.Spacer;
import free_ui.stacking.StackManager;
import free_ui.stacking.VStack;
import free_ui.theme.AppTheme;
import services.UserAccountService;
import utility.Concurrency;

public class UserAccountSettingsPage extends DatatypePage {
    private UserAccount userAccount;

    private HorizontalLine usernameLine;
    private LabeledTextField usernameField;

    private HorizontalLine passwordLine;
    private LabeledPasswordField currentPasswordField, newPasswordField, confirmNewPasswordField;

    private Button saveAndDiscardButton;

    private Label privLevelLabel;

    public UserAccountSettingsPage(UserAccount userAccount, AccessType accessType) {
        super(accessType);
        this.userAccount = userAccount;
    }

    @Override
    public void launch() {
        setSize(532, 532); // 552 550
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("FREE CINEMA Backoffice: Benutzeraccounteinstellungen");
        setResizable(false);

        getContentPane().setBackground(AppTheme.get().background());

        usernameLine = new HorizontalLine();
        usernameField = new LabeledTextField();

        passwordLine = new HorizontalLine();

        currentPasswordField = new LabeledPasswordField();
        newPasswordField = new LabeledPasswordField();
        confirmNewPasswordField = new LabeledPasswordField();
        saveAndDiscardButton = new Button();

        privLevelLabel = Labels.smallText();

        prepareUI();
        setVisible(true);
    }

    @Override
    public void save() {
        String newUsername = usernameField.getText();
        String oldPassword = currentPasswordField.getPasswordAsString();
        String newPassword = newPasswordField.getPasswordAsString();
        String newPasswordConfim = confirmNewPasswordField.getPasswordAsString();

        Concurrency.async(() -> {
            boolean error = false;

            try {
                UserAccountService.getInstance().changeUsername(userAccount, newUsername);
            } catch (DisplayableException e) {
                UI.getInstance().addErrorPanelToCurrentUIPanel(e);
                error = true;
            }

            try {
                UserAccountService.getInstance().changePassword(userAccount, oldPassword, newPassword, newPasswordConfim);
            } catch (DisplayableException e) {
                UI.getInstance().addErrorPanelToCurrentUIPanel(e);
                error = true;
            }

            if (!error)
                defaultOnCloseOperation();
        });
    }

    @Override
    public void create() {

    }

    @Override
    public void prepareEdit() {
        int fieldWidth = 480;

        usernameLine.setText("Benutzername ändern");
        UIDesigner.setHeight(usernameLine, 25);

        usernameField.setLabelText("Benutzername");
        UIDesigner.setWidth(usernameField, fieldWidth);

        passwordLine.setText("Passwort ändern");
        UIDesigner.setHeight(passwordLine, 25);

        currentPasswordField.setText("aktuelles Passwort");
        UIDesigner.setWidth(currentPasswordField, fieldWidth);

        newPasswordField.setText("neues Passwort");
        UIDesigner.setWidth(newPasswordField, fieldWidth);

        confirmNewPasswordField.setText("neues Passwort bestätigen");
        UIDesigner.setWidth(confirmNewPasswordField, fieldWidth);

        saveAndDiscardButton.setText("Speichern");
        UIDesigner.setHeight(saveAndDiscardButton, 40);
        UIDesigner.setWidth(saveAndDiscardButton, 200);
        saveAndDiscardButton.addActionListener((e) -> {
            save();
        });

        VStack mainStack = new VStack();

        mainStack.add(usernameLine);
        mainStack.add(usernameField);
        mainStack.add(new Spacer(0.75f));
        mainStack.add(passwordLine);
        mainStack.add(currentPasswordField);
        mainStack.add(newPasswordField);
        mainStack.add(confirmNewPasswordField);
        mainStack.add(new Spacer(0.75f));
        mainStack.add(saveAndDiscardButton);

        StackManager manager = new StackManager(mainStack, 24, 6); // 50
        manager.build(this);

        privLevelLabel.setSize(100, 25);
        attachComponentToCorner(privLevelLabel, 10, FrameConstants.BOTTOM_LEFT);

        setPrivilegeLevelLabel(userAccount.getPrivilegeLevel());
        privLevelLabel.setHorizontalAlignment(SwingConstants.LEFT);

        usernameField.setText(userAccount.getUsername());

    }

    @Override
    public void prepareRead() {}

    @Override
    public void prepareCreate() {}

    public void setPrivilegeLevelLabel(int privLevel) {
        privLevelLabel.setText("Priv.-Level: " + privLevel);
    }

}
