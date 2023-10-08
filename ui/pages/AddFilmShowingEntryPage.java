package ui.pages;

import java.awt.Dimension;
import java.awt.Toolkit;

import fcbo.FCBO;
import free_ui.Page;
import free_ui.UI;
import free_ui.UIDesigner;
import free_ui.components.FilmShowingList;
import free_ui.components.primitives.Labels;
import free_ui.stacking.Stack;
import free_ui.stacking.StackManager;
import free_ui.stacking.VStack;
import free_ui.theme.AppTheme;

public class AddFilmShowingEntryPage extends Page {

    private FilmShowingList filmShowingList;

    public AddFilmShowingEntryPage() {
        super();
    }

    @Override
    public void launch() {
        setSize(875, 550); // 775 550
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("FREE CINEMA Backoffice: Vorstellung hinzufügen");
        setResizable(false);

        getContentPane().setBackground(AppTheme.get().background());

        var stackManager = new StackManager(getUIStack(), 20, 0);
        stackManager.build(this);

        loadMovieData();

        setVisible(true);
    }

    private Stack getUIStack() {
        var textLabel = Labels.text();
        textLabel.setText("Wähle eine Filmvorstellung");
        UIDesigner.setHeight(textLabel, 25);

        filmShowingList = new FilmShowingList();

        var mainStack = new VStack(
                textLabel,
                filmShowingList.getStack());

        return mainStack;
    }

    private void loadMovieData() {
        UI.asyncHandlingDbException(() -> {
            filmShowingList.actions().addAll(FCBO.getInstance().getActiveFilmShowings());
        });
    }
}
