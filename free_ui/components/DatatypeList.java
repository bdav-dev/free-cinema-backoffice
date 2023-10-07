package free_ui.components;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DatatypeList<T> extends JPanel {

    private JLabel listTitle;
    private JList<T> list;
    private DefaultListModel<T> listModel;
    private JButton[] buttons;

    public DatatypeList() {
        super();

        setLayout(null);
        setBackground(null);

        buttons = new JButton[3];

        listTitle = new JLabel();
        list = new JList<>();
        listModel = new DefaultListModel<>();
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            add(buttons[i]);
        }

        add(listTitle);
        add(list);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        int distanceBetweenElements = 3;

        int listTitleHeight = 20;
        listTitle.setBounds(0, 0, width, listTitleHeight);

        int buttonHeight = 30;
        int listHeight = height - 2 * distanceBetweenElements - listTitleHeight - buttonHeight;
        list.setBounds(0, listTitleHeight + distanceBetweenElements, width, listHeight);

        int initialCenterPositionX = width / (buttons.length + 1);
        int buttonWidth = 100;
        for (int i = 1; i <= buttons.length; i++) {
            buttons[i - 1].setBounds(initialCenterPositionX * i - buttonWidth / 2,
                    list.getHeight() + list.getY() + distanceBetweenElements, buttonWidth, buttonHeight);
        }

    }

    public void setButtonText(String... buttonText) {
        for (int i = 0; i < buttons.length; i++) {
            if (buttonText[i] != null) {
                buttons[i].setText(buttonText[i]);
            }
        }
    }

    public void addToList(ArrayList<T> elements) {
        for (T element : elements) {
            listModel.addElement(element);
        }
    }

}
