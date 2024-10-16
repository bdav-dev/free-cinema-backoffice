package dev.bdav.fcbo.freeui.sizing;

import javax.swing.JComponent;


public class Sizing {
    private final JComponent component;

    private Sizing(JComponent component) {
        this.component = component;
    }

    public static Sizing modify(JComponent component) {
        return new Sizing(component);
    }

    public Sizing width(Size size) {
        size.applyAsWidth(component);
        return this;
    }

    public Sizing height(Size size) {
        size.applyAsHeight(component);
        return this;
    }

    public void widthAndHeight(Size size) {
        width(size);
        height(size);
    }

}
