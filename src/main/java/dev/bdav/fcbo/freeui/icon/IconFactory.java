package dev.bdav.fcbo.freeui.icon;

import java.util.function.Supplier;

import javax.swing.JComponent;
import javax.swing.JLabel;

import dev.bdav.fcbo.freeui.components.Icon;
import dev.bdav.fcbo.freeui.exception.FontInitializationException;
import dev.bdav.fcbo.freeui.font.Fonts;
import dev.bdav.fcbo.freeui.stacking.AlignContent;
import dev.bdav.fcbo.freeui.stacking.Spacer;
import dev.bdav.fcbo.freeui.stacking.Stack;
import dev.bdav.fcbo.freeui.stacking.StackBuilder;

import java.awt.Font;

public class IconFactory {

    private IconFactory() {
    }

    public static Stack standalone(IconCodeProvidable iconCodeProvidable) {
        return StackBuilder.horizontal()
                .content(
                        new Icon(iconCodeProvidable)
                )
                .build();
    }

    public static Icon standalone(IconCodeProvidable iconCodeProvidable, float iconSize) {
        return new Icon(iconCodeProvidable, iconSize);
    }


    public static Stack labeled(IconCodeProvidable iconCodeProvidable, String text) {
        return StackBuilder.horizontal()
                .content(
                        new Icon(iconCodeProvidable),
                        Spacer.fixed(1),
                        new JLabel(text)
                )
                .alignContent(AlignContent.CENTER)
                .build();
    }

    public static Stack labeled(IconCodeProvidable iconCodeProvidable, float iconSize, String text, float textSize) {
        var textLabel = new JLabel(text);
        Fonts.setFontSize(textLabel, textSize);

        return StackBuilder.horizontal()
                .content(
                        new Icon(iconCodeProvidable, iconSize),
                        Spacer.fixed(1),
                        textLabel
                )
                .alignContent(AlignContent.CENTER)
                .build();
    }

    public static Stack labeled(IconCodeProvidable iconCodeProvidable, float iconSize, Supplier<JLabel> labelSupplier) {
        return StackBuilder.horizontal()
                .content(
                        new Icon(iconCodeProvidable, iconSize),
                        Spacer.fixed(1),
                        labelSupplier.get()
                )
                .alignContent(AlignContent.CENTER)
                .build();
    }

}
