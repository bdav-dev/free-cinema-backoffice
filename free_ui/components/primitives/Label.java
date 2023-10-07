package free_ui.components.primitives;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import free_ui.Component;

public class Label extends JLabel implements Component {

    public Label() {
        super();
        applyStyle();
    }

    @Override
    public void applyStyle() {
        
    }

    public Label centered() {
        setVerticalAlignment(SwingConstants.CENTER);
        setHorizontalAlignment(SwingConstants.CENTER);
        return this;
    }
    
}
