package free_ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class AdvancedList<T> extends JPanel {

    private final int distanceBetweenUIElements = 1;
    private final int buttonClearWidth = 40;
    private final int topRowHeight = 20;
    private final int bottomRowHeight = 25;

    private JLabel label;
    private JTextField textField;
    private JList<T> list;
    private DefaultListModel<T> listModel;
    private JScrollPane listScrollPane;
    private JButton buttonAdd, buttonRemove, buttonClear;

    private T sample;

    public AdvancedList(T sample) {
        super();
        super.setLayout(null);
        super.setBackground(null);

        this.sample = sample;

        label = new JLabel();

        textField = new JTextField();
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {
                    case 10: // ENTER
                        enterKeyPressed();
                        break;
                }

            }
        });

        list = new JList<T>();
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    doubleClickPerformed(list.getSelectedIndex());
                }
            }

        });

        listScrollPane = new JScrollPane(list);

        buttonAdd = new JButton();
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAddClicked();
            }
        });

        buttonRemove = new JButton();
        buttonRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonRemoveClicked();
            }
        });

        buttonClear = new JButton();
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClearClicked();
            }
        });

        listModel = new DefaultListModel<T>();

        list.setModel(listModel);
        

        add(textField);
        add(listScrollPane);
        add(buttonAdd);
        add(buttonRemove);
        add(buttonClear);
        add(label);

    }

    public void setText(String text) {
        label.setText(text == null ? "" : text);
    }

    private void buttonAddClicked() {
        if (textField.getText().equals("")) {
            return;
        }

        // TODO

        // T object = sample.stringToObject(textField.getText());

        // if(object == null) {
        // return;
        // }

        // listModel.addElement(object);
        // textField.setText("");
    }

    private void doubleClickPerformed(int selectedIndex) {
        T object = listModel.get(selectedIndex);
        if (object != null) {
            System.out.println("Double-Clicked element: " + object.toString());
        }
    }

    private void buttonRemoveClicked() {
        int index = list.getSelectedIndex();

        if (index == -1) {
            return;
        }

        listModel.remove(index);
    }

    private void buttonClearClicked() {
        listModel.removeAllElements();
    }

    private void enterKeyPressed() {
        buttonAdd.doClick();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        final int listHeight = height - (topRowHeight + 2 * distanceBetweenUIElements + bottomRowHeight);
        final int textFieldWidth = width - (2 * (bottomRowHeight + distanceBetweenUIElements));

        label.setBounds(0, 0, width - (buttonClearWidth + distanceBetweenUIElements), topRowHeight);
        buttonClear.setBounds(width - buttonClearWidth, 0, buttonClearWidth, topRowHeight);
        listScrollPane.setBounds(0, topRowHeight + distanceBetweenUIElements, width, listHeight);
        textField.setBounds(0, topRowHeight + 2 * distanceBetweenUIElements + listHeight, textFieldWidth, bottomRowHeight);
        buttonAdd.setBounds(textFieldWidth + distanceBetweenUIElements, textField.getY(), bottomRowHeight, bottomRowHeight);
        buttonRemove.setBounds(buttonAdd.getX() + bottomRowHeight + distanceBetweenUIElements, buttonAdd.getY(), bottomRowHeight, bottomRowHeight);

    }



}
