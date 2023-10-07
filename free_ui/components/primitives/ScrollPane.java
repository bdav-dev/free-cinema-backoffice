package free_ui.components.primitives;

import javax.swing.JScrollPane;

import free_ui.Component;
import free_ui.components.ScrollBarFactory;
import free_ui.theme.AppTheme;

public class ScrollPane extends JScrollPane implements Component {

    public ScrollPane() {
        super();
        applyStyle();
    }

    public ScrollPane(java.awt.Component view) {
        super(view);
        applyStyle();
    }


    @Override
    public void applyStyle() {
        setBorder(null);
        setBackground(AppTheme.get().background());

        ScrollBarFactory.modifyScrollBar(getHorizontalScrollBar(), AppTheme.get().scrollBarThumb(), AppTheme.get().scrollBarTrack(), AppTheme.get().text());
        ScrollBarFactory.modifyScrollBar(getVerticalScrollBar(), AppTheme.get().scrollBarThumb(), AppTheme.get().scrollBarTrack(), AppTheme.get().text());
    }

}
