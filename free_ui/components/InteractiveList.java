package free_ui.components;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;
import java.util.function.Function;

import free_ui.Component;
import free_ui.UIDesigner;
import free_ui.components.primitives.Button;
import free_ui.components.primitives.List;
import free_ui.components.primitives.TextField;
import free_ui.components.support.StackComponent;
import free_ui.stacking.HStack;
import free_ui.stacking.Stack;
import free_ui.stacking.VStack;
import ui.pages.utility.SearchHelpPage;

public class InteractiveList<T> implements Component, StackComponent {

    private List<T> list;

    private TextField addField;

    private Button addButton, removeButton;

    private VStack stack;

    private Function<String, T[]> fromStringConverter;
    private Consumer<SearchHelpPage<T>> searchHelpPage;

    private final int BOTTOM_BAR_SIZE = 30;

    public InteractiveList(Function<String, T[]> fromStringConverter, Consumer<SearchHelpPage<T>> searchHelpPage) {
        this.fromStringConverter = fromStringConverter;
        this.searchHelpPage = searchHelpPage;

        list = new List<>();

        addField = new TextField();
        addField.addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) // 10 = ENTER
                    addFromString(addField.getText());
            }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}
        });

        addButton = new Button("+");
        UIDesigner.setWidth(addButton, BOTTOM_BAR_SIZE);
        addButton.addActionListener(e -> addFromString(addField.getText()));

        removeButton = new Button("-");
        UIDesigner.setWidth(removeButton, BOTTOM_BAR_SIZE);
        removeButton.addActionListener(e -> removeSelected());

        stack = new VStack(
                list,
                new HStack(addField, addButton, removeButton)
                        .setHeight(BOTTOM_BAR_SIZE));
        stack.setComponentPadding(4);
    }

    private void addFromString(String input) {
        T[] converted = fromStringConverter.apply(addField.getText());

        if (converted == null || converted.length == 0)
            return;

        if (converted.length == 1)
            add(converted[0]);
        else
            launchSearchHelp(converted);

    }

    private void launchSearchHelp(T[] values) {
        addField.setEnabled(false);
        addButton.setEnabled(false);

        searchHelpPage.accept(new SearchHelpPage<T>(values, e -> {
            if (e != null) {
                add(e);
                addField.setText("");
            }

            addField.setEnabled(true);
            addButton.setEnabled(true);
        }));
    }

    public void add(T item) {
        if (list.has(item))
            return;

        list.actions().addElement(item);
    }

    public void removeSelected() {
        java.util.List<T> selectedValues = list.getJList().getSelectedValuesList();

        for (T s : selectedValues)
            list.actions().removeElement(s);
    }


    @Override
    public Stack getStack() {
        return stack;
    }

    @Override
    public void applyStyle() {

    }

}
