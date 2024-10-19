package dev.bdav.fcbo.freeui.components;

import dev.bdav.fcbo.freeui.stacking.Stack;

import javax.swing.*;

public class VerticalScrollPane extends JScrollPane {

    public VerticalScrollPane(Stack stack) {
        super(stack);

        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getVerticalScrollBar().setUnitIncrement(10);

        stack.setUsePreferredSizeOfParent(true);
    }

}
