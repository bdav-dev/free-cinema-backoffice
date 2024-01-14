package free_ui.components.primitives;

import javax.swing.SwingConstants;

import free_ui.UIDesigner;
import free_ui.theme.AppTheme;

public class Labels {

    private Labels() {

    }

    public static Label headline() {
        final Label headline = new Label();

        headline.setFont(UIDesigner.getBoldXl());
        headline.setVerticalAlignment(SwingConstants.CENTER);
        headline.setHorizontalAlignment(SwingConstants.CENTER);
        UIDesigner.setHeight(headline, 35);

        return headline;
    }

    public static Label xlText() {
        final Label text = new Label();
        text.setFont(UIDesigner.getRegularXl());
        // TODO UIDesigner.setHeight(headline, 35);
        return text;
    }

    public static Label largeMonoText() {
        final Label text = new Label();
        text.setFont(UIDesigner.getMonoLg());
        // TODO UIDesigner.setHeight(headline, 35);
        return text;
    }

    public static Label xlMonoText() {
        final Label text = new Label();
        text.setFont(UIDesigner.getMonoXl());
        // TODO UIDesigner.setHeight(headline, 35);
        return text;
    }

    public static Label largeText() {
        final Label text = new Label();
        text.setFont(UIDesigner.getRegularLg());
        // TODO UIDesigner.setHeight(headline, 35);
        return text;
    }

    public static Label monoText() {
        final Label text = new Label();
        text.setFont(UIDesigner.getMonoMd());
        // TODO UIDesigner.setHeight(headline, 35);
        return text;
    }

    public static Label text() {
        final Label text = new Label();
        text.setFont(UIDesigner.getRegularMd());
        // TODO UIDesigner.setHeight(headline, 35);
        return text;
    }


    public static Label smallText() {
        final Label text = new Label();
        text.setFont(UIDesigner.getRegularSm());
        return text;
    }

    public static Label xlBoldText() {
        final Label text = new Label();
        text.setFont(UIDesigner.getBoldXl());
        return text;
    }

    public static Label largeBoldText() {
        final Label text = new Label();
        text.setFont(UIDesigner.getBoldLg());
        // TODO UIDesigner.setHeight(headline, 35);
        return text;
    }

    public static Label boldText() {
        final Label text = new Label();
        text.setFont(UIDesigner.getBoldMd());
        // TODO UIDesigner.setHeight(headline, 35);
        return text;
    }



}
