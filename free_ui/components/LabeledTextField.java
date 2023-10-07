package free_ui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.rmi.server.UID;

import javax.swing.JPanel;
import javax.swing.border.Border;

import free_ui.UIDesigner;
import free_ui.components.primitives.Label;
import free_ui.components.primitives.Labels;
import free_ui.components.primitives.TextField;

public class LabeledTextField extends JPanel {
    private Label label;
    private TextField textField;

    private int distanceLabelTextField;
    private int labelHeight;

    public final static int DEFAULT_HEIGHT = 55;

    public LabeledTextField() {
        super();
        super.setLayout(null);
        super.setBackground(null);

        label = Labels.smallText();
        textField = new TextField();

        add(label);
        add(textField);

        distanceLabelTextField = 1;
        labelHeight = 20;

        UIDesigner.setHeight(this, DEFAULT_HEIGHT);
    }

    public LabeledTextField(String text) {
        this();
        setLabelText(text);
    }

    public LabeledTextField(String text, int height) {
        this(text);
        UIDesigner.setHeight(this, height);
    }

    public LabeledTextField dynamicHeight() {
        UIDesigner.setHeight(this, 0);
        return this;
    }

    public LabeledTextField defaultHeight() {
        UIDesigner.setHeight(this, DEFAULT_HEIGHT);
        return this;
    }

    public TextField getTextField() {
        return textField;
    }

    public void setLabelText(String text) {
        label.setText(text);
    }

    @Override
    public void addKeyListener(KeyListener l) {
        textField.addKeyListener(l);
    }

    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        int fieldHeight = height - labelHeight - distanceLabelTextField;

        label.setBounds(0, 0, width, labelHeight);
        textField.setBounds(0, labelHeight + distanceLabelTextField, width, fieldHeight);
    }

    public void setBorder(Border border) {
        if (textField == null)
            return;

            textField.setBorder(border);
        }
    
        public void setFieldBackgroundColor(Color color) {
            textField.setBackground(color);
        }
    
        public void setTextFont(Font font) {
            if (font == null || label == null)
                return;
    
            label.setFont(font);
        }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public void setFieldFont(Font font) {
        if (font == null || textField == null)
            return;

        textField.setFont(font);
    }

    @Override
    public void setEnabled(boolean enabled) {
        textField.setEnabled(enabled);
    }

}
