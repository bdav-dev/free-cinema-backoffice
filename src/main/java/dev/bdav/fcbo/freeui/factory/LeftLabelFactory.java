package dev.bdav.fcbo.freeui.factory;

import dev.bdav.fcbo.freeui.components.LeftLabel;
import dev.bdav.fcbo.freeui.sizing.Size;
import dev.bdav.fcbo.freeui.sizing.Sizing;

import javax.swing.*;

public class LeftLabelFactory {
    private final static int DEFAULT_LABELED_HEIGHT = 35;

    public static LeftLabel of(String text, JComponent component, int componentMaxWidth) {
        return of(text, component, componentMaxWidth, DEFAULT_LABELED_HEIGHT);
    }

    public static LeftLabel of(String text, JComponent component, int componentMaxWidth, int height) {
        Sizing.modify(component)
                .width(Size.eagerPreferred(0, componentMaxWidth));

        var label = new LeftLabel(text, component);
        Sizing.modify(label)
                .height(Size.fixed(height));

        return label;
    }

}
