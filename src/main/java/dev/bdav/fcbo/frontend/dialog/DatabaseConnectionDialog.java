package dev.bdav.fcbo.frontend.dialog;

import dev.bdav.fcbo.backend.Database;
import dev.bdav.fcbo.backend.model.misc.DatabaseCredentials;
import dev.bdav.fcbo.freeui.components.RoundedPanel;
import dev.bdav.fcbo.freeui.components.SpecialButton;
import dev.bdav.fcbo.freeui.components.label.H1;
import dev.bdav.fcbo.freeui.components.label.SectionTitle;
import dev.bdav.fcbo.freeui.factory.IconFactory;
import dev.bdav.fcbo.freeui.factory.LeftLabelFactory;
import dev.bdav.fcbo.freeui.factory.TextFieldFactory;
import dev.bdav.fcbo.freeui.interfaces.HasRender;
import dev.bdav.fcbo.freeui.stacking.JustifyContent;
import dev.bdav.fcbo.freeui.stacking.Spacer;
import dev.bdav.fcbo.freeui.stacking.Stack;
import dev.bdav.fcbo.freeui.stacking.StackBuilder;
import dev.bdav.fcbo.freeui.util.ButtonMargins;
import dev.bdav.fcbo.freeui.util.InputValidation;
import dev.bdav.fcbo.frontend.components.PasswordField;
import dev.bdav.fcbo.frontend.icon.GoogleMaterialIcon;
import dev.bdav.fcbo.frontend.storage.DatabaseCredentialsStorage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DatabaseConnectionDialog extends JDialog implements HasRender {
    private final JTextField urlTextField;
    private final JTextField usernameTextField;
    private final PasswordField passwordTextField;
    private final Mode mode;

    private boolean reconfigureDatabaseWhenCredentialsChange;

    public DatabaseConnectionDialog(
            Mode mode,
            Frame parent
    ) {
        super(parent, true);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(350, 300));
        setSize(600, 300);
        setLocationRelativeTo(parent);

        this.mode = mode;
        urlTextField = TextFieldFactory.mono();
        usernameTextField = TextFieldFactory.mono();
        passwordTextField = new PasswordField();

        render();

        if (mode.loadCredentialsFromLocalStorage) {
            loadCredentialsFromLocalStorage();
        }
    }

    public void setReconfigureDatabaseWhenCredentialsChange(boolean reconfigureDatabaseWhenCredentialsChange) {
        this.reconfigureDatabaseWhenCredentialsChange = reconfigureDatabaseWhenCredentialsChange;
    }

    private void loadCredentialsFromLocalStorage() {
        var credentialsStorage = DatabaseCredentialsStorage.get().pack();

        urlTextField.setText(
                credentialsStorage.url().orElse("")
        );
        usernameTextField.setText(
                credentialsStorage.username().orElse("")
        );
        passwordTextField.getJPasswordField().setText(
                credentialsStorage.password().orElse("")
        );
    }

    private void saveButtonClicked() {
        var credentials = createCredentialsFromUserInput();

        if (credentials.isComplete()) {
            DatabaseCredentialsStorage.get().save(credentials);
            if (reconfigureDatabaseWhenCredentialsChange) {
                reconfigureDatabase(credentials);
            }
        }

        dispose();
    }

    private DatabaseCredentials createCredentialsFromUserInput() {
        return new DatabaseCredentials(
                urlTextField.getText().trim(),
                usernameTextField.getText().trim(),
                new String(passwordTextField.getJPasswordField().getPassword())
        );
    }

    private void reconfigureDatabase(DatabaseCredentials credentials) {
        boolean isEqualToCredentialsUsedForDbConfiguration = Database.getCredentialsUsedForConfiguration()
                .map(credentialsUsedForConfiguration -> credentialsUsedForConfiguration.equals(credentials))
                .orElse(false);

        if (!isEqualToCredentialsUsedForDbConfiguration) {
            Database.configure(credentials);
        }
    }

    @Override
    public void render() {
        add(
                StackBuilder.vertical()
                        .content(
                                createTitle(),
                                createDatabaseConnectionSettingsPanel(
                                        urlTextField, usernameTextField, passwordTextField
                                ),
                                Spacer.expanding(),
                                createFooter(mode.primaryActionName)
                        )
                        .stackMargin(10)
                        .componentMargin(10)
                        .build()
        );
    }

    private SectionTitle createTitle() {
        var title = new SectionTitle(
                IconFactory.labeled(GoogleMaterialIcon.DATABASE, new H1("Datenbankverbindung"))
        );
        title.setComponentCenterAligned();

        return title;
    }

    private RoundedPanel createDatabaseConnectionSettingsPanel(
            JTextField urlTextField,
            JTextField usernameTextField,
            PasswordField passwordField
    ) {
        final int textFieldMaxWidth = 400;

        return new RoundedPanel(
                StackBuilder.vertical()
                        .content(
                                LeftLabelFactory.of(
                                        "URL",
                                        urlTextField,
                                        textFieldMaxWidth
                                ),
                                LeftLabelFactory.of(
                                        "Benutzername",
                                        usernameTextField,
                                        textFieldMaxWidth
                                ),
                                LeftLabelFactory.of(
                                        "Passwort",
                                        passwordField,
                                        textFieldMaxWidth
                                )
                        )
                        .stackMargin(10)
                        .componentMargin(5)
                        .build()
        );
    }

    private Stack createFooter(String primaryActionName) {
        var cancelButton = new SpecialButton("Abbrechen", SpecialButton.Variant.TERTIARY);
        cancelButton.setMargin(ButtonMargins.MEDIUM_INSETS);
        cancelButton.addActionListener(e -> dispose());

        var saveButton = new SpecialButton(primaryActionName, SpecialButton.Variant.PRIMARY);
        saveButton.addActionListener(e -> saveButtonClicked());


        InputValidation.create(
                trigger -> {
                    InputValidation.valueChangedListener(urlTextField, trigger);
                    InputValidation.valueChangedListener(usernameTextField, trigger);
                    InputValidation.valueChangedListener(passwordTextField.getJPasswordField(), trigger);
                },
                List.of(
                        urlTextField::getText,
                        usernameTextField::getText,
                        () -> new String(passwordTextField.getJPasswordField().getPassword())
                ),
                list -> list.stream().noneMatch(String::isBlank),
                saveButton::setEnabled
        );


        return StackBuilder.horizontal()
                .content(cancelButton, saveButton)
                .justifyContent(JustifyContent.RIGHT)
                .componentMargin(5)
                .build();
    }


    public enum Mode {
        OVERRIDE(
                "Überschreiben",
                false
        ),
        EDIT(
                "Speichern",
                true
        );

        private final String primaryActionName;
        private final boolean loadCredentialsFromLocalStorage;

        Mode(
                String primaryActionName,
                boolean loadCredentialsFromLocalStorage
        ) {
            this.primaryActionName = primaryActionName;
            this.loadCredentialsFromLocalStorage = loadCredentialsFromLocalStorage;
        }
    }

}


