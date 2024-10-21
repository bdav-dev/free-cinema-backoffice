package dev.bdav.fcbo.freeui.font;


import dev.bdav.fcbo.freeui.exception.FontInitializationException;

import javax.swing.*;
import java.awt.*;

public class Fonts {

    private static Font monospacedFont;

    static {
        monospacedFont = new Font(Font.MONOSPACED, Font.PLAIN, 13);
    }

    private Fonts() {
    }

    public static void configureMonospaceFont(String resourcePath, float defaultFontSize) throws FontInitializationException {
        try (
                var inputStream = Fonts.class
                        .getClassLoader()
                        .getResourceAsStream(resourcePath)
        ) {
            if (inputStream == null) {
                throw new Exception("Input stream is null");
            }
            monospacedFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(defaultFontSize);

            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(monospacedFont);
        } catch (Exception e) {
            throw new FontInitializationException(e);
        }
    }

    public static Font monospaced() {
        return monospacedFont;
    }

    public static Font monospaced(float fontSize) {
        return monospaced().deriveFont(fontSize);
    }

    public static void setFontSize(JComponent component, float fontSize) {
        component.setFont(
                component.getFont().deriveFont(fontSize)
        );
    }

}
