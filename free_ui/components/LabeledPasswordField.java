package free_ui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.border.Border;

import free_ui.components.primitives.Label;
import free_ui.components.primitives.Labels;
import free_ui.components.primitives.PasswordField;

public class LabeledPasswordField extends JPanel {

    private Label label;
    private PasswordField pwField;

    private int distanceLabelTextField;
    private int labelHeight;

    public LabeledPasswordField() {
        super();
        super.setLayout(null);
        super.setBackground(null);

        label = Labels.smallText();
        pwField = new PasswordField();

        add(label);
        add(pwField);

        distanceLabelTextField = 1;
        labelHeight = 20;
    }

    public void setFieldBackgroundColor(Color color) {
        pwField.setBackground(color);
    }

    public void setText(String text) {
        label.setText(text);
    }

    public PasswordField getPasswordField() {
        return pwField;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        int fieldHeight = height - labelHeight - distanceLabelTextField;

        label.setBounds(0, 0, width, labelHeight);
        pwField.setBounds(0, labelHeight + distanceLabelTextField, width, fieldHeight);
    }

    public void setBorder(Border border) {
        if (pwField == null)
            return;
        
        pwField.setBorder(border);
    }

    public char[] getPassword() {
        return pwField.getPassword();
    }

    public String getPasswordAsString() {
        return new String(getPassword());
    }

    public void setFieldText(String text) {
        pwField.setText(text);
    }

    public void setTextFont(Font font) {
        if (font == null || label == null)
            return;

        label.setFont(font);
    }

    public void setFieldFont(Font font) {
        if (font == null || pwField == null)
            return;

        pwField.setFont(font);
    }

    @Override
    public void addKeyListener(KeyListener l) {
        pwField.addKeyListener(l);
    }

    @Override
    public void setEnabled(boolean enabled) {
        pwField.setEnabled(enabled);
    }

}
