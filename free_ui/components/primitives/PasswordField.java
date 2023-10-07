package free_ui.components.primitives;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;

import free_ui.Component;
import free_ui.UIDesigner;
import free_ui.theme.AppTheme;

public class PasswordField extends JPasswordField implements Component {

    public PasswordField() {
        super();
        applyStyle();
    }

    @Override
    public void applyStyle() {
        setBorder(BorderFactory.createCompoundBorder(UIDesigner.getThinBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        setFont(UIDesigner.getMonoMd());
        setDisabledTextColor(AppTheme.get().text());
    }
    
}
