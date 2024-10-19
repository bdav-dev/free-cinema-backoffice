package dev.bdav.fcbo.freeui.components.label;

import javax.swing.*;

public class H1 extends JLabel {

    public H1(String text) {
        super(text);
        putClientProperty("FlatLaf.styleClass", "h1");
    }
}
