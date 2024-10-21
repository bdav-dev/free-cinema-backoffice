package dev.bdav.fcbo.freeui.factory;

import dev.bdav.fcbo.freeui.font.Fonts;

import javax.swing.*;

public class TextFieldFactory {

    private TextFieldFactory() {
    }

    public static JTextField mono() {
        var textField = new JTextField();
        var fontSize = textField.getFont().getSize();
        textField.setFont(Fonts.monospaced(fontSize));

        return textField;
    }

    public static JPasswordField password() {
        var passwordField = new JPasswordField();
        var fontSize = passwordField.getFont().getSize();
        passwordField.setFont(Fonts.monospaced(fontSize));

        return passwordField;
    }


}
