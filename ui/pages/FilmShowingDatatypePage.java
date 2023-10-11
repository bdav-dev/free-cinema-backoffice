package ui.pages;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashSet;

import fcbo.datatypes.FilmShowing;
import free_ui.DatatypePage;
import free_ui.UI;
import free_ui.components.DateField;
import free_ui.components.HorizontalLine;
import free_ui.components.InteractiveList;
import free_ui.components.LabeledCheckbox;
import free_ui.components.LabeledTextField;
import free_ui.stacking.HStack;
import free_ui.stacking.Spacer;
import free_ui.stacking.StackManager;
import free_ui.stacking.VStack;

public class FilmShowingDatatypePage extends DatatypePage {

    private int filmShowingID;
    private FilmShowing filmShowing;

    // UI
    private LabeledTextField movieNameField,
            mwstField,
            minimumGuaranteeField,
            rentalRateField,
            advertisingCostField,
            spioCostField,
            otherCostField;

    private DateField moneyTransferredField;

    private LabeledCheckbox activeCheckbox;

    private InteractiveList<String> tagList;

    public FilmShowingDatatypePage(int filmShowingID, AccessType accessType) {
        super(accessType);
        this.filmShowingID = filmShowingID;
    }

    @Override
    public void launch() {
        setSize(800, 800);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("FREE CINEMA Backoffice: Film");
        setResizable(false);

        var stackManager = new StackManager(getUIStack(), 20, 0);
        stackManager.build(this);

        setVisible(true);
    }

    private VStack getUIStack() {
        var mainStack = new VStack();

        movieNameField = new LabeledTextField("Filmname");

        mwstField = new LabeledTextField("MwSt");
        minimumGuaranteeField = new LabeledTextField("Mindestgarantie");
        rentalRateField = new LabeledTextField("Mietsatz");

        advertisingCostField = new LabeledTextField("Werbekosten");
        spioCostField = new LabeledTextField("Spiokosten");
        otherCostField = new LabeledTextField("andere Kosten");

        activeCheckbox = new LabeledCheckbox("Aktiv", 10);


        tagList = new InteractiveList<String>(
                input -> {
                    var list = new ArrayList<String>();

                    list.add(input);

                    for(int i = 0; i < 100; i++)
                        list.add(input + i);

                    return list;
                },
                p -> UI.getInstance().addChildPanelToCurrentUIPanel(() -> p));

        mainStack.add(
                new HorizontalLine("Allgemein").defaultHeight(),
                movieNameField,
                activeCheckbox.getStack(),

                new Spacer(),

                new HorizontalLine("Mietkonditionen").defaultHeight(),
                HStack.fit(minimumGuaranteeField, rentalRateField, mwstField).setComponentPadding(5),

                new Spacer(),

                new HorizontalLine("Kosten").defaultHeight(),
                HStack.fit(advertisingCostField, spioCostField, otherCostField).setComponentPadding(5),

                new Spacer(),

                new HorizontalLine("Tags").defaultHeight(),
                tagList.getStack());

        return mainStack;
    }

    @Override
    public void prepareEdit() {

    }

    @Override
    public void prepareRead() {

    }

    @Override
    public void prepareCreate() {

    }

    @Override
    public void save() {

    }

    @Override
    public void create() {

    }

}
