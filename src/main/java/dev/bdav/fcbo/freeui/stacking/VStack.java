package dev.bdav.fcbo.freeui.stacking;

import dev.bdav.fcbo.freeui.sizing.Size;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class VStack extends Stack {

    public VStack() {
        super(stack -> new BoxLayout(stack, BoxLayout.Y_AXIS));
    }

    @Override
    protected void addExpandingSpacer() {
        add(Box.createVerticalGlue());
    }

    @Override
    protected void addSpacer(Size size) {
        add(
                new Box.Filler(
                        new Dimension(0, size.min()),
                        new Dimension(0, size.preferred()),
                        new Dimension(0, size.max())
                )
        );
    }

    @Override
    protected boolean isAlignContentModeAllowed(AlignContent alignContentMode) {
        return !Set
                .of(
                        AlignContent.TOP,
                        AlignContent.BOTTOM
                )
                .contains(alignContentMode);
    }

    @Override
    protected boolean isJustifyContentModeAllowed(JustifyContent justifyContentMode) {
        return !Set
                .of(
                        JustifyContent.LEFT,
                        JustifyContent.RIGHT
                )
                .contains(justifyContentMode);
    }

    @Override
    protected void applyAlignmentToComponent(JComponent component, AlignContent alignContent) {
        component.setAlignmentX(alignContent.alignmentValue);
    }

}
