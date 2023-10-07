package ui.pages;

import java.awt.Dimension;
import java.awt.Toolkit;

import assets.AssetManager.Images;
import cli.TerminalPage;
import fcbo.FCBO;
import free_ui.Page;
import free_ui.UIDesigner;
import free_ui.components.Image;
import free_ui.components.primitives.Button;
import free_ui.components.primitives.Label;
import free_ui.components.primitives.Labels;
import free_ui.stacking.Spacer;
import free_ui.stacking.StackManager;
import free_ui.stacking.VStack;
import free_ui.theme.AppTheme;

public class InfoPage extends Page {
    Image logo;
    Label projectName;
    Label version;
    Label author;
    Button launchTerminal;

    public InfoPage() {
        super();
    }

    @Override
    public void launch() {
        setSize(400, 400);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("FREE CINEMA Backoffice: Info");
        setResizable(false);

        getContentPane().setBackground(AppTheme.get().background());

        logo = new Image(Images.FcLogo, 0.5f);

        projectName = Labels.headline();
        projectName.setText("Backoffice");
        UIDesigner.setHeight(projectName, 20);

        version = Labels.text().centered();
        version.setText("Version " + FCBO.VERSION);
        UIDesigner.setHeight(version, 20);

        author = Labels.text().centered();
        author.setText("David Berezowski, 2023");
        UIDesigner.setHeight(author, 20);

        launchTerminal = new Button();
        launchTerminal.setText("CLI starten");
        launchTerminal.addActionListener((actionEvent) -> launchTerminalButtonClicked());
        UIDesigner.setWidth(launchTerminal, 110);
        UIDesigner.setHeight(launchTerminal, 35);

        VStack mainStack = new VStack();
        mainStack.add(logo);
        mainStack.add(projectName);
        mainStack.add(new Spacer());
        mainStack.add(version);
        mainStack.add(author);
        mainStack.add(new Spacer());
        mainStack.add(launchTerminal);

        StackManager stackManager = new StackManager(mainStack, 20, 6);
        stackManager.build(this);

        setVisible(true);
    }

    private void launchTerminalButtonClicked() {
        if (TerminalPage.isInitialized())
            TerminalPage.getTerminalPanel().requestFocus();
        else
            getUIManager().addIndependentPanel(() -> TerminalPage.getTerminalPanel());
    }
}
