package dev.bdav.fcbo.freeui.configuration;

import dev.bdav.fcbo.freeui.exception.FontInitializationException;
import dev.bdav.fcbo.freeui.icon.IconFactory;

import java.awt.*;
import java.util.Optional;

public class IconConfiguration {

    private static Font iconFont;

    private IconConfiguration() {
    }

    public static void configureIconFont(String pathToIconFont) throws FontInitializationException {
        try {
            var inputStream = IconFactory.class
                    .getClassLoader()
                    .getResourceAsStream(pathToIconFont);

            if (inputStream == null) {
                throw new Exception("Icon font file not found");
            }

            iconFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (Exception e) {
            throw new FontInitializationException(e);
        }
    }

    public static Optional<Font> getConfiguredIconFont() {
        return Optional.ofNullable(iconFont);
    }

}
