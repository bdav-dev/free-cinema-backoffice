package free_ui.stacking;

import java.awt.Color;
import java.util.function.Consumer;
import javax.swing.JComponent;

import dev.UIDebug;
import free_ui.UI;
import free_ui.UIDesigner;

public class VStack extends Stack {
    public VStack() {
        super(0, 0);
    }

    public VStack(int width, int height) {
        super(width, height);
    }

    public VStack(Object... components) {
        this();
        add(components);
    }

    public VStack(int width, Object... components) {
        this(components);
        setWidth(width);
    }

    public static VStack fit(Object... components) {
        var vstack = new VStack(components);
        vstack.makeFit();
        return vstack;
    }

    public void makeFit() {
        setWidth(getMaxComponentSize(true));
    }

    @Override
    protected void build(Consumer<JComponent> target, int x, int y, int width, int height, int componentPadding) {
        if (UI.getInstance().isDebug)
            target.accept(UIDebug.getDebugSquare(Color.RED, x - width / 2, y - height / 2, width, height));

        setComponentPadding(componentPadding);
        setHeight(height);
        setWidth(width);
        setTarget(target);

        updateVariableSizeWeights(true);

        final int freeSpace = calculateFreeSpace(height, componentPadding, true);
        final float sumOfWeights = getSumOfVariableSizeWeights();

        int placementY = y - height / 2;

        for (int i = 0; i < size(); i++) {
            Object element = get(i);

            final boolean hasVariableSize = hasElementVariableSize(i);
            int componentHeight = 0;

            if (element instanceof JComponent component) {
                componentHeight = hasVariableSize
                        ? getPortionOfFreeSpace(freeSpace, getVariableSizeWeight(i), sumOfWeights)
                        : component.getHeight();

                if (component.getWidth() == 0 || component.getWidth() > width)
                    UIDesigner.setWidth(component, width);

                UIDesigner.setHeight(component, componentHeight);

                placeComponent(component, x, placementY);

            } else if (element instanceof Spacer spacer) {
                int spacerSize = spacer.hasFixSize()
                        ? spacer.getPixels()
                        : getPortionOfFreeSpace(freeSpace, getVariableSizeWeight(i), sumOfWeights);

                if (UI.getInstance().isDebug)
                    getTarget().accept(UIDebug.getDebugSquare(Color.BLUE, x - 20, placementY, 40, spacerSize));

                placementY += spacerSize;

            } else if (element instanceof Stack stack) {
                if (stack.hasVariableHorizontalSize() || stack.getWidth() > width)
                    stack.setWidth(width);

                if (stack.getComponentPadding() == Stack.INHERIT_COMPONENT_PADDING)
                    stack.setComponentPadding(componentPadding);

                if (stack.hasVariableVerticalSize())
                    stack.setHeight(getPortionOfFreeSpace(freeSpace, 1f, sumOfWeights));

                stack.build(target, x, placementY + (stack.getHeight() / 2));

                componentHeight = stack.getHeight();
            }

            if (UI.getInstance().isDebug)
                getTarget().accept(UIDebug.getDebugSquare(Color.BLACK, x - 10, placementY + componentHeight, 20, (usePadding(i) ? componentPadding : 0)));

            placementY += componentHeight + (usePadding(i) ? componentPadding : 0);
        }
    }

    private void placeComponent(JComponent component, int x, int y) {
        component.setLocation(x - (component.getWidth() / 2), y);

        if (UI.getInstance().isDebug)
            getTarget().accept(UIDebug.getDebugSquare(Color.YELLOW, component.getX(), component.getY(), component.getWidth(), component.getHeight()));

        getTarget().accept(component);
    }

}
