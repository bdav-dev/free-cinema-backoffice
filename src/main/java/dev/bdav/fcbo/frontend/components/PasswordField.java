package dev.bdav.fcbo.frontend.components;

import dev.bdav.fcbo.freeui.factory.IconFactory;
import dev.bdav.fcbo.freeui.font.Fonts;
import dev.bdav.fcbo.freeui.sizing.Size;
import dev.bdav.fcbo.freeui.sizing.Sizing;
import dev.bdav.fcbo.freeui.stacking.HStack;
import dev.bdav.fcbo.freeui.stacking.StackBuilder;
import dev.bdav.fcbo.freeui.util.ButtonMargins;
import dev.bdav.fcbo.frontend.icon.GoogleMaterialIcon;

import javax.swing.*;

public class PasswordField extends HStack {
    private static final int DEFAULT_SHOW_HIDE_PASSWORD_BUTTON_WIDTH = 35;

    private final JButton showHidePasswordButton;
    private final JPasswordField passwordField;

    private boolean isPasswordVisible;

    public PasswordField() {
        this(DEFAULT_SHOW_HIDE_PASSWORD_BUTTON_WIDTH);
    }

    public PasswordField(int showHidePasswordButtonWidth) {
        passwordField = new JPasswordField();
        float fontSize = passwordField.getFont().getSize();
        passwordField.setFont(Fonts.monospaced().deriveFont(fontSize));

        showHidePasswordButton = new JButton();
        showHidePasswordButton.addActionListener(e -> togglePasswordVisibility());
        showHidePasswordButton.setMargin(ButtonMargins.SMALL_INSETS);
        Sizing.modify(showHidePasswordButton)
                .height(Size.lazyGrowing())
                .width(Size.fixed(showHidePasswordButtonWidth));

        setIsPasswordVisible(false);

        StackBuilder.modify(this)
                .content(passwordField, showHidePasswordButton)
                .componentMargin(1);
    }

    public JPasswordField getJPasswordField() {
        return passwordField;
    }

    public void setIsPasswordVisible(boolean isPasswordVisible) {
        this.isPasswordVisible = isPasswordVisible;

        showHidePasswordButton.removeAll();
        showHidePasswordButton.add(
                IconFactory.standalone(
                        isPasswordVisible ? GoogleMaterialIcon.VISIBILITY_OFF : GoogleMaterialIcon.VISIBILITY
                )
        );
        showHidePasswordButton.revalidate();

        passwordField.setEchoChar(
                isPasswordVisible
                        ? (char) 0
                        : '•'
        );
    }

    private void togglePasswordVisibility() {
        setIsPasswordVisible(!isPasswordVisible);
    }

}
