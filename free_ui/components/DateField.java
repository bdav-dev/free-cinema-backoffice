package free_ui.components;

import java.text.AttributedCharacterIterator.Attribute;

import javax.print.attribute.AttributeSet;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import free_ui.Component;
import free_ui.UIDesigner;
import free_ui.components.primitives.Label;
import free_ui.components.primitives.Labels;
import free_ui.components.primitives.TextField;
import free_ui.components.support.DateDocumentFilter;
import free_ui.components.support.StackComponent;
import free_ui.stacking.HStack;
import free_ui.stacking.Stack;

@SuppressWarnings("serial")
public class DateField implements Component, StackComponent {

    private final int FIELD_SIZE = 30;
    private final int DOTLABEL_WIDTH = 9;

    private TextField dayField, monthField, yearField;
    private Label dot1Label, dot2Label;

    public DateField() {
        super();

        dayField = new TextField();
        dayField.setSize(FIELD_SIZE, FIELD_SIZE);
        dayField.setHorizontalAlignment(SwingConstants.CENTER);
        dayField.setBorder(UIDesigner.getThinBorder());
        AbstractDocument dayDoc = (AbstractDocument) dayField.getDocument();
        dayDoc.setDocumentFilter(new DateDocumentFilter(31));

        dot1Label = Labels.largeText().centered();
        dot1Label.setText(".");
        dot1Label.setSize(DOTLABEL_WIDTH, FIELD_SIZE);
        dot1Label.setVerticalAlignment(SwingConstants.BOTTOM);

        monthField = new TextField();
        monthField.setSize(FIELD_SIZE, FIELD_SIZE);
        monthField.setHorizontalAlignment(SwingConstants.CENTER);
        monthField.setBorder(UIDesigner.getThinBorder());
        AbstractDocument monthDoc = (AbstractDocument) monthField.getDocument();
        monthDoc.setDocumentFilter(new DateDocumentFilter(12));

        dot2Label = Labels.largeText().centered();
        dot2Label.setText(".");
        dot2Label.setSize(DOTLABEL_WIDTH, FIELD_SIZE);
        dot2Label.setVerticalAlignment(SwingConstants.BOTTOM);

        yearField = new TextField();
        yearField.setSize(2 * FIELD_SIZE, FIELD_SIZE);
        yearField.setHorizontalAlignment(SwingConstants.CENTER);
        yearField.setBorder(UIDesigner.getThinBorder());
        AbstractDocument yearDoc = (AbstractDocument) monthField.getDocument();
        monthDoc.setDocumentFilter(new DateDocumentFilter(12));

    }

    @Override
    public Stack getStack() {
        var stack = HStack.fit(
                dayField,
                dot1Label,
                monthField,
                dot2Label,
                yearField).setWidth(4 * FIELD_SIZE + 2 * DOTLABEL_WIDTH);

        return stack;
    }

    @Override
    public void applyStyle() {

    }

}
