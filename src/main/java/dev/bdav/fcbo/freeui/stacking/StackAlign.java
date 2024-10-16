package dev.bdav.fcbo.freeui.stacking;

import javax.swing.JComponent;

public class StackAlign {

    private StackAlign() {
    }

    public static Stack left(JComponent... content) {
        return StackBuilder.horizontal()
                .justifyContent(JustifyContent.LEFT)
                .content(content)
                .build();
    }

    public static Stack right(JComponent... content) {
        return StackBuilder.horizontal()
                .justifyContent(JustifyContent.RIGHT)
                .content(content)
                .build();
    }

    public static Stack top(JComponent... content) {
        return StackBuilder.vertical()
                .justifyContent(JustifyContent.TOP)
                .content(content)
                .build();
    }

    public static Stack bottom(JComponent... content) {
        return StackBuilder.vertical()
                .justifyContent(JustifyContent.BOTTOM)
                .content(content)
                .build();
    }

}
