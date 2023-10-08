package ui.pages;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import assets.AssetManager;
import assets.AssetManager.Images;
import exceptions.DatabaseException;
import fcbo.FCBO;
import fcbo.datatype.entries.EntryType;
import free_ui.DatatypePage.AccessType;
import free_ui.Page;
import free_ui.UIDesigner;
import free_ui.components.CompositeComponent;
import free_ui.components.HorizontalLine;
import free_ui.components.PlacementType;
import free_ui.components.primitives.Button;
import free_ui.components.primitives.ComboBox;
import free_ui.components.primitives.Label;
import free_ui.components.primitives.Labels;
import free_ui.components.support.FrameConstants;
import free_ui.stacking.HStack;
import free_ui.stacking.Spacer;
import free_ui.stacking.StackManager;
import free_ui.stacking.VStack;
import free_ui.theme.AppTheme;
import utility.Concurrency;

public class MainMenuPage extends Page {
    private AssetManager assetManager;

    private JComboBox<EntryType> entrySelection;
    private Button addEntryButton;
    private Button queryAndChangeEntrysButton;

    private Button memberManagementButton;
    private Button useraccountManagementButton;

    private Button settingsButton;

    private Label loggedInUserAccountField;
    private Button logoutButton;
    private Button userAccountSettingsButton;
    private JLabel loggedInAsLabel;

    private Button infoButton;

    public MainMenuPage() {
        super();
    }

    @Override
    public void launch() {
        assetManager = AssetManager.get();

        setSize(875, 550); // 775 550
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("FREE CINEMA Backoffice: Hauptmenü");
        setResizable(false);

        getContentPane().setBackground(AppTheme.get().background());


        entrySelection = new ComboBox<>();
        for (EntryType element : EntryType.values())
            entrySelection.addItem(element);

        addEntryButton = new Button();
        addEntryButton.setFont(UIDesigner.getRegularLg());
        addEntryButton.setText("+");
        addEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEntryButtonClicked();
            }
        });

        CompositeComponent entrySelectionCC = new CompositeComponent(entrySelection, 270, 41, 2);
        entrySelectionCC.addExtensionComponent(addEntryButton, PlacementType.RIGHT, 41, true);

        queryAndChangeEntrysButton = new Button("Einträge abfragen & ändern");

        memberManagementButton = new Button("Mitgliederverwaltung");

        useraccountManagementButton = new Button("Benutzeraccountverwaltung");

        settingsButton = new Button("Einstellungen");
        settingsButton.setSize(150, 41);
        settingsButton.addActionListener(e -> settingsButtonClicked());

        logoutButton = new Button("logout");
        logoutButton.setFont(UIDesigner.getRegularSm());
        logoutButton.addActionListener(e -> logoutButtonClicked());

        final double gearIconScale = 0.71f;
        userAccountSettingsButton = new Button();
        userAccountSettingsButton.setIcon(assetManager.getScaledImageIcon(
                Images.IconGear, (int) (25 * gearIconScale), (int) (25 * gearIconScale)));
        userAccountSettingsButton.addActionListener(e -> userAccountSettingsButtonClicked());

        loggedInUserAccountField = Labels.smallText().centered();
        loggedInUserAccountField.setBackground(AppTheme.get().background());
        loggedInUserAccountField.setBorder(UIDesigner.getThinBorder());
        setLoggedInUserAccountFieldText(FCBO.getInstance().getCurrentUsername());

        loggedInAsLabel = Labels.smallText().centered();
        loggedInAsLabel.setText("Eingeloggt als:");

        /* Component Placement */
        var hstack1 = HStack.fit(
                new Spacer(0.45f),
                entrySelectionCC,
                new Spacer(0.25f),
                queryAndChangeEntrysButton,
                new Spacer(0.5f));


        var hstack2 = new HStack(
                new Spacer(0.4f),
                memberManagementButton,
                new Spacer(0.4f),
                useraccountManagementButton,
                new Spacer(0.4f));
        hstack2.setHeight(45);


        var mainStack = new VStack(
                new HorizontalLine("Einträge einfügen, abfragen & ändern").defaultHeight(),
                hstack1,

                new Spacer(),

                new HorizontalLine("Verwaltung").defaultHeight(),
                hstack2,

                new Spacer(),

                new HorizontalLine(5),
                settingsButton,

                new Spacer(0.5f));

        StackManager stackManager = new StackManager(mainStack, 34, 6);
        stackManager.build(this);

        /* CORNER */
        CompositeComponent loggedInUserPanel = new CompositeComponent(logoutButton, 120, 25, 2);
        loggedInUserPanel.addExtensionComponent(loggedInUserAccountField, PlacementType.TOP, 25, true);
        loggedInUserPanel.addExtensionComponent(userAccountSettingsButton, PlacementType.RIGHT, 25, false);

        loggedInUserPanel.addExtensionComponent(loggedInAsLabel, PlacementType.TOP, 18, true);
        /**/ attachComponentToCorner(loggedInUserPanel, 10, FrameConstants.BOTTOM_LEFT);

        infoButton = new Button();
        infoButton.setSize(25, 25);
        infoButton.setText("i");
        infoButton.addActionListener((actionEvent) -> infoButtonClicked());
        attachComponentToCorner(infoButton, 10, FrameConstants.BOTTOM_RIGHT);

        setVisible(true);
    }

    private void setLoggedInUserAccountFieldText(String text) {
        loggedInUserAccountField.setText(text);
    }

    protected void addEntryButtonClicked() {
        switch ((EntryType) entrySelection.getSelectedItem()) {
            case Coupon -> getUIManager().launchPanel(() -> new InfoPage());

            case Donation -> {
            }
            case Expense -> {
            }
            case FilmShowingAndBarRevenue -> getUIManager().launchPanel(() -> new AddFilmShowingEntryPage());

            case OtherRevenue -> {
            }
            case RoomRental -> {
            }
            case Sponsoring -> {
            }
        }
    }

    public void userAccountSettingsButtonClicked() {
        getUIManager()
                .launchPanel(() -> new UserAccountSettingsPage(FCBO.getInstance().getCurrentUser(), AccessType.EDIT));
    }

    public void queryAndChangeEntrysButtonClicked() {

    }

    @Override
    public void update() {
        loggedInUserAccountField.setText(FCBO.getInstance().getCurrentUser().getUsername());
    }

    public void infoButtonClicked() {
        getUIManager().launchPanel(() -> new InfoPage());
    }

    public void settingsButtonClicked() {
        getUIManager().launchPanel(() -> new SettingsPage());
    }

    public void useraccountManagementButtonClicked() {
        getUIManager().launchPanel(() -> new UserAccountManagementPage());
    }

    public void memberManagementButtonClicked() {

    }

    public void logoutButtonClicked() {
        Concurrency.async(() -> {
            UIDesigner.block(logoutButton);

            try {
                FCBO.getInstance().logoutUser();
            } catch (DatabaseException e) {
                addErrorPanel(e);
                return;
            } finally {
                UIDesigner.unblock(logoutButton);
            }

            super.onClose();
        });
    }

    @Override
    public void reset() {}

    @Override
    public void onClose() {
        FCBO.getInstance().exit();
    }

}