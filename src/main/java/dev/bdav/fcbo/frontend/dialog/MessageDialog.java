package dev.bdav.fcbo.frontend.dialog;

import dev.bdav.fcbo.freeui.components.Icon;
import dev.bdav.fcbo.freeui.components.SpecialButton;
import dev.bdav.fcbo.freeui.components.label.H1;
import dev.bdav.fcbo.freeui.factory.IconFactory;
import dev.bdav.fcbo.freeui.factory.TextAreaFactory;
import dev.bdav.fcbo.freeui.interfaces.IconCodeProvidable;
import dev.bdav.fcbo.freeui.sizing.Size;
import dev.bdav.fcbo.freeui.sizing.Sizing;
import dev.bdav.fcbo.freeui.stacking.AlignContent;
import dev.bdav.fcbo.freeui.stacking.JustifyContent;
import dev.bdav.fcbo.freeui.stacking.Spacer;
import dev.bdav.fcbo.freeui.stacking.StackBuilder;
import dev.bdav.fcbo.frontend.icon.GoogleMaterialIcon;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class MessageDialog extends JDialog {
    private final Runnable close = this::dispose;

    public MessageDialog(JFrame parent, Content content) {
        super(parent, true);

        setMinimumSize(new Dimension(400, 300));
        setSize(500, 350);
        setLocationRelativeTo(parent);
        setTitle(content.title);

        var icon = new Icon(content.type.iconCode, 75);
        Sizing.modify(icon)
                .height(Size.fixed(70));
        icon.setForeground(content.type.color);

        var titleH1 = new H1(content.title);

        var descriptionTextBox = TextAreaFactory.textBox(content.description);
        Sizing.modify(descriptionTextBox)
                .widthAndHeight(Size.eagerGrowing());

        var buttons = Stream.of(content.buttonInfo)
                .map(item -> {
                    var button = new SpecialButton(item.text, item.variant);
                    button.addActionListener(e -> item.actionListener.accept(close));
                    return button;
                })
                .toArray(SpecialButton[]::new);


        add(
                StackBuilder.vertical()
                        .content(
                                StackBuilder.horizontal()
                                        .content(
                                                IconFactory.wrap(icon), titleH1
                                        )
                                        .justifyContent(JustifyContent.CENTER)
                                        .alignContent(AlignContent.CENTER)
                                        .componentMargin(10)
                                        .build(),
                                Spacer.fixed(5),
                                descriptionTextBox,
                                Spacer.fixed(10),
                                StackBuilder.horizontal()
                                        .content(
                                                buttons
                                        )
                                        .justifyContent(JustifyContent.RIGHT)
                                        .alignContent(AlignContent.CENTER)
                                        .build()
                        )
                        .stackMargin(15)
                        .componentMargin(5)
                        .build()
        );
    }

    public enum Type {
        INFORMATION(GoogleMaterialIcon.INFO, new Color(54, 116, 240)),
        WARNING(GoogleMaterialIcon.WARNING, new Color(234, 179, 8)),
        ERROR(GoogleMaterialIcon.ERROR, new Color(224, 36, 26));

        private final IconCodeProvidable iconCode;
        private final Color color;

        Type(IconCodeProvidable iconCode, Color color) {
            this.iconCode = iconCode;
            this.color = color;
        }
    }

    public record Content(
            Type type,
            String title,
            String description,
            Button... buttonInfo
    ) {
        public static Content of(Type type, String title, String description, Button... buttonInfo) {
            return new Content(type, title, description, buttonInfo);
        }

        public record Button(
                String text,
                SpecialButton.Variant variant,
                Consumer<Runnable> actionListener
        ) {
            public static Button of(String text, SpecialButton.Variant variant, Consumer<Runnable> actionListener) {
                return new Button(text, variant, actionListener);
            }

            public static Button of(String text, Consumer<Runnable> actionListener) {
                return new Button(text, null, actionListener);
            }
        }
    }


}

