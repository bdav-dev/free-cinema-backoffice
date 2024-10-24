package dev.bdav.fcbo.frontend.dialog;

import dev.bdav.fcbo.freeui.components.RoundedPanel;
import dev.bdav.fcbo.freeui.components.SpecialButton;
import dev.bdav.fcbo.freeui.components.label.H1;
import dev.bdav.fcbo.freeui.components.label.SectionTitle;
import dev.bdav.fcbo.freeui.factory.IconFactory;
import dev.bdav.fcbo.freeui.factory.LeftLabelFactory;
import dev.bdav.fcbo.freeui.factory.TextFieldFactory;
import dev.bdav.fcbo.freeui.stacking.JustifyContent;
import dev.bdav.fcbo.freeui.stacking.Spacer;
import dev.bdav.fcbo.freeui.stacking.Stack;
import dev.bdav.fcbo.freeui.stacking.StackBuilder;
import dev.bdav.fcbo.freeui.util.ButtonMargins;
import dev.bdav.fcbo.frontend.components.PasswordField;
import dev.bdav.fcbo.frontend.icon.GoogleMaterialIcon;
import dev.bdav.fcbo.frontend.storage.DatabaseCredentials;

import javax.swing.*;
import java.awt.*;

public class DatabaseConnectionDialog extends JDialog {
    private final DatabaseCredentials databaseCredentials;

    JTextField urlTextField;
    JTextField usernameTextField;
    PasswordField passwordTextField;

    public DatabaseConnectionDialog(Frame parent) {
        super(parent, true);

        this.databaseCredentials = new DatabaseCredentials();

        setMinimumSize(new Dimension(350, 250));
        setSize(600, 250);
        setLocationRelativeTo(parent);

        urlTextField = TextFieldFactory.mono();
        usernameTextField = TextFieldFactory.mono();
        passwordTextField = new PasswordField();

        add(
                StackBuilder.vertical()
                        .content(
                                createTitle(),
                                createDatabaseConnectionSettingsPanel(
                                        urlTextField, usernameTextField, passwordTextField
                                ),
                                Spacer.expanding(),
                                createFooter()
                        )
                        .stackMargin(10)
                        .componentMargin(10)
                        .build()
        );

        mountCredentials();

    }

    private void mountCredentials() {
        urlTextField.setText(
                databaseCredentials.url().get().orElse("")
        );
        usernameTextField.setText(
                databaseCredentials.username().get().orElse("")
        );
        passwordTextField.getJPasswordField().setText(
                databaseCredentials.password().get().orElse("")
        );
    }

    private void saveCredentials() {
        databaseCredentials.url().set(
                urlTextField.getText().trim()
        );
        databaseCredentials.username().set(
                usernameTextField.getText().trim()
        );
        databaseCredentials.password().set(
                new String(passwordTextField.getJPasswordField().getPassword())
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

    private Stack createFooter() {
        var cancelButton = new SpecialButton("Abbrechen", SpecialButton.Variant.TERTIARY);
        cancelButton.setMargin(ButtonMargins.MEDIUM_INSETS);
        cancelButton.addActionListener(e -> setVisible(false));

        var saveButton = new SpecialButton("Überschreiben", SpecialButton.Variant.PRIMARY);
        saveButton.addActionListener(e -> saveCredentials());

        return StackBuilder.horizontal()
                .content(cancelButton, saveButton)
                .justifyContent(JustifyContent.RIGHT)
                .componentMargin(5)
                .build();
    }

}
