package free_ui.components.primitives;

import javax.swing.JComboBox;

import free_ui.Component;
import free_ui.UIDesigner;
import free_ui.components.support.CustomListCellRenderer;
import free_ui.theme.AppTheme;

public class ComboBox<T> extends JComboBox<T> implements Component {

    public ComboBox() {
        super();
        applyStyle();
    }

    @Override
    public void applyStyle() {
        setRenderer(new CustomListCellRenderer());
        setForeground(AppTheme.get().text());
        setBackground(AppTheme.get().elementPrimary());
        setOpaque(true);
        setFont(UIDesigner.getRegularMd());
        //setBorder(UIDesigner.getThinBorder());
    }

}
