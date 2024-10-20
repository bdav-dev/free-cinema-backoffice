package dev.bdav.fcbo.freeui.components;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    int cornerRadius = 20;

    public RoundedPanel(Component component) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // setBackground(UIManager.getDefaults().getColor("Component.borderColor"));
        setBackground(new Color(18, 18, 18));
        add(component);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setColor(getBackground());

        graphics.fillRoundRect(
                0, 0, width, height, cornerRadius, cornerRadius
        );

    }

}
