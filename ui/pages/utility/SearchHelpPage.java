package ui.pages.utility;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.function.Consumer;

import javax.swing.JList;

import free_ui.Page;
import free_ui.components.primitives.List;
import free_ui.stacking.Stack;
import free_ui.stacking.StackManager;
import free_ui.stacking.VStack;

public class SearchHelpPage<T> extends Page {
    List<T> list;

    Consumer<T> whenValueSelected;

    public SearchHelpPage(T[] values, Consumer<T> whenValueSelected) {
        list = new List<>();


        for (T value : values)
            list.actions().addElement(value);

        this.whenValueSelected = whenValueSelected;

        list.getJList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2)
                    valueSelected(list.getJList().getSelectedValue());
            }
        });

        list.getJList().addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) // ENTER
                    valueSelected(list.getJList().getSelectedValue());
            }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}
        });
    }


    @Override
    public void launch() {
        setSize(300, 300); // 775 550
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("Suchhilfe");
        setResizable(false);

        var stackManager = new StackManager(getUIStack(), 20, 0);
        stackManager.build(this);

        setVisible(true);
    }

    private void valueSelected(T value) {
        whenValueSelected.accept(value);
        defaultOnCloseOperation();
    }

    @Override
    public void onClose() {
        whenValueSelected.accept(null);
        defaultOnCloseOperation();
    }

    private Stack getUIStack() {
        return new VStack(list);
    }

}
