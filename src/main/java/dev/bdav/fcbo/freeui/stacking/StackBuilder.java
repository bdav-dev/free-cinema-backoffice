package dev.bdav.fcbo.freeui.stacking;

import java.util.function.Supplier;

import javax.swing.JComponent;

import dev.bdav.fcbo.freeui.sizing.Size;

public class StackBuilder {
    private final Stack stack;

    private StackBuilder(Supplier<Stack> stackSupplier) {
        stack = stackSupplier.get();
    }

    public static StackBuilder vertical() {
        return new StackBuilder(VStack::new);
    }

    public static StackBuilder horizontal() {
        return new StackBuilder(HStack::new);
    }

    public static StackBuilder modify(Stack stack) {
        return new StackBuilder(() -> stack);
    }

    public StackBuilder componentMargin(int margin) {
        stack.setComponentMargin(margin);
        return this;
    }

    public StackBuilder stackMargin(int margin) {
        stack.setStackMarginX(margin);
        stack.setStackMarginY(margin);
        return this;
    }

    public StackBuilder stackMarginX(int marginX) {
        stack.setStackMarginX(marginX);
        return this;
    }

    public StackBuilder stackMarginY(int marginY) {
        stack.setStackMarginY(marginY);
        return this;
    }


    public StackBuilder width(Size width) {
        stack.setStackWidth(width);
        return this;
    }

    public StackBuilder height(Size height) {
        stack.setStackHeight(height);
        return this;
    }

    public StackBuilder justifyContent(JustifyContent justifyContent) {
        stack.setJustifyContent(justifyContent);
        return this;
    }

    public StackBuilder alignContent(AlignContent alignContent) {
        stack.setAlignContent(alignContent);
        return this;
    }

    public StackBuilder content(JComponent... components) {
        stack.setStackComponents(components);
        return this;
    }

    public StackBuilder addContent(JComponent... components) {
        stack.addStackComponents(components);
        return this;
    }

    public Stack build() {
        return stack;
    }

}
