package dev.bdav.fcbo.freeui.components;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class Button extends JButton {
    private Variant variant;
    private Color color;

    public Button() {
        this.color = Color.DEFAULT;
        this.variant = Variant.SECONDARY;
        abc();
    }

    public void setColor(Color color) {
        this.color = color;
        abc();
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
        abc();
    }

    public void abc() {
        switch (variant) {
            case PRIMARY -> {
                setBackground(color.getColor());
                setFont(getFont().deriveFont(Font.BOLD));
            }
            case SECONDARY -> {
                setForeground(color.getColor());
            }
            case TERTIARY -> {
                setBackground(null);
            }
        }
    }

    public enum Variant {
        PRIMARY,
        SECONDARY,
        TERTIARY
    }

    public enum Color {
        DEFAULT(new java.awt.Color(102, 168, 255)),
        CONTRAST(null),
        DANGER(new java.awt.Color(224, 36, 26)),
        WARNING(new java.awt.Color(255, 255, 255)),
        SUCCESS(new java.awt.Color(21, 132, 67));

        private final java.awt.Color color;

        Color(java.awt.Color color) {
            this.color = color;
        }

        public java.awt.Color getColor() {
            return color;
        }
    }

}
