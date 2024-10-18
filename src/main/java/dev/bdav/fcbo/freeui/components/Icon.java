package dev.bdav.fcbo.freeui.components;

import dev.bdav.fcbo.freeui.configuration.IconConfiguration;
import dev.bdav.fcbo.freeui.icon.IconCodeProvidable;

import javax.swing.*;
import java.awt.*;

public class Icon extends JLabel {
    public static final float DEFAULT_ICON_SIZE = 20;

    public Icon(IconCodeProvidable iconCodeProvidable) {
        this(iconCodeProvidable, DEFAULT_ICON_SIZE);
    }

    public Icon(IconCodeProvidable iconCodeProvidable, float size) {
        setFont(
                IconConfiguration
                        .getConfiguredIconFont()
                        .orElse(new Font(Font.DIALOG, Font.PLAIN, (int) size))
                        .deriveFont(size)
        );
        setText(iconCodeProvidable.getIconCode());
    }

}
