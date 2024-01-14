package free_ui.components.support;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DateDocumentFilter extends DocumentFilter {

    private final int cap;

    public DateDocumentFilter(int cap) {
        this.cap = cap;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string != null &&
                isInteger(string) &&
                fb.getDocument().getLength() + string.length() <= 2)
            super.insertString(fb, offset, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text != null &&
                isInteger(text) &&
                fb.getDocument().getLength() + text.length() <= 2)
            super.replace(fb, offset, length, text, attrs);
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
