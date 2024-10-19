package dev.bdav.fcbo.frontend.dialog;

import dev.bdav.fcbo.freeui.components.RoundedPanel;
import dev.bdav.fcbo.freeui.components.SpecialButton;
import dev.bdav.fcbo.freeui.components.label.H1;
import dev.bdav.fcbo.freeui.components.label.SectionTitle;
import dev.bdav.fcbo.freeui.factory.IconFactory;
import dev.bdav.fcbo.freeui.factory.LeftLabelFactory;
import dev.bdav.fcbo.freeui.stacking.JustifyContent;
import dev.bdav.fcbo.freeui.stacking.Spacer;
import dev.bdav.fcbo.freeui.stacking.Stack;
import dev.bdav.fcbo.freeui.stacking.StackBuilder;
import dev.bdav.fcbo.freeui.util.ButtonMargins;
import dev.bdav.fcbo.frontend.icon.GoogleMaterialIcon;

import javax.swing.*;
import java.awt.*;

public class DatabaseConnectionDialog extends JDialog {

    public DatabaseConnectionDialog(Frame parent) {
        super(parent, true);

        setMinimumSize(new Dimension(350, 250));
        setSize(600, 250);
        setLocationRelativeTo(parent);

        var urlTextField = new JTextField();
        var usernameTextField = new JTextField();
        var passwordTextField = new JPasswordField();


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
            JPasswordField passwordField
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

        var saveButton = new SpecialButton("Speichern", SpecialButton.Variant.PRIMARY);

        return StackBuilder.horizontal()
                .content(cancelButton, saveButton)
                .justifyContent(JustifyContent.RIGHT)
                .componentMargin(5)
                .build();
    }

}
