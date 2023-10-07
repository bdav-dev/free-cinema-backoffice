package free_ui.stacking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

import javax.swing.JComponent;

public abstract class Stack {
    private ArrayList<Object> uiElements;
    private float[] variableSizeWeights;
    private int componentPadding;
    private Consumer<JComponent> target;

    private int width, height;

    public static final int INHERIT_COMPONENT_PADDING = -1;

    public Stack(int width, int height) {
        uiElements = new ArrayList<>();

        this.width = width;
        this.height = height;
        componentPadding = INHERIT_COMPONENT_PADDING;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void add(Object o) {
        uiElements.add(o);
    }

    public void add(Object... o) {
        uiElements.addAll(Arrays.asList(o));
    }

    public Object get(int index) {
        return uiElements.get(index);
    }

    public int size() {
        return uiElements.size();
    }

    public Stack setComponentPadding(int componentPadding) {
        this.componentPadding = componentPadding;
        return this;
    }

    protected void setTarget(Consumer<JComponent> target) {
        this.target = target;
    }

    public int getComponentPadding() {
        return componentPadding;
    }

    protected Consumer<JComponent> getTarget() {
        return target;
    }

    public void build(Consumer<JComponent> target, int x, int y) {
        build(target, x, y, getWidth(), getHeight(), getComponentPadding());
    }

    protected abstract void build(Consumer<JComponent> target, int x, int y, int width, int height, int componentPadding);

    protected void updateVariableSizeWeights(boolean vstack) {
        variableSizeWeights = new float[size()];

        for (int i = 0; i < size(); i++)
            variableSizeWeights[i] = -1f; // -1: Fix size / no variable size


        for (int i = 0; i < size(); i++) {
            Object element = get(i);

            if (element instanceof JComponent component) {
                if (vstack && component.getHeight() == 0)
                    variableSizeWeights[i] = 1f;

                if (!vstack && component.getWidth() == 0)
                    variableSizeWeights[i] = 1f;

            } else if (element instanceof Spacer spacer && spacer.hasVariableSize()) {
                variableSizeWeights[i] = spacer.getScale();

            } else if (element instanceof Stack stack) {
                if (vstack && stack.hasVariableVerticalSize())
                    variableSizeWeights[i] = 1f;

                if (!vstack && stack.hasVariableHorizontalSize())
                    variableSizeWeights[i] = 1f;

            }
        }
    }

    public boolean hasVariableHorizontalSize() {
        return getWidth() == 0;
    }

    public boolean hasVariableVerticalSize() {
        return getHeight() == 0;
    }

    private boolean hasVariableSize(boolean vstack) {
        return vstack ? hasVariableVerticalSize() : hasVariableHorizontalSize();
    }

    protected boolean hasElementVariableSize(int index) {
        return variableSizeWeights[index] != -1f;
    }

    protected int getNumberOfVariableSizeComponents() {
        int counter = 0;

        for (int i = 0; i < variableSizeWeights.length; i++) {
            if (hasElementVariableSize(i))
                counter++;
        }

        return counter;
    }


    protected float getVariableSizeWeight(int index) {
        return variableSizeWeights[index];
    }

    protected float getSumOfVariableSizeWeights() {
        float sum = 0f;

        for (float weight : variableSizeWeights) {
            if (weight == -1f)
                continue;

            sum += weight;
        }

        return sum;
    }

    protected int calculateFreeSpace(int availableSize, int componentPadding, boolean vstack) {
        int sumOfFixSizeComponentsSizeAndPadding = 0;

        for (int i = 0; i < size(); i++) {
            Object element = get(i);

            int componentSize = 0;


            if (variableSizeWeights[i] != -1f) { // -1 means fix size / no variable size

            } else if (element instanceof JComponent component) {
                componentSize = vstack
                        ? component.getHeight()
                        : component.getWidth();

            } else if (element instanceof Spacer spacer) {
                componentSize = spacer.getPixels();

            } else if (element instanceof Stack stack) {
                componentSize = vstack
                        ? stack.getHeight()
                        : stack.getWidth();
            }

            sumOfFixSizeComponentsSizeAndPadding += componentSize + (usePadding(i) ? componentPadding : 0);
        }

        return availableSize - sumOfFixSizeComponentsSizeAndPadding;

    }

    protected int getPortionOfFreeSpace(int freeSpace, float weight, float sumOfWeights) {
        return (int) (freeSpace * (weight / sumOfWeights));
    }

    protected boolean usePadding(int index) {
        Object currentElement = get(index);
        Object nextElement = (index == size() - 1) ? null : get(index + 1);

        if (nextElement == null)
            return false;

        if (currentElement instanceof Spacer ||
                nextElement instanceof Spacer)
            return false;

        return true;
    }

    protected int cutComponentHeight(JComponent component, int maxHeight) {
        int componentHeight = component.getHeight();

        if (componentHeight > maxHeight) {
            return maxHeight;
        }

        return componentHeight;
    }

    protected int cutComponentWidth(JComponent component, int maxWidth) {
        int componentWidth = component.getWidth();

        if (componentWidth > maxWidth) {
            return maxWidth;
        }

        return componentWidth;
    }

    protected int getMaxComponentSize(boolean vstack) {
        return uiElements.stream()
                .mapToInt(e -> {
                    if (e instanceof JComponent c)
                        return vstack ? c.getWidth() : c.getHeight();

                    if (e instanceof Spacer spacer && spacer.hasFixSize())
                        return spacer.getPixels();

                    if (e instanceof Stack stack && !stack.hasVariableSize(vstack))
                        return vstack ? stack.getWidth() : stack.getHeight();

                    return 0;
                })
                .max()
                .orElse(0);
    }

}
