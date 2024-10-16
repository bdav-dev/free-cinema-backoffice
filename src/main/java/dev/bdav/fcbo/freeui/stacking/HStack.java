package dev.bdav.fcbo.freeui.stacking;

import java.awt.Dimension;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

import dev.bdav.fcbo.freeui.sizing.Size;

public class HStack extends Stack {

    public HStack() {
        super(stack -> new BoxLayout(stack, BoxLayout.X_AXIS));
    }

    @Override
    protected void addExpandingSpacer() {
        add(Box.createHorizontalGlue());
    }

    @Override
    protected void addSpacer(Size size) {
        add(
            new Box.Filler(
                new Dimension(size.min(), 0),
                new Dimension(size.preferred(), 0),
                new Dimension(size.max(), 0)
            )
        );
    }

    @Override
    protected boolean isAlignContentModeAllowed(AlignContent alignContentMode) {
        return !Set
            .of(
                AlignContent.LEFT,
                AlignContent.RIGHT
            )
            .contains(alignContentMode);
    }

    @Override
    protected boolean isJustifyContentModeAllowed(JustifyContent justifyContentMode) {
        return !Set
            .of(
                JustifyContent.TOP,
                JustifyContent.BOTTOM
            )
            .contains(justifyContentMode);
    }

    @Override
    protected void applyAlignmentToComponent(JComponent component, AlignContent alignContent) {
        component.setAlignmentY(alignContent.alignmentValue);
    }

}
