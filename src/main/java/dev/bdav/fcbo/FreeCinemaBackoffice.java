package dev.bdav.fcbo;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import dev.bdav.fcbo.freeui.configuration.IconConfiguration;
import dev.bdav.fcbo.freeui.core.UI;
import dev.bdav.fcbo.freeui.exception.FontInitializationException;
import dev.bdav.fcbo.freeui.factory.IconFactory;
import dev.bdav.fcbo.freeui.font.Fonts;
import dev.bdav.fcbo.freeui.localstorage.LocalStorage;
import dev.bdav.fcbo.frontend.icon.GoogleMaterialIcon;
import dev.bdav.fcbo.frontend.pages.LoginPage;

import javax.swing.*;
import java.awt.*;

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
                    12
            );
        } catch (FontInitializationException e) {
            System.err.println("Couldn't set custom mono font 'Cascadia Mono'.");
        }

        try {
            IconConfiguration.configureIconFont("icons/MaterialSymbolsRounded-Regular.ttf");
        } catch (FontInitializationException e) {
            System.err.println("Couldn't set icon font.");
        }

        LocalStorage.initializeDefaultStorage("fcbo");

        UI.runWhenReady(
                ui -> ui.getNavHeader().setNavigateBackButtonContent(IconFactory.standalone(GoogleMaterialIcon.WEST))
        );

        //Database.initialize();
    }

}
