package free_ui.components.primitives;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import free_ui.UIDesigner;
import free_ui.components.support.CustomListCellRenderer;

public class List<T> extends ScrollPane {
    private JList<T> list;
    private DefaultListModel<T> defaultListModel;

    public List() {
        super();

        list = new JList<>();
        defaultListModel = new DefaultListModel<>();
        list.setModel(defaultListModel);

        setViewportView(list);

        applyListStyle();
    }

    public DefaultListModel<T> actions() {
        return defaultListModel;
    }

    private void applyListStyle() {
        list.setFont(UIDesigner.getRegularXl());
        list.setCellRenderer(new CustomListCellRenderer());
        //TODO: Selected item color
    }

}
