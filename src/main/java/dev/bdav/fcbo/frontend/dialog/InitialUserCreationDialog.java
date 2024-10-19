package dev.bdav.fcbo.frontend.dialog;

import dev.bdav.fcbo.freeui.components.Button;
import dev.bdav.fcbo.freeui.components.RoundedPanel;
import dev.bdav.fcbo.freeui.components.label.H1;
import dev.bdav.fcbo.freeui.components.label.H2;
import dev.bdav.fcbo.freeui.components.label.SectionTitle;
import dev.bdav.fcbo.freeui.icon.IconFactory;
import dev.bdav.fcbo.freeui.stacking.StackBuilder;
import dev.bdav.fcbo.frontend.icon.GoogleMaterialIcon;

import javax.swing.*;
import java.awt.*;

public class InitialUserCreationDialog extends JDialog {


    public InitialUserCreationDialog(Frame parent) {
        super(parent, true);

        setSize(300, 300);

        setLocationRelativeTo(parent);

        var button = new JButton("Create New User");
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);

        var test = StackBuilder.vertical()
                .content(
                        new JButton("test"),
                        new JButton("testsduihfsuidhfsuidhf")
                )
                .stackMargin(10)
                .build();

        var r = new RoundedPanel(test);
        r.setBackground(Color.RED);

        var b = new Button();
        b.setText("hello");

        add(
                StackBuilder.vertical()
                        .content(
                                IconFactory.labeled(GoogleMaterialIcon.LOGIN, new H1("Login")),
                                new SectionTitle(
                                        IconFactory.labeled(GoogleMaterialIcon.ACCOUNT_CIRCLE, new H2("Welcome"))
                                ),
                                new JLabel("Hello world"),
                                button,
                                r,
                                b
                        )
                        .stackMargin(10)
                        .build()
        );


    }


}
