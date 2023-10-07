package ui.pages;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import free_ui.Page;
import free_ui.UIDesigner;
import free_ui.components.primitives.Button;
import free_ui.components.primitives.Label;
import free_ui.components.primitives.Labels;
import free_ui.stacking.HStack;
import free_ui.stacking.Spacer;
import free_ui.stacking.StackManager;
import free_ui.stacking.VStack;

public class ConfirmExitPage extends Page {
    Label symbol;
    Label title;
    Label[] descriptions;
    Button firstOption;
    Button secondOption;

    String firstOptionText;
    String secondOptionText;
    String titleText;
    String[] descriptionsText;

    public ConfirmExitPage(String firstOption, String secondOption, String title, String... descriptions) {
        super();

        this.firstOptionText = firstOption;
        this.secondOptionText = secondOption;
        this.titleText = title;
        this.descriptionsText = descriptions;
    }

    public static ConfirmExitPage defaultExitPanel() {
        return new ConfirmExitPage("Schließen", "Zurück", "Fenster schließen?",
                "Womöglich gibt es ungespeicherte Änderungen.", "Möchtest du dieses Fenster wirklich schließen?");
    }

    public void firstButtonClicked() {
        dispose();
        getUIParent().defaultOnCloseOperation();
    }

    public void secondButtonClicked() {
        onClose();
    }

    @Override
    public void onClose() {
        getUIParent().setEnabled(true);
        super.onClose();
    }

    @Override
    public void launch() {
        getUIParent().setEnabled(false);

        setSize(500, 290);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("FREE CINEMA Backoffice: Schließen?");
        setResizable(false);

        symbol = Labels.xlText().centered();
        symbol.setFont(symbol.getFont().deriveFont(35f));
        symbol.setText("?");
        symbol.setSize(60, 60);

        this.title = Labels.largeBoldText().centered();
        this.title.setText(titleText);

        this.descriptions = new Label[descriptionsText.length];
        for (int i = 0; i < this.descriptionsText.length; i++) {
            this.descriptions[i] = Labels.text().centered();
            this.descriptions[i].setText(descriptionsText[i]);
            this.descriptions[i].setSize(this.descriptions[i].getWidth(), 20);
        }

        this.firstOption = new Button();
        this.firstOption.setText(firstOptionText);
        this.firstOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstButtonClicked();
            }
        });

        this.secondOption = new Button();
        this.secondOption.setText(secondOptionText);
        this.secondOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondButtonClicked();
            }
        });

        VStack mainStack = new VStack();
        mainStack.add(symbol);
        mainStack.add(this.title);
        mainStack.add(new Spacer(0.5f));

        for (JLabel l : this.descriptions) {
            l.setSize(l.getWidth(), 22);
            mainStack.add(l);
        }

        mainStack.add(new Spacer(0.9f));

        HStack hstack1 = new HStack();
        hstack1.add(new Spacer(0.1f));
        hstack1.add(this.firstOption);
        hstack1.add(new Spacer(0.1f));
        hstack1.add(this.secondOption);
        hstack1.add(new Spacer(0.1f));
        mainStack.add(hstack1);

        StackManager stackManager = new StackManager(mainStack, 20, 2);
        stackManager.build(this);

        symbol.setBorder(UIDesigner.getThinBorder());

        setVisible(true);
    }

}
