package free_ui.components.primitives;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import free_ui.Component;
import free_ui.theme.AppTheme;

public class Label extends JLabel implements Component {

    public Label() {
        super();
        applyStyle();
    }

    @Override
    public void applyStyle() {
        setForeground(AppTheme.get().text());
    }

    public Label centered() {
        setVerticalAlignment(SwingConstants.CENTER);
        setHorizontalAlignment(SwingConstants.CENTER);
        return this;
    }
    
    public Label rightAligned() {
        setHorizontalAlignment(RIGHT);
        return this;
    }

}
