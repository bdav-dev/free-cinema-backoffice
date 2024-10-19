package dev.bdav.fcbo.freeui.sizing;

import javax.swing.*;
import java.awt.*;


public record Size(
        int min,
        int preferred,
        int max
) {

    public static final int EXPANDING = 999999;

    public static Size of(int min, int preferred, int max) {
        return new Size(min, preferred, max);
    }

    public static Size eagerPreferred(int min, int max) {
        return of(min, max, max);
    }

    public static Size lazyPreferred(int min, int max) {
        return of(min, min, max);
    }

    public static Size fixed(int fixedSize) {
        return of(fixedSize, fixedSize, fixedSize);
    }

    public static Size growing(int min, int preferred, float percentage) {
        return of(min, preferred, (int) (EXPANDING * percentage));
    }

    public static Size growing(int min, int preferred) {
        return growing(min, preferred, 1f);
    }

    public static Size eagerGrowing(int min, float percentage) {
        return growing(min, (int) (EXPANDING * percentage), percentage);
    }

    public static Size eagerGrowing(int min) {
        return eagerGrowing(min, 1f);
    }

    public static Size eagerGrowing() {
        return eagerGrowing(0, 1f);
    }

    public static Size lazyGrowing(int min, float percentage) {
        return growing(min, min, percentage);
    }

    public static Size lazyGrowing(int min) {
        return lazyGrowing(min, 1f);
    }

    public static Size lazyGrowing() {
        return lazyGrowing(0, 1f);
    }


    public void applyAsWidth(JComponent component) {
        applyWidth(component, min, preferred, max);
    }

    public void applyAsHeight(JComponent component) {
        applyHeight(component, min, preferred, max);
    }

    public static void applyWidth(JComponent component, int min, int preferred, int max) {
        component.setMinimumSize(new Dimension(min, component.getMinimumSize().height));
        component.setPreferredSize(new Dimension(preferred, component.getPreferredSize().height));
        component.setMaximumSize(new Dimension(max, component.getMaximumSize().height));
    }

    public static void applyHeight(JComponent component, int min, int preferred, int max) {
        component.setMinimumSize(new Dimension(component.getMinimumSize().width, min));
        component.setPreferredSize(new Dimension(component.getPreferredSize().width, preferred));
        component.setMaximumSize(new Dimension(component.getMaximumSize().width, max));
    }

}
