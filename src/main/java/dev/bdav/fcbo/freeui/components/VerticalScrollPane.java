package dev.bdav.fcbo.freeui.components;

import javax.swing.JScrollPane;

import dev.bdav.fcbo.freeui.stacking.Stack;

public class VerticalScrollPane extends JScrollPane {
    
    public VerticalScrollPane(Stack stack) {
        super(stack);

        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getVerticalScrollBar().setUnitIncrement(10);

        stack.setUsePreferredSizeOfParent(true);
    }

}
