package ui.pages.utility;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import free_ui.Page;
import free_ui.UIDesigner;
import free_ui.components.primitives.Button;
import free_ui.components.primitives.List;
import free_ui.stacking.Stack;
import free_ui.stacking.StackManager;
import free_ui.stacking.VStack;

public class SearchHelpPage<T> extends Page {
    private List<T> list;
    private Button selectButton;

    private Consumer<java.util.List<T>> whenValuesSelected;

    public SearchHelpPage(java.util.List<T> values, Consumer<java.util.List<T>> whenValuesSelected) {
        this.whenValuesSelected = whenValuesSelected;

        list = new List<>();

        for (T value : values)
            list.actions().addElement(value);

        list.getJList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2)
                    valuesSelected(list.getJList().getSelectedValuesList());
            }
        });

        list.getJList().addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) // ENTER
                    valuesSelected(list.getJList().getSelectedValuesList());
            }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}
        });


        selectButton = new Button("Auswählen");

        UIDesigner.setHeight(selectButton, 25);

        selectButton.addActionListener(event -> {
            valuesSelected(list.getJList().getSelectedValuesList());
        });
    }


    @Override
    public void launch() {
        setSize(300, 300);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("Suchhilfe");
        setResizable(false);

        var stackManager = new StackManager(getUIStack(), 20, 4);
        stackManager.build(this);

        setVisible(true);
    }

    private void valuesSelected(java.util.List<T> values) {
        if (values == null || values.size() == 0)
            return;

        whenValuesSelected.accept(values);
        defaultOnCloseOperation();
    }

    @Override
    public void onClose() {
        whenValuesSelected.accept(null);
        defaultOnCloseOperation();
    }

    private Stack getUIStack() {
        return new VStack(
                list,
                selectButton);
    }

}
