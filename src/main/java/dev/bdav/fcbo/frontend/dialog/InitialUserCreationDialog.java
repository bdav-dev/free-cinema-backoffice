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
import dev.bdav.fcbo.freeui.sizing.Size;
import dev.bdav.fcbo.freeui.sizing.Sizing;
import dev.bdav.fcbo.freeui.stacking.Spacer;
import dev.bdav.fcbo.freeui.stacking.Stack;
import dev.bdav.fcbo.freeui.stacking.StackAlign;
import dev.bdav.fcbo.freeui.stacking.StackBuilder;
import dev.bdav.fcbo.freeui.util.ButtonMargins;
import dev.bdav.fcbo.frontend.icon.GoogleMaterialIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InitialUserCreationDialog extends JDialog {


    public InitialUserCreationDialog(Frame parent) {
        super(parent, true);

        setMinimumSize(new Dimension(350, 400));
        setSize(550, 400);
        setLocationRelativeTo(parent);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        var descriptionTextArea = TextAreaFactory.readonlyScrollable(
                "Es wurde erkannt, dass dieses System keine Benutzer besitzt. " +
                        "Du kannst jetzt einen neuen Benutzer anlegen, um zu starten."
        );
        Sizing.modify(descriptionTextArea)
                .width(Size.eagerGrowing())
                .height(Size.eagerPreferred(10, 100));

        var usernameTextField = new JTextField();
        var passwordField = new JPasswordField();


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

    private RoundedPanel createSignUpPanel(JTextField usernameTextField, JPasswordField passwordField) {
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
        var closeDialogButton = new SpecialButton("Dialog schließen", SpecialButton.Variant.TERTIARY);
        closeDialogButton.setMargin(ButtonMargins.MEDIUM_INSETS);
        closeDialogButton.addActionListener(e -> setVisible(false));

        var closeAppButton = new SpecialButton("App schließen", SpecialButton.Variant.TERTIARY);
        closeAppButton.setMargin(ButtonMargins.MEDIUM_INSETS);
        closeAppButton.addActionListener(e -> System.exit(0));

        var createAccountButton = new SpecialButton("Account anlegen", SpecialButton.Variant.PRIMARY);

        return StackBuilder.horizontal()
                .content(
                        closeDialogButton, Spacer.expanding(), closeAppButton, createAccountButton
                )
                .componentMargin(5)
                .build();
    }
}
