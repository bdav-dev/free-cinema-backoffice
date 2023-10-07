package free_ui.components.primitives;

import javax.swing.JButton;

import free_ui.Component;
import free_ui.UIDesigner;
import free_ui.theme.AppTheme;

public class Checkbox extends JButton implements Component {
    private boolean isActive = false;

    public Checkbox() {
        super();
        applyStyle();

        addActionListener(e -> {
            toggle();
        });

        setActive(false);
    }

    public void toggle() {
        setActive(!isActive);
    }

    public void setActive(boolean active) {
        isActive = active;

        if (isActive)
            setText("✓");
        else
            setText("");
    }

    @Override
    public void applyStyle() {
        this.setBackground(AppTheme.get().elementPrimary());
        this.setForeground(AppTheme.get().text());
        this.setFocusPainted(false);
        //this.setFont(UIDesigner.getRegularMd());
        this.setBorder(UIDesigner.getThinBorder());
    }

}
