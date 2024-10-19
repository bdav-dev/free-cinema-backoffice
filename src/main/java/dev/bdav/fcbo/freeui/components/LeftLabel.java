package dev.bdav.fcbo.freeui.components;

import dev.bdav.fcbo.freeui.stacking.HStack;
import dev.bdav.fcbo.freeui.stacking.Spacer;
import dev.bdav.fcbo.freeui.stacking.StackBuilder;

import javax.swing.*;

public class LeftLabel extends HStack {

    public LeftLabel(String text, JComponent component) {

        StackBuilder.modify(this)
                .content(
                        new JLabel(text),
                        Spacer.fixed(5),
                        new HorizontalRuler(),
                        Spacer.fixed(5),
                        component
                );
    }

}
