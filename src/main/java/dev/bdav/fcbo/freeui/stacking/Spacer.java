package dev.bdav.fcbo.freeui.stacking;

import javax.swing.JComponent;

import dev.bdav.fcbo.freeui.sizing.Size;

public class Spacer extends JComponent {
    private final SpacerType type;
    private final Size size;

    private Spacer() {
        type = SpacerType.EXPANDING;
        this.size = null;
    }

    private Spacer(Size size) {
        type = SpacerType.SIZE_BASED;
        this.size = size;
    }

    public static Spacer expanding() {
        return new Spacer();
    }

    public static Spacer custom(Size size) {
        return new Spacer(size);
    }

    public static Spacer fixed(int size) {
        return custom(Size.fixed(size));
    }

    public static Spacer variable(int min, int max) {
        return custom(Size.eagerPreferred(min, max));
    }

    public static Spacer variable(int max) {
        return variable(0, max);
    }

    public void applyToStack(Stack stack) {
        switch (type) {
            case EXPANDING -> stack.addExpandingSpacer();
            case SIZE_BASED -> stack.addSpacer(size);
        }
    }

    private enum SpacerType {
        EXPANDING,
        SIZE_BASED
    }

}
