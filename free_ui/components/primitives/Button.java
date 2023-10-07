package free_ui.components.primitives;

import javax.swing.JButton;

import free_ui.Component;
import free_ui.UIDesigner;
import free_ui.theme.AppTheme;

public class Button extends JButton implements Component {
    
    public Button() {
        super();
        applyStyle();
    }

    public Button(String text) {
        super(text);
        applyStyle();
    }

    @Override
    public void applyStyle() {
        this.setBackground(AppTheme.get().elementPrimary());
        this.setForeground(AppTheme.get().text());
        this.setFocusPainted(false);
        this.setFont(UIDesigner.getRegularMd());
        this.setBorder(UIDesigner.getThinBorder());
    }

}
