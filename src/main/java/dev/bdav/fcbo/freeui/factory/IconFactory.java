package dev.bdav.fcbo.freeui.factory;

import dev.bdav.fcbo.freeui.components.Icon;
import dev.bdav.fcbo.freeui.interfaces.IconCodeProvidable;
import dev.bdav.fcbo.freeui.stacking.AlignContent;
import dev.bdav.fcbo.freeui.stacking.Spacer;
import dev.bdav.fcbo.freeui.stacking.Stack;
import dev.bdav.fcbo.freeui.stacking.StackBuilder;

import javax.swing.*;
import java.util.function.Consumer;

public class IconFactory {

    private IconFactory() {
    }

    public static Stack wrap(Icon icon) {
        return StackBuilder.horizontal()
                .content(icon)
                .build();
    }

    public static Stack wrap(Icon icon, JLabel label) {
        return StackBuilder.horizontal()
                .content(icon, Spacer.fixed(3), label)
                .alignContent(AlignContent.CENTER)
                .build();
    }

    public static Stack standalone(IconCodeProvidable iconCodeProvidable) {
        return StackBuilder.horizontal()
                .content(new Icon(iconCodeProvidable))
                .build();
    }

    public static Stack standalone(IconCodeProvidable iconCodeProvidable, float iconSize) {
        return wrap(new Icon(iconCodeProvidable, iconSize));
    }

    public static Stack labeled(IconCodeProvidable iconCodeProvidable, JLabel label) {
        return wrap(
                new Icon(iconCodeProvidable, label.getFont().getSize() + 5),
                label
        );
    }

    public static Stack labeled(IconCodeProvidable iconCodeProvidable, String text) {
        return labeled(iconCodeProvidable, new JLabel(text));
    }

}
