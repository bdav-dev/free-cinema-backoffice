package dev.bdav.fcbo.freeui.components;

import javax.swing.JPanel;
import javax.swing.UIManager;

import dev.bdav.fcbo.freeui.sizing.Size;
import dev.bdav.fcbo.freeui.sizing.Sizing;
import dev.bdav.fcbo.freeui.stacking.HStack;
import dev.bdav.fcbo.freeui.stacking.StackBuilder;

public class HorizontalRuler extends HStack {
    
    public HorizontalRuler() {

        var line = new JPanel();

        Sizing.modify(line)
            .width(Size.lazyGrowing())
            .height(Size.fixed(1));

        line.setBackground(
            UIManager.getDefaults().getColor("Component.borderColor")
        );

        StackBuilder.modify(this)
            .content(line);
    }

}
