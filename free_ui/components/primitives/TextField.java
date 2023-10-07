package free_ui.components.primitives;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import free_ui.Component;
import free_ui.UIDesigner;
import free_ui.theme.AppTheme;

public class TextField extends JTextField implements Component {

    public static final Color backgroundDisabled = AppTheme.get().scrollBarThumb();
    public static final Color backgroundEnabled = AppTheme.get().elementPrimary();

    public TextField() {
        super();
        applyStyle();
    }

    @Override
    public void applyStyle() {
        setBorder(BorderFactory.createCompoundBorder(UIDesigner.getThinBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        setFont(UIDesigner.getRegularMd());
        setBackground(backgroundEnabled);
        setDisabledTextColor(AppTheme.get().text());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (enabled)
            setBackground(backgroundEnabled);
        else
            setBackground(backgroundDisabled);
    }

}
