package free_ui.components.support;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.swing.border.Border;

public class ShadowBorder implements Border {

    private static final int SHADOW_SIZE = 5;

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D imageGraphics = image.createGraphics();
        
        imageGraphics.setComposite(AlphaComposite.Clear);
        imageGraphics.fillRect(0, 0, width, height);
        imageGraphics.setComposite(AlphaComposite.SrcOver);

        //c.getGraphics().(imageGraphics);

        float[] blurKernel = {
            0.0625f, 0.125f, 0.0625f,
            0.125f, 0.25f, 0.125f,
            0.0625f, 0.125f, 0.0625f 
        };

        Kernel blurKernelObj = new Kernel(3, 3, blurKernel);
        ConvolveOp blurOp = new ConvolveOp(blurKernelObj, ConvolveOp.EDGE_NO_OP, null);

        BufferedImage blurredImage = blurOp.filter(image, null);

        g2d.drawImage(blurredImage, x + SHADOW_SIZE, y + SHADOW_SIZE, null);
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(SHADOW_SIZE, SHADOW_SIZE, 0, 0);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
