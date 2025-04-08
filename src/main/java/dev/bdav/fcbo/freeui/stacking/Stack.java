package dev.bdav.fcbo.freeui.stacking;

import dev.bdav.fcbo.freeui.sizing.Size;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class Stack extends JPanel {
    private boolean usePreferredSizeOfParent;

    protected List<JComponent> stackComponents;
    private int stackMarginX;
    private int stackMarginY;
    private int componentMargin;
    private JustifyContent justifyContent;
    private AlignContent alignContent;
    private Size stackWidth;
    private Size stackHeight;

    public Stack(Function<JPanel, LayoutManager> layoutManagerSupplier) {
        usePreferredSizeOfParent = false;
        stackComponents = new ArrayList<>();
        stackMarginX = stackMarginY = componentMargin = 0;
        justifyContent = JustifyContent.NONE;
        alignContent = AlignContent.CENTER;

        setLayout(layoutManagerSupplier.apply(this));
        setOpaque(false);
    }

    protected abstract void addExpandingSpacer();

    protected abstract void addSpacer(Size size);

    protected abstract boolean isJustifyContentModeAllowed(JustifyContent justifyContentMode);

    protected abstract boolean isAlignContentModeAllowed(AlignContent alignContentMode);

    protected abstract void applyAlignmentToComponent(JComponent component, AlignContent alignContent);

    protected void rebuildStack() {
        removeAll();
        applyPreContent();
        applyContent();
        applyPostContent();
        applyDimensions();
        revalidate();
    }

    private void applyPreContent() {
        if (
                Set.of(
                                JustifyContent.RIGHT,
                                JustifyContent.BOTTOM,
                                JustifyContent.CENTER,
                                JustifyContent.SPACE_AROUND
                        )
                        .contains(justifyContent)
        ) {
            addExpandingSpacer();
        }
    }

    private void applyContent() {
        for (var component : stackComponents) {
            if (component instanceof Spacer spacer) {
                spacer.applyToStack(this);
                continue;
            }

            applyAlignmentToComponent(component, alignContent);

            add(component);

            boolean isLastComponent = stackComponents.indexOf(component) == stackComponents.size() - 1;

            if (componentMargin != 0 && !isLastComponent) {
                addSpacer(Size.fixed(componentMargin));
            }

            if (
                    !isLastComponent
                            && Set
                            .of(
                                    JustifyContent.SPACE_AROUND,
                                    JustifyContent.SPACE_BETWEEN
                            )
                            .contains(justifyContent)
            ) {
                addExpandingSpacer();
            }
        }
    }

    private void applyPostContent() {
        if (
                List
                        .of(
                                JustifyContent.LEFT,
                                JustifyContent.TOP,
                                JustifyContent.CENTER,
                                JustifyContent.SPACE_AROUND
                        )
                        .contains(justifyContent)
        ) {
            addExpandingSpacer();
        }
    }

    private void applyDimensions() {
        setBorder(
                new EmptyBorder(
                        stackMarginY,
                        stackMarginX,
                        stackMarginY,
                        stackMarginX
                )
        );

        if (stackWidth != null)
            stackWidth.applyAsWidth(this);

        if (stackHeight != null)
            stackHeight.applyAsHeight(this);
    }

    public void addStackComponent(JComponent component) {
        stackComponents.add(component);
        rebuildStack();
    }

    public void addStackComponents(JComponent... components) {
        stackComponents.addAll(
                Stream.of(components)
                        .filter(Objects::nonNull)
                        .toList()
        );
        rebuildStack();
    }

    public void setStackComponents(JComponent... components) {
        stackComponents = new ArrayList<>(
                Stream.of(components)
                        .filter(Objects::nonNull)
                        .toList()
        );
        rebuildStack();
    }

    public void clearStackComponents() {
        stackComponents.clear();
        rebuildStack();
    }

    public void setAlignContent(AlignContent alignContent) {
        if (!isAlignContentModeAllowed(alignContent)) {
            throw new IllegalArgumentException(alignContent + " is not allowed on this kind of stack.");
        }

        this.alignContent = alignContent;
        rebuildStack();
    }

    public void setJustifyContent(JustifyContent justifyContent) {
        if (!isJustifyContentModeAllowed(justifyContent)) {
            throw new IllegalArgumentException(justifyContent + " is not allowed on this kind of stack.");
        }

        this.justifyContent = justifyContent;
        rebuildStack();
    }

    public void setStackWidth(Size size) {
        stackWidth = size;
        rebuildStack();
    }

    public void setStackHeight(Size size) {
        stackHeight = size;
        rebuildStack();
    }

    public void setStackMarginX(int stackMarginX) {
        this.stackMarginX = stackMarginX;
        rebuildStack();
    }

    public void setStackMarginY(int stackMarginY) {
        this.stackMarginY = stackMarginY;
        rebuildStack();
    }

    public void setComponentMargin(int componentMargin) {
        this.componentMargin = componentMargin;
        rebuildStack();
    }

    public void setUsePreferredSizeOfParent(boolean usePreferredSizeOfParent) {
        this.usePreferredSizeOfParent = usePreferredSizeOfParent;
    }

    public int getStackMarginX() {
        return stackMarginX;
    }

    public int getStackMarginY() {
        return stackMarginY;
    }

    public int getComponentMargin() {
        return componentMargin;
    }

    public JustifyContent getJustifyContent() {
        return justifyContent;
    }

    public AlignContent getAlignContent() {
        return alignContent;
    }

    public List<JComponent> getStackComponents() {
        return stackComponents;
    }

    public boolean getUsePreferredSizeOfParent() {
        return usePreferredSizeOfParent;
    }

    @Override
    public Dimension getPreferredSize() {
        if (!usePreferredSizeOfParent) {
            return super.getPreferredSize();
        }

        int width = getParent().getSize().width;
        int height = super.getPreferredSize().height;

        return new Dimension(width, height);
    }

}
