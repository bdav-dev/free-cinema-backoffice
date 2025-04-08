package dev.bdav.fcbo.freeui.components;

import javax.swing.*;
import java.awt.*;

public class SpecialButton extends JButton {
    private final Color defaultForegroundColor;
    private final Color defaultBackgroundColor;

    private SpecialButton(Variant variant) {
        defaultForegroundColor = getForeground();
        defaultBackgroundColor = getBackground();
        setVariant(variant);
    }

    public SpecialButton(String text, Variant variant) {
        this(variant);
        setText(text);
    }

    public SpecialButton(JComponent component, Variant variant) {
        this(variant);
        add(component);
    }

    public void setVariant(Variant variant) {
        if(variant == Variant.DEFAULT) {
            setBackground(defaultBackgroundColor);
            setForeground(defaultForegroundColor);
            return;
        }

        setBackground(variant.getBackgroundColor());
        setForeground(
                variant.getBackgroundColor() == null
                        ? defaultForegroundColor
                        : Color.WHITE
        );
    }

    public enum Variant {
        PRIMARY(new Color(54, 116, 240)),
        PRIMARY_RED(new Color(224, 36, 26)),
        PRIMARY_GREEN(new Color(21, 132, 67)),
        TERTIARY(null),
        DEFAULT(null);

        private final Color backgroundColor;

        Variant(Color backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public Color getBackgroundColor() {
            return backgroundColor;
        }
    }

}
