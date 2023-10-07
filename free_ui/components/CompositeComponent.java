package free_ui.components;

import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CompositeComponent extends JPanel {

    private JComponent baseComponent;

    private ArrayList<JComponent> topExtensions, topCuts, rightExtensions, rightCuts, bottomExtensions, bottomCuts,
            leftExtensions, leftCuts;

    private int baseComponentHeight, baseComponentWidth, padding;

    public CompositeComponent(JComponent baseComponent, int width, int height, int padding) {
        super();
        super.setLayout(null);
        super.setBackground(null);

        topExtensions = new ArrayList<>();
        topCuts = new ArrayList<>();
        rightExtensions = new ArrayList<>();
        rightCuts = new ArrayList<>();
        bottomExtensions = new ArrayList<>();
        bottomCuts = new ArrayList<>();
        leftExtensions = new ArrayList<>();
        leftCuts = new ArrayList<>();

        this.padding = padding;

        this.baseComponent = baseComponent;
        add(baseComponent);

        baseComponentHeight = height;
        baseComponentWidth = width;
    }

    public void addExtensionComponent(JComponent extensionComponent, PlacementType placementType, int extensionLength,
            boolean extend) {
        if (extend) {
            switch (placementType) {
                case TOP:
                    setDimensions(extensionComponent, 0, extensionLength);
                    topExtensions.add(0, extensionComponent);
                    break;

                case RIGHT:
                    setDimensions(extensionComponent, extensionLength, 0);
                    rightExtensions.add(0, extensionComponent);
                    break;

                case BOTTOM:
                    setDimensions(extensionComponent, 0, extensionLength);
                    bottomExtensions.add(extensionComponent);
                    break;

                case LEFT:
                    setDimensions(extensionComponent, extensionLength, 0);
                    leftExtensions.add(extensionComponent);
                    break;
            }
        } else {
            switch (placementType) {
                case TOP:
                    setDimensions(extensionComponent, 0, extensionLength);
                    topCuts.add(0, extensionComponent);
                    break;

                case RIGHT:
                    setDimensions(extensionComponent, extensionLength, 0);
                    rightCuts.add(0, extensionComponent);
                    break;

                case BOTTOM:
                    setDimensions(extensionComponent, 0, extensionLength);
                    bottomCuts.add(extensionComponent);
                    break;

                case LEFT:
                    setDimensions(extensionComponent, extensionLength, 0);
                    leftCuts.add(extensionComponent);
                    break;
            }

        }
        add(extensionComponent);
        updateBounds();
    }

    public void updateBounds() {
        updateBounds(getX(), getY());
    }

    public void updateBounds(int x, int y) {

        int combinedHeight = baseComponentHeight;
        combinedHeight += getSumOfHeightsWithPadding(topExtensions);
        combinedHeight += getSumOfHeightsWithPadding(bottomExtensions);

        int combinedWidth = baseComponentWidth;
        combinedWidth += getSumOfWidthsWithPadding(leftExtensions);
        combinedWidth += getSumOfWidthsWithPadding(rightExtensions);

        super.setBounds(x, y, combinedWidth, combinedHeight);

        int placementY = 0;
        int placementX = getSumOfWidthsWithPadding(leftExtensions);

        for (JComponent c : topExtensions) {
            c.setBounds(placementX, placementY, baseComponentWidth, c.getHeight());
            placementY += c.getHeight() + padding;
        }

        buildBaseComponent(placementX, placementY);

        placementY += baseComponent.getHeight();

        for (JComponent c : bottomExtensions) {
            placementY += padding;
            c.setBounds(placementX, placementY, baseComponentWidth, c.getHeight());
            placementY += c.getHeight();
        }

        /**/

        placementY = getSumOfHeightsWithPadding(topExtensions);
        placementX = 0;

        for (JComponent c : leftExtensions) {
            c.setBounds(placementX, placementY, c.getWidth(), baseComponentHeight);
            placementX += c.getWidth() + padding;
        }

        placementX += baseComponent.getWidth();

        for (JComponent c : rightExtensions) {
            placementX += padding;
            c.setBounds(placementX, placementY, c.getWidth(), baseComponentHeight);
            placementX += c.getHeight();
        }

    }

    private int getSumOfHeightsWithPadding(ArrayList<JComponent> list) {
        int sum = 0;
        for (JComponent c : list)
            sum += c.getHeight() + padding;

        return sum;
    }

    private int getSumOfWidthsWithPadding(ArrayList<JComponent> list) {
        int sum = 0;
        for (JComponent c : list)
            sum += c.getWidth() + padding;

        return sum;
    }

    private void setDimensions(JComponent component, int width, int height) {
        component.setBounds(component.getX(), component.getY(), width, height);
    }

    private void buildBaseComponent(int x, int y) {
        boolean leftRightCuts = false;
        int remainingBaseComponentWidth = baseComponentWidth
                - getSumOfWidthsWithPadding(leftCuts)
                - getSumOfWidthsWithPadding(rightCuts);

        int remainingBaseComponentHeight = baseComponentHeight
                - getSumOfHeightsWithPadding(topCuts)
                - getSumOfHeightsWithPadding(bottomCuts);

        if (leftCuts.size() != 0 || rightCuts.size() != 0)
            leftRightCuts = true;


        if (topCuts.size() == 0 && bottomCuts.size() == 0 && !leftRightCuts) {
            baseComponent.setBounds(x, y, baseComponentWidth, remainingBaseComponentHeight);
            return;
        }

        if (leftRightCuts) {

            int placementX = x;
            for (JComponent c : leftCuts) {
                c.setBounds(placementX, y, c.getWidth(), baseComponentHeight);
                placementX += c.getWidth() + padding;
            }

            baseComponent.setBounds(placementX, y, remainingBaseComponentWidth, baseComponentHeight);
            placementX += baseComponent.getWidth();

            for (JComponent c : rightCuts) {
                placementX += padding;
                c.setBounds(placementX, y, c.getWidth(), baseComponentHeight);
                placementX += c.getWidth();
            }
            return;
        }

        int placementY = y;
        for (JComponent c : leftCuts) {
            c.setBounds(x, placementY, baseComponentWidth, c.getHeight());
            placementY += c.getHeight() + padding;
        }

        baseComponent.setBounds(x, placementY, baseComponentWidth, remainingBaseComponentHeight);
        placementY += baseComponent.getHeight();

        for (JComponent c : rightCuts) {
            placementY += padding;
            c.setBounds(x, placementY, baseComponentWidth, c.getHeight());
            placementY += c.getHeight();
        }

        return;
    }

    public ArrayList<JComponent> getAllComponents() {
        ArrayList<JComponent> allComponents = new ArrayList<>();

        allComponents.addAll(topExtensions);
        allComponents.addAll(topCuts);
        allComponents.addAll(rightExtensions);
        allComponents.addAll(rightCuts);
        allComponents.addAll(bottomExtensions);
        allComponents.addAll(bottomCuts);
        allComponents.addAll(leftExtensions);
        allComponents.addAll(leftCuts);
        allComponents.add(baseComponent);

        return allComponents;
    }

}