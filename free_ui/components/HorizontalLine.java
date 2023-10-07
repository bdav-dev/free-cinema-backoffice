package free_ui.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import free_ui.UIDesigner;
import free_ui.components.primitives.Label;
import free_ui.components.primitives.Labels;

public class HorizontalLine extends JPanel {
    private Label leftLine, rightLine, text;
    private int lineThickness;

    private int textAlignment;

    public static final int DEFAULT_HEIGHT = 30;

    public HorizontalLine() {
        super();
        setLayout(null);
        setBackground(null);

        text = Labels.largeText();
        textAlignment = 2;

        leftLine = new Label();
        leftLine.setOpaque(true);

        rightLine = new Label();
        rightLine.setOpaque(true);

        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.CENTER);

        setLineColor(Color.BLACK);
        setLineThickness(1);

        add(text);
        add(leftLine);
        add(rightLine);
    }

    public HorizontalLine(String text) {
        this();
        setText(text);
    }

    public HorizontalLine(String text, int height) {
        this(text);
        UIDesigner.setHeight(this, height);
    }

    public HorizontalLine(int height) {
        this();
        UIDesigner.setHeight(this, height);
    }

    public HorizontalLine defaultHeight() {
        UIDesigner.setHeight(this, DEFAULT_HEIGHT);
        return this;
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setLineThickness(int thickness) {
        this.lineThickness = thickness;
    }

    public String getText() {
        return text.getText();
    }

    public void setFont(Font font) {
        if (text != null) {
            text.setFont(font);
            setBounds(getX(), getY(), getWidth(), getHeight());
        }
    }

    public void setTextAlignment(int textAlignment) {
        this.textAlignment = textAlignment;
    }

    public void setLineColor(Color lineColor) {
        leftLine.setBackground(lineColor);
        rightLine.setBackground(lineColor);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        if (text.getText() == "") {
            leftLine.setBounds(0, getCenterLinePositionY(height), width, lineThickness);
            return;
        }

        if (textAlignment == 2) {
            int leftIndent = 20;
            int distanceLineText = 20;
            leftLine.setBounds(0, getCenterLinePositionY(height), leftIndent, lineThickness);
            text.setBounds(leftIndent, -2, text.getPreferredSize().width + distanceLineText, height);
            rightLine.setBounds(text.getX() + text.getWidth(), getCenterLinePositionY(height),
                    width - leftIndent - text.getWidth(), lineThickness);
        }

    }

    @Override
    public void setSize(int width, int height) {
        this.setBounds(getX(), getY(), width, height);
    }

    private int getCenterLinePositionY(int height) {
        return (height + lineThickness) / 2 - lineThickness;
    }

}
