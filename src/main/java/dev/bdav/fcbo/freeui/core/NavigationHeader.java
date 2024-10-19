package dev.bdav.fcbo.freeui.core;

import dev.bdav.fcbo.freeui.sizing.Size;
import dev.bdav.fcbo.freeui.sizing.Sizing;
import dev.bdav.fcbo.freeui.stacking.AlignContent;
import dev.bdav.fcbo.freeui.stacking.HStack;
import dev.bdav.fcbo.freeui.stacking.Stack;
import dev.bdav.fcbo.freeui.stacking.StackBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NavigationHeader extends HStack {
    private final JButton navigateBackButton;
    private final JPanel navHeaderComponentWrapper;
    private final int BACK_BUTTON_WIDTH = 50;

    private JComponent navHeaderComponent;
    private List<Runnable> navigateBackButtonClickListener;

    public NavigationHeader() {
        this.navigateBackButtonClickListener = new ArrayList<>();

        var backLabel = new JLabel("Back");
        backLabel.setAlignmentX(CENTER_ALIGNMENT);

        navigateBackButton = new JButton();
        navigateBackButton.setMargin(new Insets(0, 0, 0, 0));
        navigateBackButton.add(backLabel);
        navigateBackButton.addActionListener(
                event -> navigateBackButtonClickListener.forEach(Runnable::run)
        );
        Sizing.modify(navigateBackButton)
                .width(Size.fixed(BACK_BUTTON_WIDTH))
                .height(Size.lazyGrowing(30));

        navHeaderComponentWrapper = new JPanel();
        navHeaderComponentWrapper.setLayout(new BorderLayout());
        Sizing.modify(navHeaderComponentWrapper)
                .width(Size.eagerGrowing());

        StackBuilder.modify((Stack) this)
                .componentMargin(4)
                .stackMarginX(4)
                .stackMarginY(4)
                .content(
                        navigateBackButton,
                        navHeaderComponentWrapper
                )
                .alignContent(AlignContent.TOP)
                .width(Size.eagerGrowing())
                .height(Size.fixed(36));
    }

    public Optional<JComponent> getNavHeaderComponent() {
        return Optional.ofNullable(navHeaderComponent);
    }

    public void setNavHeaderComponent(JComponent component) {
        this.navHeaderComponent = component;

        navHeaderComponentWrapper.removeAll();
        navHeaderComponentWrapper.add(component);
        navHeaderComponentWrapper.revalidate();
    }

    public void addNavigateBackButtonClickListener(Runnable onClickAction) {
        navigateBackButtonClickListener.add(onClickAction);
    }

    public void setNavigateBackButtonEnabled(boolean enabled) {
        navigateBackButton.setEnabled(enabled);
    }

    public void setNavigateBackButtonContent(JComponent component) {
        navigateBackButton.removeAll();
        navigateBackButton.add(component);
    }
}
