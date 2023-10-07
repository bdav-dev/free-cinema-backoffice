package free_ui.components;

import assets.AssetManager;
import assets.AssetManager.Images;
import free_ui.components.primitives.Label;

public class Image extends Label {

    public Image(Images image, float scale) {
        setSize((int) (image.width * scale), (int) (image.height * scale));
        setIcon(AssetManager.get().getScaledImageIcon(AssetManager.Images.FcLogo, scale));
    }
    
}
