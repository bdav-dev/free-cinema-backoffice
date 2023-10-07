package free_ui.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.Adjustable;

public class ScrollBarFactory {

    private ScrollBarFactory() {
    }

    public static JScrollBar createVerticalScrollBar(Color thumbColor, Color trackColor, Color arrowColor) {
        return modifyScrollBar(new JScrollBar(Adjustable.VERTICAL), thumbColor, trackColor, arrowColor);
    }

    public static JScrollBar createHorizontalScrollBar(Color thumbColor, Color trackColor, Color arrowColor) {
        return modifyScrollBar(new JScrollBar(Adjustable.HORIZONTAL), thumbColor, trackColor, arrowColor);
    }

    public static JScrollBar modifyScrollBar(JScrollBar scrollBar, Color customThumbColor, Color customTrackColor, Color customArrowColor) {

        scrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbHighlightColor = customThumbColor;
                thumbLightShadowColor = customThumbColor;
                thumbDarkShadowColor = customThumbColor;
                thumbColor = customThumbColor;
                trackColor = customTrackColor;
                trackHighlightColor = customArrowColor;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return getBasicArrowButton(orientation);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return getBasicArrowButton(orientation);
            }

            @SuppressWarnings("serial")
            private BasicArrowButton getBasicArrowButton(int orientation) {
                return new BasicArrowButton(orientation, customTrackColor, customTrackColor, customArrowColor,
                        customArrowColor) {
                    @Override
                    public void paint(Graphics g) {
                        Color origColor;
                        boolean isEnabled;
                        int w, h, size;

                        w = getSize().width;
                        h = getSize().height;
                        origColor = g.getColor();
                        isEnabled = isEnabled();

                        g.setColor(getBackground());
                        g.fillRect(1, 1, w - 2, h - 2);

                        /// Draw the proper Border

                        g.setColor(trackColor);
                        g.drawRect(0, 0, w - 1, h - 1);

                        // If there's no room to draw arrow, bail
                        if (h < 5 || w < 5) {
                            g.setColor(origColor);
                            return;
                        }

                        g.translate(1, 1);

                        // Draw the arrow
                        size = Math.min((h - 4) / 3, (w - 4) / 3);
                        size = Math.max(size, 2);
                        paintTriangle(g, (w - size) / 2, (h - size) / 2, size, direction, isEnabled);

                        g.translate(-1, -1);
                        g.setColor(origColor);
                    }

                };
            }
        });

        return scrollBar;
    }

}
