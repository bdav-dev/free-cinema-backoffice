package dev.bdav.fcbo;

import java.awt.Dimension;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import dev.bdav.fcbo.backend.Database;
import dev.bdav.fcbo.freeui.core.UI;
import dev.bdav.fcbo.freeui.exception.FontInitializationException;
import dev.bdav.fcbo.freeui.font.FontSize;
import dev.bdav.fcbo.freeui.font.Fonts;
import dev.bdav.fcbo.freeui.icon.Icon;
import dev.bdav.fcbo.frontend.icon.GoogleMaterialIcon;
import dev.bdav.fcbo.frontend.pages.LoginPage;

public class FreeCinemaBackoffice {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
            () -> UI.launch(
                LoginPage::new,
                FlatMacDarkLaf::new,
                ui -> {
                    ui.setSize(700, 500);
                    ui.setMinimumSize(new Dimension(600, 400));

                    initialize();
                }
            )
        );
    }

    private static void initialize() {
        try {
            Fonts.configureMonospaceFont(
                "fonts/CascadiaMono-SemiLight.ttf",
                FontSize.MEDIUM.value()
            );
        } catch (FontInitializationException e) {
            System.err.println("Couldn't set custom mono font 'Cascadia Mono'.");
        }

        try {
            Icon.configureIconFont("icons/MaterialSymbolsRounded-Regular.ttf");
        } catch (FontInitializationException e) {
            System.err.println("Couldn't set icon font.");
        }

        UI.runWhenReady(
            ui -> ui.getNavHeader().setNavigateBackButtonContent(Icon.standalone(GoogleMaterialIcon.WEST))
        );

        Database.initialize();
    }

}
