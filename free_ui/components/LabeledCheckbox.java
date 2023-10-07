package free_ui.components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import free_ui.UIDesigner;
import free_ui.components.primitives.Checkbox;
import free_ui.components.primitives.Label;
import free_ui.components.primitives.Labels;
import free_ui.components.support.StackComponent;
import free_ui.stacking.HStack;
import free_ui.stacking.Spacer;
import free_ui.stacking.Stack;

public class LabeledCheckbox implements StackComponent {
    private Checkbox checkbox;
    private Label label;

    private HStack stack;

    private final int stackHeight = 25;

    public LabeledCheckbox(String text) {
        this(text, 0);
    }

    public LabeledCheckbox(String text, int leftIndent) {
        label = Labels.text();
        UIDesigner.setHeight(label, stackHeight);
        label.setText(text);
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                checkbox.doClick();
            }
        });

        checkbox = new Checkbox();
        checkbox.setSize(stackHeight - 8, stackHeight - 8);

        stack = new HStack();

        stack.add(
                new Spacer(leftIndent),
                checkbox,
                new Spacer(5),
                label);

        stack.setHeight(stackHeight);
    }

    public Checkbox getCheckbox() {
        return checkbox;
    }

    @Override
    public Stack getStack() {
        return stack;
    }

}
