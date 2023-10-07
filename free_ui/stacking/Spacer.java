package free_ui.stacking;

public class Spacer {
    private int pixels;
    private float scale;

    public Spacer() {
        pixels = -1;
        scale = 1f;
    }

    public Spacer(float scale) {
        this.scale = scale;
        pixels = -1;
    }

    public Spacer(int pixels) {
        this.pixels = pixels;
        this.scale = -1f;
    }

    public int getPixels() {
        return pixels;
    }

    public float getScale() {
        return scale;
    }

    public boolean hasVariableSize() {
        return pixels == -1;
    }

    public boolean hasFixSize() {
        return pixels != -1;
    }
}