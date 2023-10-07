package free_ui.stacking;

import java.util.function.Consumer;

import javax.swing.JComponent;

import free_ui.Page;

public class StackManager {
    private Stack parentStack;
    private int usableWidth, usableHeight, windowPadding, elementPadding;

    public StackManager(Stack parentStack, int windowPadding, int elementPadding) {
        this.parentStack = parentStack;
        this.windowPadding = windowPadding;
        this.elementPadding = elementPadding;
    }

    public void build(Page panel) {
        build((component) -> panel.addUIComponent(component),
                panel.getWidth(),
                panel.getHeight());
    }

    public void build(Consumer<JComponent> target, int width, int height) {
        this.usableWidth = width - windowPadding - 16;
        this.usableHeight = height - windowPadding - 37;

        int x = (width / 2) - 7;
        int y = (height / 2) - 19;

        parentStack.build(target, x, y, usableWidth, usableHeight, elementPadding);
    }

    public int getStackHeight() {
        return usableHeight;
    }

    public int getStackWidth() {
        return usableWidth;
    }

    public void setParentStack(Stack parentStack) {
        this.parentStack = parentStack;
    }
}