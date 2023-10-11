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

    private Function<String, java.util.List<T>> fromStringConverter;
    private Consumer<SearchHelpPage<T>> searchHelpPageLauncher;

    private final int BOTTOM_BAR_SIZE = 30;

    public InteractiveList(Function<String, java.util.List<T>> fromStringConverter, Consumer<SearchHelpPage<T>> searchHelpPageLauncher) {
        this.fromStringConverter = fromStringConverter;
        this.searchHelpPageLauncher = searchHelpPageLauncher;

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
        java.util.List<T> converted = fromStringConverter.apply(addField.getText());

        if (converted == null)
            return;

        converted.removeIf(element -> list.getAllValues().contains(element));

        if (converted.size() == 0)
            return;

        if (converted.size() == 1) {
            add(converted.get(0));
            addField.setText("");
        } else {
            launchSearchHelp(converted);
        }
    }

    private void launchSearchHelp(java.util.List<T> values) {
        addField.setEnabled(false);
        addButton.setEnabled(false);

        searchHelpPageLauncher.accept(new SearchHelpPage<T>(values, selectedValues -> {
            if (selectedValues != null && selectedValues.size() != 0) {
                addAll(selectedValues);
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

    public void addAll(java.util.List<T> list) {
        for (T element : list)
            add(element);
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
