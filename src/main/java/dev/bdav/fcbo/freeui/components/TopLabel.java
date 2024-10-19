package dev.bdav.fcbo.freeui.components;

import dev.bdav.fcbo.freeui.stacking.*;

import javax.swing.*;

public class TopLabel extends VStack {

    public TopLabel(String text, JComponent component) {
        var label = StackBuilder.horizontal()
                .content(Spacer.fixed(2), new JLabel(text))
                .justifyContent(JustifyContent.LEFT)
                .build();

        StackBuilder.modify(this)
                .alignContent(AlignContent.LEFT)
                .content(
                        label,
                        component
                );
    }

}
