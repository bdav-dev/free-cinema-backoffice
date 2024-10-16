package free_ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.function.Supplier;
import javax.swing.*;
import exceptions.DisplayableException;
import fcbo.FreeCinemaBackoffice;
import free_ui.components.support.FrameConstants;
import ui.pages.ErrorPage;

@SuppressWarnings("serial")
public abstract class Page extends JFrame {
    private ArrayList<Page> childPanels;
    private ArrayList<JComponent> uiComponents;
    private Container uiContainer;

    private UI uiManager;
    private Page parent;

    private boolean resetOnClose;

    private static int shiftX, shiftY, widthAddtion, heightAddition = 0;
    private boolean sizeAdditionPerformed;

    public Page() {
        super();

        uiComponents = new ArrayList<JComponent>();
        childPanels = new ArrayList<>();
        resetOnClose = false;
        sizeAdditionPerformed = false;

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        // setIconImage(AssetManager.get().getImage(Images.GearIcon));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });

        uiContainer = getContentPane();
        uiContainer.setLayout(null);
    }

    public static void setShift(int shiftX, int shiftY, int widthAddtion, int heightAddition) {
        Page.shiftX = shiftX;
        Page.shiftY = shiftY;
        Page.widthAddtion = widthAddtion;
        Page.heightAddition = heightAddition;
    }

    public abstract void launch();

    public void setParent(Page parent) {
        this.parent = parent;
    }

    public void setUIManager(UI uiManager) {
        this.uiManager = uiManager;
    }

    public void attachComponentToCorner(JComponent component, int padding, FrameConstants placement) {
        addUIComponent(component);

        switch (placement) {
            case TOP_LEFT:
                component.setBounds(padding, padding, component.getWidth(), component.getHeight());
                break;

            case TOP_RIGHT:
                component.setBounds(getWidth() - component.getWidth() - padding - 13, padding, component.getWidth(),
                        component.getHeight());
                break;

            case BOTTOM_LEFT:
                component.setBounds(padding, getHeight() - component.getHeight() - padding - 37, component.getWidth(),
                        component.getHeight());
                break;

            case BOTTOM_RIGHT:
                component.setBounds(getWidth() - component.getWidth() - padding - 13,
                        getHeight() - component.getHeight() - padding - 37, component.getWidth(),
                        component.getHeight());
                break;

            default:
                break;
        }

    }

    @Override
    public void setVisible(boolean visible) {
        boolean center = true;

        if (shiftX != 0 || shiftY != 0) {
            for (JComponent c : uiComponents)
                c.setLocation(c.getX() + shiftX, c.getY() + shiftY);
        }

        if ((widthAddtion != 0 || heightAddition != 0) && !sizeAdditionPerformed) {
            sizeAdditionPerformed = true;
            setSize(getWidth() + widthAddtion, getHeight() + heightAddition);
            if (center) {
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (d.width - getSize().width) / 2;
                int y = (d.height - getSize().height) / 2;
                setLocation(x, y);
            }
        }

        super.setVisible(visible);
    }

    public boolean isChildPanelOfSelf(Page panel) {
        return childPanels.stream().anyMatch(childPanel -> childPanel == panel);

        /*
         * for(UIPanel childPanel : childPanels) {
         * if(childPanel == panel) {
         * return true;
         * }
         * }
         * return false;
         */
    }

    public boolean isChildPanel() {
        if (parent == null)
            return false;

        if (parent.isChildPanelOfSelf(this))
            return true;

        return false;
    }

    public boolean hasChildPanels() {
        return childPanels.size() > 0;
    }

    public void addErrorPanel(DisplayableException e) {
        addChildPanel(() -> new ErrorPage(e));
    }

    public Page addChildPanel(Supplier<Page> childPanelSupplier) {
        Page childPanel = childPanelSupplier.get();
        childPanel.setParent(this);
        childPanel.setUIManager(getUIManager());
        childPanel.launch();
        childPanels.add(childPanel);
        return childPanel;
    }

    public Page addChildPanelToTopParent(Supplier<Page> childPanelSupplier) {
        if (isChildPanel()) {
            return parent.addChildPanelToTopParent(childPanelSupplier);
        } else {
            Page childPanel = childPanelSupplier.get();
            childPanel.setUIManager(getUIManager());
            childPanel.setParent(this);
            childPanel.launch();
            childPanels.add(childPanel);
            return childPanel;
        }
    }

    /**
     * This method should be overwritten when it's function is needed. By default,
     * it does nothing.
     * The method updates all parts of a UIFrame which contains information that
     * might have
     * changed during it's runtime. (E.g.: update() refreshes the UserAccount list
     * in the UserAccountManagement
     * UIFrame because a new user account was added).
     */
    public void update() {

    }

    public void removeChildPanels() {
        for (Page childPanel : childPanels) {
            if (childPanel.hasChildPanels()) {
                childPanel.removeChildPanels();
            }
            childPanel.dispose();
        }

        childPanels.clear();
    }

    public void setChildPanelsVisible(boolean visible) {
        for (Page childPanel : childPanels) {
            if (childPanel.hasChildPanels()) {
                childPanel.setChildPanelsVisible(visible);
            }
            childPanel.setVisible(visible);
        }
    }

    public Page getUIParent() {
        return parent;
    }

    /**
     * This method gets called when the X in the right corner of the window gets
     * clicked.
     */
    public void onClose() {
        defaultOnCloseOperation();
    }

    public final void defaultOnCloseOperation() {
        removeChildPanels(); // dispose all child panels

        if (isChildPanel()) { // if i'm a child panel
            parent.removeChildPanel(this); // dispose and delete myself from the parents "childPanels" ArrayList
            return;
        }

        if (uiManager.hierarchySize() == 1) { // if it's the last panel in the hierarchy
            FreeCinemaBackoffice.getInstance().exit(); // exit (use exit procedure exit() in main class)
        } else { // if there are any other panels in the hierarchy
            uiManager.removeCurrentPanel(); // remove and dispose current open panel from the hierarchy
            uiManager.setCurrentPanelVisible(true); // show parent panel
            uiManager.setCurrentChildPanelsVisible(true); // show parents child panels
        }
    }

    public void removeChildPanel(Page panel) {
        panel.dispose();
        childPanels.remove(panel);
    }

    public void addUIComponent(JComponent component) {
        uiContainer.add(component);
        uiComponents.add(component);
    }

    public void addUIComponents(JComponent... components) {
        for (JComponent c : components) {
            addUIComponent(c);
        }
    }

    public void reset() {

    }

    public void setUIEnabled(boolean enabled) {
        for (JComponent c : uiComponents) {
            c.setEnabled(enabled);
        }
    }

    public void setResetOnClose(boolean resetOnClose) {
        this.resetOnClose = resetOnClose;
    }

    public boolean getResetOnClose() {
        return resetOnClose;
    }

    public UI getUIManager() {
        return uiManager;
    }

}
