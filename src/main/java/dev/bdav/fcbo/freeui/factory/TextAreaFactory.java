package dev.bdav.fcbo.freeui.factory;

import dev.bdav.fcbo.freeui.components.VerticalScrollPane;
import dev.bdav.fcbo.freeui.stacking.StackBuilder;

import javax.swing.*;

public class TextAreaFactory {

    public static VerticalScrollPane readonlyScrollable(String text) {
        var textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCaretColor(textArea.getBackground());

        return new VerticalScrollPane(
                StackBuilder.vertical()
                        .content(textArea)
                        .build()
        );
    }


}
