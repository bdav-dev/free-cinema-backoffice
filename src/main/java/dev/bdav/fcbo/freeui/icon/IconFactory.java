package dev.bdav.fcbo.freeui.icon;

import dev.bdav.fcbo.freeui.components.Icon;
import dev.bdav.fcbo.freeui.stacking.AlignContent;
import dev.bdav.fcbo.freeui.stacking.Spacer;
import dev.bdav.fcbo.freeui.stacking.Stack;
import dev.bdav.fcbo.freeui.stacking.StackBuilder;

import javax.swing.*;

public class IconFactory {

    private IconFactory() {
    }

    public static Stack standalone(IconCodeProvidable iconCodeProvidable) {
        return StackBuilder.horizontal()
                .content(new Icon(iconCodeProvidable))
                .build();
    }

    public static Stack standalone(IconCodeProvidable iconCodeProvidable, float iconSize) {
        return StackBuilder.horizontal()
                .content(new Icon(iconCodeProvidable, iconSize))
                .build();
    }

    public static Stack labeled(IconCodeProvidable iconCodeProvidable, JLabel label) {
        return StackBuilder.horizontal()
                .content(
                        new Icon(iconCodeProvidable, label.getFont().getSize() + 5),
                        Spacer.fixed(3),
                        label
                )
                .alignContent(AlignContent.CENTER)
                .build();
    }

}
