package free_ui.stacking;

import java.awt.Color;
import java.util.function.Consumer;
import javax.swing.JComponent;

import dev.UIDebug;
import free_ui.UI;
import free_ui.UIDesigner;

public class HStack extends Stack {
    public HStack() {
        super(0, 0);
    }

    public HStack(int width, int height) {
        super(width, height);
    }

    public HStack(Object... components) {
        this();
        for (Object c : components)
            add(c);
    }

    public HStack(int height, Object... components) {
        this(components);
        setHeight(height);
    }

    public static HStack fit(Object... components) {
        var hstack = new HStack(components);
        hstack.makeFit();
        return hstack;
    }

    public void makeFit() {
        setHeight(getMaxComponentSize(false));
    }

    @Override
    protected void build(Consumer<JComponent> target, int x, int y, int width, int height, int componentPadding) {
        if (UI.getInstance().isDebug)
            target.accept(UIDebug.getDebugSquare(Color.CYAN, x - width / 2, y - height / 2, width, height));

        setComponentPadding(componentPadding);
        setHeight(height);
        setWidth(width);
        setTarget(target);

        updateVariableSizeWeights(false);

        final int freeSpace = calculateFreeSpace(width, componentPadding, false);
        final float sumOfWeights = getSumOfVariableSizeWeights();

        int placementX = x - width / 2;

        for (int i = 0; i < size(); i++) {
            Object element = get(i);

            final boolean hasVariableSize = hasElementVariableSize(i);
            int componentWidth = 0;

            if (element instanceof JComponent component) {
                componentWidth = hasVariableSize
                        ? getPortionOfFreeSpace(freeSpace, getVariableSizeWeight(i), sumOfWeights)
                        : component.getWidth();

                if (component.getHeight() == 0 || component.getHeight() > height)
                    UIDesigner.setHeight(component, height);

                UIDesigner.setWidth(component, componentWidth);

                placeComponent(component, placementX, y);

            } else if (element instanceof Spacer spacer) {
                int spacerSize = spacer.hasFixSize()
                        ? spacer.getPixels()
                        : getPortionOfFreeSpace(freeSpace, getVariableSizeWeight(i), sumOfWeights);

                if (UI.getInstance().isDebug)
                    getTarget().accept(UIDebug.getDebugSquare(Color.BLUE, placementX, (int) (y - height * 0.5 / 2), spacerSize, (int) (height * 0.5)));

                placementX += spacerSize;

            } else if (element instanceof Stack stack) {
                if (stack.hasVariableVerticalSize() || stack.getHeight() > height)
                    stack.setHeight(height);

                if (stack.getComponentPadding() == Stack.INHERIT_COMPONENT_PADDING)
                    stack.setComponentPadding(componentPadding);

                if (stack.hasVariableHorizontalSize())
                    stack.setWidth(getPortionOfFreeSpace(freeSpace, 1f, sumOfWeights));

                stack.build(target, placementX + (stack.getWidth() / 2), y);

                componentWidth = stack.getWidth();
            }

            if (UI.getInstance().isDebug)
                getTarget().accept(UIDebug.getDebugSquare(Color.BLACK, placementX + componentWidth, (int) (y - (height * 0.25 / 2)), (usePadding(i) ? componentPadding : 0), (int)(height * 0.25)));

            placementX += componentWidth + (usePadding(i) ? componentPadding : 0);
        }
    }

    private void placeComponent(JComponent component, int x, int y) {
        component.setLocation(x, y - component.getHeight() / 2);

        if (UI.getInstance().isDebug)
            getTarget().accept(UIDebug.getDebugSquare(Color.YELLOW, component.getX(), component.getY(), component.getWidth(), component.getHeight()));

        getTarget().accept(component);
    }
}
