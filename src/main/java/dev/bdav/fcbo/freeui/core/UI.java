package dev.bdav.fcbo.freeui.core;

import dev.bdav.fcbo.freeui.stacking.StackBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class UI extends JFrame {
    private static UI instance;

    private final NavigationHeader navHeader;
    private final JPanel content;

    private final Stack<Page> pages;

    private static List<Runnable> runAfterInitialization;

    static {
        runAfterInitialization = new ArrayList<>();
    }

    private UI(Consumer<JFrame> init) {
        this.pages = new Stack<>();

        init.accept(this);

        navHeader = new NavigationHeader();
        navHeader.addNavigateBackButtonClickListener(this::pop);

        content = new JPanel();
        content.setLayout(new BorderLayout());

        setResizable(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        add(
                StackBuilder.vertical()
                        .content(
                                navHeader,
                                content
                        )
                        .build()
        );

        setLocationRelativeTo(null);
    }

    public static void launch(
            Supplier<Page> initialPageSupplier,
            Supplier<LookAndFeel> lookAndFeelSupplier,
            Consumer<JFrame> init
    ) {
        try {
            UIManager.setLookAndFeel(lookAndFeelSupplier.get());
        } catch (UnsupportedLookAndFeelException exception) {
            System.err.println("Failed to set LookAndFeel: " + exception.getMessage());
        }

        instance = new UI(init);
        instance.push(initialPageSupplier.get());
        instance.setVisible(true);
        runAfterInitialization.forEach(Runnable::run);
        runAfterInitialization = null;
    }

    public static void runAfterInitialization(Runnable runAfterInit) {
        if (runAfterInitialization == null) {
            throw new IllegalStateException("UI is already initialized");
        }

        runAfterInitialization.add(runAfterInit);
    }

    public static UI get() {
        if (instance == null)
            throw new RuntimeException("The UI hasn't been launched yet.");

        return instance;
    }

    public NavigationHeader getNavHeader() {
        return navHeader;
    }

    public final void replaceAll(Page page) {
        pages.peek().onVisibilityLoss();
        pages.clear();

        push(page);
    }

    public final void replace(Page page) {
        pages.peek().onVisibilityLoss();
        pages.pop();

        pages.push(page);
        displayPage(page);
    }

    public final void push(Page page) {
        if (!pages.isEmpty())
            pages.peek().onVisibilityLoss();

        pages.push(page);
        displayPage(page);
        updateNavBackButtonEnabled();
    }

    public final void pop() {
        if (pages.size() <= 1)
            throw new RuntimeException("Cannot pop last page from stack. To close app use exit()");

        pages.peek().onVisibilityLoss();
        pages.pop();

        displayPage(pages.peek());
        updateNavBackButtonEnabled();
    }

    public void exit() {
        pages.peek().onVisibilityLoss();
        dispose();
        System.exit(0);
    }

    private void updateNavBackButtonEnabled() {
        navHeader.setNavigateBackButtonEnabled(pages.size() > 1);
    }

    private void displayPage(Page page) {
        content.removeAll();
        content.repaint();
        content.revalidate();

        content.add(page);
        revalidate();
        repaint();

        page.onVisibilityGain();
    }

}
