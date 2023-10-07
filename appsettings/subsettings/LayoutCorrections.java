package appsettings.subsettings;

import appsettings.FromString;
import appsettings.LocalStorageSetting;

public class LayoutCorrections {

    private static final LayoutCorrections instance = new LayoutCorrections();

    private LocalStorageSetting<Integer> x;
    private LocalStorageSetting<Integer> y;
    private LocalStorageSetting<Integer> width;
    private LocalStorageSetting<Integer> height;

    private LayoutCorrections() {
        x = new LocalStorageSetting<Integer>(
                "layoutcorrection_x",
                0,
                FromString.Primitives.INTEGER);

        y = new LocalStorageSetting<Integer>(
                "layoutcorrection_y",
                0,
                FromString.Primitives.INTEGER);

        width = new LocalStorageSetting<Integer>(
                "layoutcorrection_width",
                0,
                FromString.Primitives.INTEGER);

        height = new LocalStorageSetting<Integer>(
                "layoutcorrection_height",
                0,
                FromString.Primitives.INTEGER);
    }

    public static LayoutCorrections getInstance() {
        return instance;
    }

    public void setAll(
            int x,
            int y,
            int width,
            int height) {
        x().set(x);
        y().set(y);
        width().set(width);
        height().set(height);
    }

    public void setAllString(
            String x,
            String y,
            String width,
            String height) {
        x().setString(x);
        y().setString(y);
        width().setString(width);
        height().setString(height);
    }

    public LocalStorageSetting<Integer> x() {
        return x;
    }

    public LocalStorageSetting<Integer> y() {
        return y;
    }

    public LocalStorageSetting<Integer> width() {
        return width;
    }

    public LocalStorageSetting<Integer> height() {
        return height;
    }

}
