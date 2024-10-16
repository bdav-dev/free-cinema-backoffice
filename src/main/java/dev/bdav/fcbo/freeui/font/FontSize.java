package dev.bdav.fcbo.freeui.font;

public enum FontSize {
    SMALL(10f),
    MEDIUM(13f),
    LARGE(18f),
    EXTRA_LARGE(24f);

    private float fontSize;

    private FontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public float value() {
        return fontSize;
    }

}
