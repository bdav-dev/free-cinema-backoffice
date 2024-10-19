package dev.bdav.fcbo.freeui.core;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public abstract class Page extends JPanel {
    private String pageTitle;

    public Page() {
        pageTitle = null;

        setOpaque(false);
        setLayout(new BorderLayout());
    }

    protected void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public Optional<String> getPageTitle() {
        return Optional.ofNullable(pageTitle);
    }

    public void onVisibilityGain() {
    }

    public void onVisibilityLoss() {
    }

    public void reset() {
    }

    public void refresh() {
    }

}
