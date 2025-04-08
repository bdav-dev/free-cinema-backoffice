package dev.bdav.fcbo.freeui.util;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class InputValidation {

    private InputValidation() {
    }


    public static void create(
            Consumer<Runnable> trigger,
            List<Supplier<String>> valueSuppliers,
            Function<List<String>, Boolean> trueFalseFunction,
            Consumer<Boolean> trueFalseConsumer
    ) {
        Runnable test = () -> {
            var values = valueSuppliers.stream()
                    .map(Supplier::get)
                    .toList();
            var trueFalse = trueFalseFunction.apply(values);
            trueFalseConsumer.accept(trueFalse);
        };

        trigger.accept(test);
        test.run();
    }

    public static void valueChangedListener(JTextField textField, Runnable runnable) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                runnable.run();
            }
        });
    }

}
