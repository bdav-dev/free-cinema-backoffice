package free_ui.components.support;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import free_ui.theme.AppTheme;


public class CustomListCellRenderer extends DefaultListCellRenderer {

    public CustomListCellRenderer() {
        super();
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (isSelected) {
            c.setBackground(AppTheme.get().background());
        } else {
            c.setBackground(AppTheme.get().elementPrimary());
        }

        c.setForeground(AppTheme.get().text());
        list.setSelectionBackground(AppTheme.get().elementPrimary());
        list.setSelectionForeground(AppTheme.get().text());
        return c;
    }
}
