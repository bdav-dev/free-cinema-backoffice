package free_ui;

import java.util.ArrayList;
import java.util.function.Supplier;

import appsettings.AppSettings;
import db.utility.DatabaseExceptionRunnable;
import exceptions.DatabaseException;
import exceptions.DisplayableException;
import ui.pages.ErrorPage;
import utility.Concurrency;

public class UI {
    private ArrayList<Page> panelHierarchy, independentPanels;
    private static UI instance;

    public final boolean isDebug;

    private UI() {
        panelHierarchy = new ArrayList<>();
        independentPanels = new ArrayList<>();

        isDebug = AppSettings.getInstance().uidebug().get();
    }

    public static UI getInstance() {
        if (instance == null)
            instance = new UI();
        return instance;
    }

    public Page launchPanel(Supplier<Page> panel) {
        Page uiPanel = panel.get();
        uiPanel.setUIManager(this);
        uiPanel.setParent(hideCurrentPanel());
        uiPanel.launch();
        panelHierarchy.add(uiPanel);
        return uiPanel;
    }

    /**
     * This method gets called whenever a new panel is launched. It hides the
     * current panel and returns it so that the new panel saves the now hidden panel
     * as parent.
     */
    public Page hideCurrentPanel() {
        Page parent = null;

        if (hierarchySize() > 0) { // if there is a parent panel
            parent = panelHierarchy.get(panelHierarchy.size() - 1);
            if (getCurrentPanel().getResetOnClose()) // if panel wants to be reseted
                resetCurrentPanel(); // reset panel

            setCurrentChildPanelsVisible(false);
            setCurrentPanelVisible(false);
        }

        return parent;
    }

    public void addIndependentPanel(Supplier<Page> independentPanelSupplier) {
        Page panel = independentPanelSupplier.get();
        panel.setUIManager(this);
        panel.launch();
        independentPanels.add(panel);
    }

    public boolean removeIndependentPanel(Page independentPanel) {
        // TODO?
        independentPanel.dispose();
        independentPanel.removeChildPanels();
        return independentPanels.remove(independentPanel);
    }

    public void removeIndependentPanels() {
        independentPanels.forEach(independentPanel -> {
            independentPanel.removeChildPanels();
            independentPanel.dispose();
        });

        independentPanels.clear();
    }

    public void addChildPanelToCurrentUIPanel(Supplier<Page> childPanelSupplier) {
        getCurrentPanel().addChildPanel(childPanelSupplier);
    }

    public void addErrorPanelToCurrentUIPanel(DisplayableException e) {
        getCurrentPanel().addChildPanel(() -> new ErrorPage(e));
    }

    public void resetCurrentPanel() {
        getCurrentPanel().reset();
    }

    private Page getCurrentPanel() {
        return panelHierarchy.get(panelHierarchy.size() - 1);
    }

    public int hierarchySize() {
        return panelHierarchy.size();
    }

    public void removeCurrentPanel() {
        panelHierarchy.get(panelHierarchy.size() - 1).dispose();
        panelHierarchy.remove(panelHierarchy.size() - 1);
    }

    public void setCurrentPanelVisible(boolean visible) {
        getCurrentPanel().update();
        getCurrentPanel().setVisible(visible);
    }

    public void setCurrentChildPanelsVisible(boolean visible) {
        getCurrentPanel().setChildPanelsVisible(visible);
    }

    public static void asyncHandlingDbException(DatabaseExceptionRunnable r) {
        Concurrency.async(() -> {
            handleDatabaseException(r);
        });
    }

    public static void handleDatabaseException(DatabaseExceptionRunnable r) {
        try {
            r.run();
        } catch (DatabaseException e) {
            UI.getInstance().addErrorPanelToCurrentUIPanel(e);
        }
    }

}