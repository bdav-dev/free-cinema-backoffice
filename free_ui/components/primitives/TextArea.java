package free_ui.components.primitives;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import free_ui.Component;
import free_ui.UIDesigner;
import free_ui.theme.AppTheme;

public class TextArea extends JTextArea implements Component {


    public TextArea() {
        super();
        applyStyle();
    }


    @Override
    public void applyStyle() {
        setBackground(AppTheme.get().elementPrimary());
        setForeground(AppTheme.get().text());
        setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        setLineWrap(true);
        setWrapStyleWord(true);
        setFont(UIDesigner.getRegularMd());
        setDisabledTextColor(AppTheme.get().text());
    }
    
}
