package dev.bdav.fcbo.freeui.icon;

import java.io.IOException;
import java.util.function.Supplier;

import javax.swing.JComponent;
import javax.swing.JLabel;

import dev.bdav.fcbo.freeui.exception.FontInitializationException;
import dev.bdav.fcbo.freeui.stacking.Spacer;
import dev.bdav.fcbo.freeui.stacking.Stack;
import dev.bdav.fcbo.freeui.stacking.StackBuilder;

import java.awt.Font;
import java.awt.FontFormatException;

public class Icon {
    private static final float DEFAULT_ICON_SIZE = 20;
    private static Font iconFont;

    static {
        iconFont = new Font(Font.DIALOG, Font.PLAIN, (int) DEFAULT_ICON_SIZE);
    }

    private Icon() {
    }

    public static void configureIconFont(String pathToIconFont) throws FontInitializationException {
        try {
            var inputStream = Icon.class
                .getClassLoader()
                .getResourceAsStream(pathToIconFont);

            iconFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (Exception e) {
            throw new FontInitializationException(e);
        }
    }

    public static Stack standalone(IconCodeProvidable materialIcon, float iconSize) {
        var icon = new JLabel(materialIcon.getIconCode());
        icon.setFont(iconFont.deriveFont(iconSize));

        return StackBuilder.horizontal()
            .content(icon)
            .build();
    }

    public static Stack standalone(IconCodeProvidable materialIcon) {
        return standalone(materialIcon, DEFAULT_ICON_SIZE);
    }

    public static Stack labeled(IconCodeProvidable materialIcon, Supplier<JComponent> labelSupplier, float iconSize) {
        var icon = new JLabel(materialIcon.getIconCode());
        icon.setFont(iconFont.deriveFont(iconSize));

        var label = labelSupplier.get();

        return StackBuilder.horizontal()
            .content(
                icon,
                label,
                Spacer.fixed(1)
            )
            .componentMargin(4)
            .build();
    }

    public static Stack labeled(IconCodeProvidable materialIcon, Supplier<JComponent> labelSupplier) {
        return labeled(materialIcon, labelSupplier, DEFAULT_ICON_SIZE);
    }

    public static Stack labeled(IconCodeProvidable materialIcon, String labelText, float iconSize) {
        return labeled(materialIcon, () -> new JLabel(labelText), iconSize);
    }

    public static Stack labeled(IconCodeProvidable materialIcon, String labelText) {
        return labeled(materialIcon, labelText, DEFAULT_ICON_SIZE);
    }

}
