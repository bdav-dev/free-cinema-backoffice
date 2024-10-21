package dev.bdav.fcbo.frontend.pages;

import dev.bdav.fcbo.backend.service.AccountService;
import dev.bdav.fcbo.freeui.components.SpecialButton;
import dev.bdav.fcbo.freeui.components.TopLabel;
import dev.bdav.fcbo.freeui.core.Page;
import dev.bdav.fcbo.freeui.core.UI;
import dev.bdav.fcbo.freeui.factory.IconFactory;
import dev.bdav.fcbo.freeui.factory.TextFieldFactory;
import dev.bdav.fcbo.freeui.font.Fonts;
import dev.bdav.fcbo.freeui.interfaces.HasRender;
import dev.bdav.fcbo.freeui.sizing.Size;
import dev.bdav.fcbo.freeui.sizing.Sizing;
import dev.bdav.fcbo.freeui.stacking.*;
import dev.bdav.fcbo.freeui.util.ButtonMargins;
import dev.bdav.fcbo.frontend.components.PasswordField;
import dev.bdav.fcbo.frontend.dialog.DatabaseConnectionDialog;
import dev.bdav.fcbo.frontend.dialog.InitialUserCreationDialog;
import dev.bdav.fcbo.frontend.icon.GoogleMaterialIcon;
import dev.bdav.fcbo.util.ApplicationConstants;

import javax.swing.*;


public class LoginPage extends Page implements HasRender {
    AccountService accountService;

    public LoginPage() {
        accountService = AccountService.get();

        render();

        UI.runWhenReady(
                ui -> test()
        );
    }

    private void test() {
        var doesAccountExist = accountService.doesAnyAccountExist();

        if (!doesAccountExist) {
            System.out.println("Account does not exist");
            var dialog = new InitialUserCreationDialog(UI.get());
            dialog.setVisible(true);
        }

    }

    @Override
    public void render() {
        add(
                StackBuilder.vertical()
                        .content(
                                StackBuilder.vertical()
                                        .content(
                                                createAppLabel(),
                                                Spacer.fixed(15),
                                                createUsernameTextField(),
                                                createPasswordField(),
                                                Spacer.fixed(15),
                                                createLoginButton()
                                        )
                                        .stackMargin(10)
                                        .componentMargin(10)
                                        .justifyContent(JustifyContent.CENTER)
                                        .build(),
                                createFooter()
                        )
                        .stackMargin(5)
                        .build()
        );
    }

    private Stack createFooter() {
        var dbConnectionSettingsButton = new SpecialButton(
                IconFactory.labeled(GoogleMaterialIcon.DATABASE, "Einstellungen"),
                SpecialButton.Variant.TERTIARY
        );
        dbConnectionSettingsButton.setMargin(ButtonMargins.SMALL_INSETS);
        dbConnectionSettingsButton.addActionListener(
                e -> {
                    new DatabaseConnectionDialog(UI.get()).setVisible(true);
                    //MemberDao.get().save();
                }
        );

        return StackBuilder.horizontal()
                .content(
                        dbConnectionSettingsButton, createVersionLabel()
                )
                .justifyContent(JustifyContent.SPACE_BETWEEN)
                .alignContent(AlignContent.BOTTOM)
                .build();
    }

    private Stack createAppLabel() {
        var freeCinemaLabel = new JLabel("FREE CINEMA");
        Fonts.setFontSize(freeCinemaLabel, 30f);

        var backofficeLabel = new JLabel("Backoffice");
        backofficeLabel.setFont(Fonts.monospaced(25f));

        return StackBuilder.vertical()
                .content(
                        freeCinemaLabel,
                        backofficeLabel
                )
                .build();
    }

    private Stack createUsernameTextField() {
        var usernameTextField = TextFieldFactory.mono();
        Sizing.modify(usernameTextField)
                .width(Size.lazyGrowing(100, 0.5f))
                .height(Size.fixed(40));

        return StackBuilder.horizontal()
                .content(
                        Spacer.custom(Size.lazyGrowing(0, 0.2f)),
                        new TopLabel(
                                "Benutzername",
                                usernameTextField
                        ),
                        Spacer.custom(Size.lazyGrowing(0, 0.2f))
                )
                .build();
    }

    private Stack createPasswordField() {
        var passwordField = new PasswordField(40);

        Sizing.modify(passwordField)
                .width(Size.lazyGrowing(100, 0.5f))
                .height(Size.fixed(40));

        return StackBuilder.horizontal()
                .content(
                        Spacer.custom(Size.lazyGrowing(0, 0.2f)),
                        new TopLabel(
                                "Passwort",
                                passwordField
                        ),
                        Spacer.custom(Size.lazyGrowing(0, 0.2f))
                )
                .build();
    }

    private JButton createLoginButton() {
        var loginButton = new JButton();
        loginButton.add(IconFactory.labeled(GoogleMaterialIcon.LOGIN, new JLabel("Login")));
        Sizing.modify(loginButton)
                .width(Size.fixed(125))
                .height(Size.fixed(35));

        loginButton.addActionListener(e -> UI.get().push(new LoginPage()));

        return loginButton;
    }

    private JLabel createVersionLabel() {
        var versionLabel = new JLabel("Version " + ApplicationConstants.APP_VERSION);
        return versionLabel;
    }

}
