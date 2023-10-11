package free_ui.components.primitives;

import java.util.ArrayList;
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

    public JList<T> getJList() {
        return list;
    }

    public java.util.List<T> getAllValues() {
        ArrayList<T> allValues = new ArrayList<>();

        for (int i = 0; i < actions().getSize(); i++)
            allValues.add(actions().get(i));

        return allValues;
    }

    public boolean has(T value) {
        return getAllValues()
                .stream()
                .anyMatch(e -> e.equals(value));
    }

    private void applyListStyle() {
        list.setFont(UIDesigner.getRegularMd());
        list.setCellRenderer(new CustomListCellRenderer());
        // TODO: Selected item color
    }

}
