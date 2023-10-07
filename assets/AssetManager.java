package assets;

import java.awt.Font;
import java.awt.Image;
import java.util.Objects;

import javax.swing.ImageIcon;

import exceptions.AssetNotFoundException;

/**
 * This class manages various assets (Colors, Images, ImageIcons, Fonts) needed
 * throughout the project.
 * 
 * @author David Berezowski
 */
public class AssetManager {

    private static AssetManager instance;

    private AssetManager() {
    }

    public static AssetManager get() {
        if (instance == null)
            instance = new AssetManager();

        return instance;
    }

    /* Fonts */
    public static enum Fonts {
        Ubuntu("Ubuntu-R.ttf"), UbuntuMono("UbuntuMono-R.ttf"), UbuntuBold("Ubuntu-B.ttf");

        private String filename;

        private Fonts(String filename) {
            this.filename = filename;
        }
    }

    public Font getFont(Fonts font, float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT,
                    Objects.requireNonNull(
                            getClass().getClassLoader().getResourceAsStream("assets/fonts/" + font.filename)))
                    .deriveFont(size);
        } catch (Exception e) {
            throw new AssetNotFoundException(font.filename);
        }
    }

    public Font getFont(Fonts font) {
        return getFont(font, 12f);
    }

    /* Images */
    public enum Images {
        IconGear("icon_gear.png", 50, 50), FcLogo("free-cinema-logo.png", 472, 200);

        private String filename;
        public final int width;
        public final int height;

        private Images(String filename, int width, int height) {
            this.filename = filename;
            this.width = width;
            this.height = height;
        }
    }

    public ImageIcon getImageIcon(Images image) {
        try {
            return new ImageIcon(getClass().getResource("images/" + image.filename));
        } catch (Exception e) {
            throw new AssetNotFoundException(image.filename);
        }
    }

    public Image getImage(Images image) {
        return getImageIcon(image).getImage();
    }

    public int getImageHeight(Images image) {
        return getImage(image).getHeight(null);
    }

    public Integer getImageWidth(Images image) {
        return getImage(image).getWidth(null);
    }

    public Image getScaledImage(Images image, int imageWidth, int imageHeight) {
        ImageIcon imageIcon = getImageIcon(image);
        return scaleImage(imageIcon.getImage(), imageWidth, imageHeight);
    }

    public Image getScaledImage(Images image, float factor) {
        ImageIcon imageIcon = getImageIcon(image);
        return scaleImage(imageIcon.getImage(), factor);
    }

    public ImageIcon getScaledImageIcon(Images image, int imageWidth, int imageHeight) {
        Image scaledImage = getScaledImage(image, imageWidth, imageHeight);
        return new ImageIcon(scaledImage);
    }

    public ImageIcon getScaledImageIcon(Images image, float factor) {
        Image scaledImage = getScaledImage(image, factor);
        return new ImageIcon(scaledImage);
    }

    public Image scaleImage(Image image, int width, int height) {
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public Image scaleImage(Image image, float factor) {
        return image.getScaledInstance((int) (image.getWidth(null) * factor),
                (int) (image.getHeight(null) * factor),
                Image.SCALE_SMOOTH);
    }

}
