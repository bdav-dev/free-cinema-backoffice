package dev.bdav.fcbo.freeui.components;

import javax.swing.JComponent;
import javax.swing.JLabel;

import dev.bdav.fcbo.freeui.stacking.AlignContent;
import dev.bdav.fcbo.freeui.stacking.StackBuilder;
import dev.bdav.fcbo.freeui.stacking.VStack;

public class TopLabel extends VStack {

    public TopLabel(String text, JComponent component) {
        StackBuilder.modify(this)
            .alignContent(AlignContent.LEFT)
            .content(
                new JLabel(text),
                component
            );
    }

}
