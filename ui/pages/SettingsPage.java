package ui.pages;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;

import appsettings.AppSettings;
import fcbo.FCBO;
import free_ui.Page;
import free_ui.UIDesigner;
import free_ui.components.HorizontalLine;
import free_ui.components.LabeledTextField;
import free_ui.components.primitives.Button;
import free_ui.components.primitives.StackScrollPane;
import free_ui.stacking.HStack;
import free_ui.stacking.Spacer;
import free_ui.stacking.StackManager;
import free_ui.stacking.VStack;
import free_ui.theme.AppTheme;

public class SettingsPage extends Page {

    private enum DbFields {
        HOST, PORT, DATABASE, USERNAME, PASSWORD;
    }

    private HashMap<DbFields, LabeledTextField> dbFields = new HashMap<>();


    private enum LayoutcorrFields {
        X, Y, WIDTH, HEIGHT;
    }

    private HashMap<LayoutcorrFields, LabeledTextField> layoutcorrFields = new HashMap<>();


    private enum DefaultValueFields {
        MWST, TICKETPRICE;
    }

    private HashMap<DefaultValueFields, LabeledTextField> defaultValueFields = new HashMap<>();

    private LabeledTextField mandtField;

    private boolean authorized = FCBO.getInstance().getCurrentUser().getPrivilegeLevel() != 0;

    @Override
    public void launch() {
        setSize(602, 535);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("FREE CINEMA Backoffice: Einstellungen");

        getContentPane().setBackground(AppTheme.get().background());
        setResizable(false);

        var exitButton = new Button("Speichern und schließen");
        exitButton.addActionListener(a -> saveAndClose());
        UIDesigner.setHeight(exitButton, 35);

        var mainStack = new VStack();
        mainStack.add(StackScrollPane.verticalScrollView(new StackManager(settingsStack(), 20, 3), 800));
        mainStack.add(exitButton);

        StackManager m = new StackManager(mainStack, 10, 10);
        m.build(this);

        setVisible(true);
    }

    @Override
    public void onClose() {
        addChildPanelToTopParent(() -> ConfirmExitPage.defaultExitPanel());
    }


    private VStack settingsStack() {
        final int labeledTextFieldHeight = 55;

        var settingsStack = new VStack();

        var mandtTitle = new HorizontalLine("Applikationsmandant", 30);
        mandtField = new LabeledTextField("mandt", labeledTextFieldHeight);

        var defaultValuesTitle = new HorizontalLine("Standardwerte", 30);
        defaultValueFields.put(DefaultValueFields.MWST, new LabeledTextField("MwSt (in %)", labeledTextFieldHeight));
        defaultValueFields.put(DefaultValueFields.TICKETPRICE, new LabeledTextField("Ticketpreis (in €)", labeledTextFieldHeight));

        var dbTitle = new HorizontalLine("Datenbank", 30);
        dbFields.put(DbFields.DATABASE, new LabeledTextField("Database", labeledTextFieldHeight));
        dbFields.put(DbFields.HOST, new LabeledTextField("Host", labeledTextFieldHeight));
        dbFields.put(DbFields.PORT, new LabeledTextField("Port", labeledTextFieldHeight));
        dbFields.put(DbFields.USERNAME, new LabeledTextField("Username", labeledTextFieldHeight));
        dbFields.put(DbFields.PASSWORD, new LabeledTextField("Password", labeledTextFieldHeight));


        var shiftTitle = new HorizontalLine("Layoutkorrekturen", 30);
        layoutcorrFields.put(LayoutcorrFields.X, new LabeledTextField("X", labeledTextFieldHeight));
        layoutcorrFields.put(LayoutcorrFields.Y, new LabeledTextField("Y", labeledTextFieldHeight));
        layoutcorrFields.put(LayoutcorrFields.WIDTH, new LabeledTextField("Breite", labeledTextFieldHeight));
        layoutcorrFields.put(LayoutcorrFields.HEIGHT, new LabeledTextField("Höhe", labeledTextFieldHeight));


        settingsStack.add(
                mandtTitle,
                mandtField,

                new Spacer(),

                defaultValuesTitle,
                HStack.fit(defaultValueFields.get(DefaultValueFields.MWST), defaultValueFields.get(DefaultValueFields.TICKETPRICE)),

                new Spacer(),

                dbTitle,
                dbFields.get(DbFields.DATABASE),
                HStack.fit(dbFields.get(DbFields.HOST), dbFields.get(DbFields.PORT)),
                HStack.fit(dbFields.get(DbFields.USERNAME), dbFields.get(DbFields.PASSWORD)),

                new Spacer(),

                shiftTitle,
                HStack.fit(layoutcorrFields.get(LayoutcorrFields.X), layoutcorrFields.get(LayoutcorrFields.Y)),
                HStack.fit(layoutcorrFields.get(LayoutcorrFields.WIDTH), layoutcorrFields.get(LayoutcorrFields.HEIGHT)));

        disableUnauthorizedSettings();
        loadDatabaseValues();
        loadLayoutCorrectionValues();
        loadMandt();

        return settingsStack;
    }

    private void saveAndClose() {
        save();
        dispose();
        defaultOnCloseOperation();
    }

    public void save() {

        if (authorized) {
            // Save database credentials
            AppSettings.getInstance().database().setAll(
                    dbFields.get(DbFields.DATABASE).getText(),
                    dbFields.get(DbFields.HOST).getText(),
                    dbFields.get(DbFields.PORT).getText(),
                    dbFields.get(DbFields.USERNAME).getText(),
                    dbFields.get(DbFields.PASSWORD).getText());

            // Save application mandt
            AppSettings.getInstance().mandt().setStringOnlySavingToLocalStorage(mandtField.getText());

        }

        // Save layout correction values
        AppSettings.getInstance().layoutCorrections().setAllString(
                layoutcorrFields.get(LayoutcorrFields.X).getText(),
                layoutcorrFields.get(LayoutcorrFields.Y).getText(),
                layoutcorrFields.get(LayoutcorrFields.WIDTH).getText(),
                layoutcorrFields.get(LayoutcorrFields.HEIGHT).getText());
    }

    private void loadDatabaseValues() {
        if (!authorized)
            return;

        dbFields.get(DbFields.DATABASE).setText(
                AppSettings.getInstance().database().database().get());

        dbFields.get(DbFields.HOST).setText(
                AppSettings.getInstance().database().host().get());

        dbFields.get(DbFields.PASSWORD).setText(
                AppSettings.getInstance().database().password().get());

        dbFields.get(DbFields.PORT).setText(
                AppSettings.getInstance().database().port().get());

        dbFields.get(DbFields.USERNAME).setText(
                AppSettings.getInstance().database().username().get());
    }

    private void loadMandt() {
        mandtField.setText(
                String.valueOf(AppSettings.getInstance().mandt().get()));
    }

    private void loadLayoutCorrectionValues() {
        layoutcorrFields.get(LayoutcorrFields.X).setText(
                String.valueOf(AppSettings.getInstance().layoutCorrections().x().get()));

        layoutcorrFields.get(LayoutcorrFields.Y).setText(
                String.valueOf(AppSettings.getInstance().layoutCorrections().y().get()));

        layoutcorrFields.get(LayoutcorrFields.WIDTH).setText(
                String.valueOf(AppSettings.getInstance().layoutCorrections().width().get()));

        layoutcorrFields.get(LayoutcorrFields.HEIGHT).setText(
                String.valueOf(AppSettings.getInstance().layoutCorrections().height().get()));
    }

    private void disableUnauthorizedSettings() {
        if (!authorized) {
            for (var entry : dbFields.entrySet())
                entry.getValue().setEnabled(false);

            mandtField.setEnabled(false);
        }
    }
}