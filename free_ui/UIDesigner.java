package free_ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

import assets.AssetManager;
import assets.AssetManager.Fonts;

public class UIDesigner {
    private static float FONT_SIZE_XL = 20f;
    private static float FONT_SIZE_LG = 18f;
    private static float FONT_SIZE_MD = 16f;
    private static float FONT_SIZE_SM = 14f;

    private UIDesigner() {
    }

    public static void block(JComponent component) {
        component.setEnabled(false);
    }

    public static void unblock(JComponent component) {
        component.setEnabled(true);
    }

    public static void setWidth(JComponent component, int newWidth) {
        component.setSize(newWidth, component.getHeight());
    }

    public static void setHeight(JComponent component, int newHeight) {
        component.setSize(component.getWidth(), newHeight);
    }

    public static Border getThinBorder() {
        return BorderFactory.createStrokeBorder(new BasicStroke(0.5f), Color.BLACK);
    }

    public static Font getRegularXl() {
        return getExtraLarge(AssetManager.Fonts.Ubuntu);
    }

    public static Font getRegularLg() {
        return getLarge(AssetManager.Fonts.Ubuntu);
    }

    public static Font getRegularMd() {
        return getMedium(AssetManager.Fonts.Ubuntu);
    }

    public static Font getRegularSm() {
        return getSmall(AssetManager.Fonts.Ubuntu);
    }


    public static Font getMonoXl() {
        return getExtraLarge(AssetManager.Fonts.UbuntuMono);
    }

    public static Font getMonoLg() {
        return getLarge(AssetManager.Fonts.UbuntuMono);
    }

    public static Font getMonoMd() {
        return getMedium(AssetManager.Fonts.UbuntuMono);
    }

    public static Font getMonoSm() {
        return getSmall(AssetManager.Fonts.UbuntuMono);
    }


    public static Font getBoldXl() {
        return getExtraLarge(AssetManager.Fonts.UbuntuBold);
    }

    public static Font getBoldLg() {
        return getLarge(AssetManager.Fonts.UbuntuBold);
    }

    public static Font getBoldMd() {
        return getMedium(AssetManager.Fonts.UbuntuBold);
    }

    public static Font getBoldSm() {
        return getSmall(AssetManager.Fonts.UbuntuBold);
    }


    private static Font getSmall(Fonts font) {
        return AssetManager.get().getFont(font, FONT_SIZE_SM);
    }

    private static Font getMedium(Fonts font) {
        return AssetManager.get().getFont(font, FONT_SIZE_MD);
    }

    private static Font getLarge(Fonts font) {
        return AssetManager.get().getFont(font, FONT_SIZE_LG);
    }

    private static Font getExtraLarge(Fonts font) {
        return AssetManager.get().getFont(font, FONT_SIZE_XL);
    }
}
