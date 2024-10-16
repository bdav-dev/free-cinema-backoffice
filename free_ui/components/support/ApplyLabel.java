package free_ui.components.support;

import free_ui.UIDesigner;
import free_ui.components.primitives.Label;
import free_ui.components.primitives.Labels;
import free_ui.stacking.Stack;
import free_ui.stacking.VStack;

public class ApplyLabel<T> implements StackComponent {

    private T component;
    private Label label;

    private ApplyLabel(String label, T component) {
        this.component = component;
        this.label = Labels.smallText();
        setLabelText(label);
        UIDesigner.setHeight(this.label, 20);
    }

    public static <T> ApplyLabel<T> to(T component, String label) {
        return new ApplyLabel<>(label, component);
    }

    public void setLabelText(String text) {
        label.setText(text);
        UIDesigner.makeFitContent(label);
    }

    @Override
    public Stack getStack() {
        return VStack.fit(
                label,
                component);
    }


}
