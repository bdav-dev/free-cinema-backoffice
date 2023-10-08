package free_ui.components;

import javax.swing.DefaultListModel;

import fcbo.datatypes.FilmShowing;
import free_ui.Component;
import free_ui.UI;
import free_ui.UIDesigner;
import free_ui.DatatypePage.AccessType;
import free_ui.components.primitives.Button;
import free_ui.components.primitives.List;
import free_ui.components.support.StackComponent;
import free_ui.stacking.HStack;
import free_ui.stacking.Spacer;
import free_ui.stacking.Stack;
import free_ui.stacking.VStack;
import ui.pages.FilmShowingDatatypePage;

public class FilmShowingList implements Component, StackComponent {
    private List<FilmShowing> list;
    private Button addButton, infoButton, proceedButton;

    private Stack mainStack;

    private final int buttonBarHeight = 35;
    private final int componentPadding = 4;

    public FilmShowingList() {
        super();

        infoButton = new Button("i");
        infoButton.setFont(UIDesigner.getMonoMd());
        UIDesigner.setWidth(infoButton, 2 * buttonBarHeight);

        infoButton.addActionListener(e -> {
            UI.getInstance().launchPanel(() -> new FilmShowingDatatypePage(1, AccessType.READ));
        });

        proceedButton = new Button("Bestätigen");

        addButton = new Button("+");
        UIDesigner.setWidth(addButton, 2 * buttonBarHeight);


        var buttonBar = new HStack(
                infoButton,
                new Spacer(componentPadding),
                proceedButton,
                new Spacer(componentPadding),
                addButton)
                        .setHeight(buttonBarHeight);

        list = new List<FilmShowing>();

        mainStack = new VStack(
                list,
                new Spacer(componentPadding),
                buttonBar);

        applyStyle();

    }

    public DefaultListModel<FilmShowing> actions() {
        return list.actions();
    }

    public Stack getStack() {
        return mainStack;
    }

    @Override
    public void applyStyle() {
        list.getJList().setFont(UIDesigner.getRegularXl());
    }

}
