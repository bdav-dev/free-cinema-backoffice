package debug;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import free_ui.Page;

public class UIDebug {

    public static void addDebugCross(Page page, int x, int y) {
        var xLine = new JPanel();
        xLine.setBounds(x, y - 1, 999, 3);
        xLine.setOpaque(true);
        xLine.setBackground(Color.RED);

        var yLine = new JPanel();
        yLine.setBounds(x - 3, y, 3, 999);
        yLine.setOpaque(true);
        yLine.setBackground(Color.RED);

        page.addUIComponents(xLine, yLine);
    }

    public static JPanel getDebugSquare(Color color, int x, int y, int width, int height) {
        var panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 0.2f));
                
                g2d.setColor(color);
                g2d.fillRect(0, 0, width, height);
            }
        };
        panel.setOpaque(true);
        panel.setBounds(x, y, width, height);
        
        return panel;
    }

}
