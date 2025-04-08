package dev.bdav.fcbo.frontend.dialog;

import dev.bdav.fcbo.freeui.components.LeftLabel;
import dev.bdav.fcbo.freeui.components.RoundedPanel;
import dev.bdav.fcbo.freeui.components.SpecialButton;
import dev.bdav.fcbo.freeui.components.label.H1;
import dev.bdav.fcbo.freeui.components.label.H2;
import dev.bdav.fcbo.freeui.components.label.SectionTitle;
import dev.bdav.fcbo.freeui.factory.IconFactory;
import dev.bdav.fcbo.freeui.factory.LeftLabelFactory;
import dev.bdav.fcbo.freeui.factory.TextAreaFactory;
import dev.bdav.fcbo.freeui.factory.TextFieldFactory;
import dev.bdav.fcbo.freeui.interfaces.HasRender;
import dev.bdav.fcbo.freeui.sizing.Size;
import dev.bdav.fcbo.freeui.sizing.Sizing;
import dev.bdav.fcbo.freeui.stacking.*;
import dev.bdav.fcbo.freeui.util.ButtonMargins;
import dev.bdav.fcbo.frontend.components.PasswordField;
import dev.bdav.fcbo.frontend.icon.GoogleMaterialIcon;

import javax.swing.*;
import java.awt.*;

public class InitialUserCreationDialog extends JDialog implements HasRender {

    public InitialUserCreationDialog(Frame parent) {
        super(parent, true);

        setMinimumSize(new Dimension(425, 390));
        setSize(550, 400);
        setLocationRelativeTo(parent);

        render();
    }

    @Override
    public void render() {
        var descriptionTextArea = TextAreaFactory.textBox(
                "Willkommen im Einrichtungsassistenten. " +
                        "Du kannst jetzt einen neuen Benutzer anlegen, um FREE CINEMA Backoffice zu nutzen."
        );
        Sizing.modify(descriptionTextArea)
                .width(Size.eagerGrowing())
                .height(Size.eagerPreferred(10, 100));

        var usernameTextField = TextFieldFactory.mono();
        var passwordField = new PasswordField();

        add(
                StackBuilder.vertical()
                        .content(
                                createTitle(),
                                descriptionTextArea,
                                createSignUpPanel(usernameTextField, passwordField),
                                Spacer.fixed(10),
                                Spacer.expanding(),
                                createFooter()
                        )
                        .stackMargin(10)
                        .componentMargin(5)
                        .build()
        );
    }

    private SectionTitle createTitle() {
        var title = new SectionTitle(
                IconFactory.labeled(GoogleMaterialIcon.EMOJI_PEOPLE, new H1("Willkommen"))
        );
        title.setComponentCenterAligned();

        return title;
    }

    private RoundedPanel createSignUpPanel(JTextField usernameTextField, PasswordField passwordField) {
        var privilegeLabeled = new LeftLabel(
                "Berechtigungen",
                new JLabel("Administrator")
        );
        Sizing.modify(privilegeLabeled)
                .height(Size.fixed(35));

        return new RoundedPanel(
                StackBuilder.vertical()
                        .content(
                                StackAlign.left(IconFactory.labeled(GoogleMaterialIcon.PASSKEY, new H2("Neuer Benutzer"))),
                                LeftLabelFactory.of(
                                        "Benutzername",
                                        usernameTextField,
                                        300
                                ),
                                LeftLabelFactory.of(
                                        "Passwort",
                                        passwordField,
                                        300
                                ),
                                privilegeLabeled
                        )
                        .stackMargin(10)
                        .componentMargin(5)
                        .build()
        );
    }

    private Stack createFooter() {
        var closeDialogButton = new SpecialButton("Abbrechen", SpecialButton.Variant.TERTIARY);
        closeDialogButton.setMargin(ButtonMargins.MEDIUM_INSETS);
        closeDialogButton.addActionListener(e -> setVisible(false));

        var createAccountButton = new SpecialButton("Account anlegen", SpecialButton.Variant.PRIMARY);

        return StackBuilder.horizontal()
                .content(
                        closeDialogButton, createAccountButton
                )
                .justifyContent(JustifyContent.RIGHT)
                .componentMargin(5)
                .build();
    }
}
