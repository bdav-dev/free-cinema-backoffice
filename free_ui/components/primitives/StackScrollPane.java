package free_ui.components.primitives;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import free_ui.Component;
import free_ui.components.ScrollBarFactory;
import free_ui.stacking.StackManager;
import free_ui.theme.AppTheme;

public class StackScrollPane extends JScrollPane implements Component {

    private JPanel panel;
    private StackManager stackManager;
    private int preferredHeight;


    private StackScrollPane() {
        super();
        applyStyle();
    }

    private StackScrollPane(java.awt.Component view) {
        super(view);
        applyStyle();
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        updateStackSize(width);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        updateStackSize(width);
    }

    @Override
    public void setBounds(Rectangle r) {
        super.setBounds(r);
        updateStackSize(r.width);
    }


    private void updateStackSize(int width) {
        panel.removeAll();
        panel.setPreferredSize(new Dimension(width, preferredHeight));
        stackManager.build(e -> panel.add(e), width, preferredHeight);
    }

    @Override
    public void setBackground(Color color) {
        super.setBackground(color);
        if (panel != null)
            panel.setBackground(color);
    }

    @Override
    public void applyStyle() {
        setBorder(null);
        setBackground(AppTheme.get().background());

        ScrollBarFactory.modifyScrollBar(getHorizontalScrollBar(), AppTheme.get().scrollBarThumb(), AppTheme.get().scrollBarTrack(), AppTheme.get().text());
        ScrollBarFactory.modifyScrollBar(getVerticalScrollBar(), AppTheme.get().scrollBarThumb(), AppTheme.get().scrollBarTrack(), AppTheme.get().text());
    }

    public static StackScrollPane verticalScrollView(StackManager stackManager, int viewHeight) {
        var panel = new JPanel();
        panel.setBackground(AppTheme.get().background());
        panel.setLayout(null);

        StackScrollPane verticalScrollView = new StackScrollPane(panel);
        verticalScrollView.preferredHeight = viewHeight;
        verticalScrollView.panel = panel;
        verticalScrollView.stackManager = stackManager;

        verticalScrollView.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        return verticalScrollView;
    }
}
