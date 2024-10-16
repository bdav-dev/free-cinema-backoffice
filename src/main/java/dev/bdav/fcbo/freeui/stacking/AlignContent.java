package dev.bdav.fcbo.freeui.stacking;

import javax.swing.JComponent;

public enum AlignContent {
    CENTER(JComponent.CENTER_ALIGNMENT),
    TOP(JComponent.TOP_ALIGNMENT), // only availabe on HStack
    BOTTOM(JComponent.BOTTOM_ALIGNMENT), // only availabe on HStack
    LEFT(JComponent.LEFT_ALIGNMENT), // only availabe on VStack
    RIGHT(JComponent.RIGHT_ALIGNMENT); // only availabe on VStack

    public final float alignmentValue;

    private AlignContent(float alignmentValue) {
        this.alignmentValue = alignmentValue;
    }

}
