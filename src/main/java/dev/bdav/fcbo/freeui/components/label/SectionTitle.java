package dev.bdav.fcbo.freeui.components.label;

import dev.bdav.fcbo.freeui.components.HorizontalRuler;
import dev.bdav.fcbo.freeui.sizing.Size;
import dev.bdav.fcbo.freeui.sizing.Sizing;
import dev.bdav.fcbo.freeui.stacking.AlignContent;
import dev.bdav.fcbo.freeui.stacking.HStack;
import dev.bdav.fcbo.freeui.stacking.Spacer;
import dev.bdav.fcbo.freeui.stacking.StackBuilder;

import javax.swing.*;

public class SectionTitle extends HStack {
    private final static int DEFAULT_SIDE_DISTANCE = 40;

    private final HorizontalRuler leftHorizontalRuler;
    private final HorizontalRuler rightHorizontalRuler;

    public SectionTitle(JComponent component) {
        leftHorizontalRuler = new HorizontalRuler();
        rightHorizontalRuler = new HorizontalRuler();

        setComponentLeftAligned();

        StackBuilder.modify(this)
                .content(
                        leftHorizontalRuler,
                        Spacer.fixed(8),
                        component,
                        Spacer.fixed(8),
                        rightHorizontalRuler
                )
                .alignContent(AlignContent.CENTER)
                .width(Size.eagerGrowing())
                .build();
    }

    public void setComponentCenterAligned() {
        Sizing.modify(leftHorizontalRuler)
                .width(Size.eagerGrowing());

        Sizing.modify(rightHorizontalRuler)
                .width(Size.eagerGrowing());
    }

    public void setComponentLeftAligned() {
        setComponentLeftAligned(DEFAULT_SIDE_DISTANCE);
    }

    public void setComponentLeftAligned(int distanceLeft) {
        Sizing.modify(leftHorizontalRuler)
                .width(Size.eagerPreferred(0, distanceLeft));

        Sizing.modify(rightHorizontalRuler)
                .width(Size.growing(0, distanceLeft));
    }

    public void setComponentRightAligned() {
        setComponentRightAligned(DEFAULT_SIDE_DISTANCE);
    }

    public void setComponentRightAligned(int distanceRight) {
        Sizing.modify(leftHorizontalRuler)
                .width(Size.growing(0, distanceRight));

        Sizing.modify(rightHorizontalRuler)
                .width(Size.eagerPreferred(0, distanceRight));
    }

}
