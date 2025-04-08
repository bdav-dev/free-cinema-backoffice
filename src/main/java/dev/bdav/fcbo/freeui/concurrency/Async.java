package dev.bdav.fcbo.freeui.concurrency;

import javax.swing.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Async {

    private Async() {
    }

    public static <I, R> void run(
            Function<InterimPublisher<I>, R> asyncTask,
            Consumer<I> interimProcessor,
            Consumer<Exception> handle,
            Consumer<R> then
    ) {
        new SwingWorker<R, I>() {
            @Override
            protected R doInBackground() {
                R result = null;

                try {
                    result = asyncTask.apply(this::publish);
                } catch (Exception e) {
                    handle.accept(e);
                }

                return result;
            }

            @Override
            protected void process(List<I> chunks) {
                interimProcessor.accept(chunks.getFirst());
            }

            @Override
            protected void done() {
                R result = null;

                try {
                    result = get();
                } catch (Exception ignored) {
                }

                if (result != null) {
                    then.accept(result);
                }
            }
        }.execute();
    }

    public static <R> void run(
            Supplier<R> asyncTask,
            Consumer<Exception> handle,
            Consumer<R> then
    ) {
        run(
                interimPublisher -> asyncTask.get(),
                interimValue -> { },
                handle,
                then
        );
    }

    public static <R> void runWithLock(
            JComponent lockComponent,
            Supplier<R> asyncTask,
            Consumer<Exception> handle,
            Consumer<R> then
    ) {
        lockComponent.setEnabled(false);

        run(
                asyncTask,
                exception -> {
                    lockComponent.setEnabled(true);
                    handle.accept(exception);
                },
                result -> {
                    lockComponent.setEnabled(true);
                    then.accept(result);
                }
        );
    }

}
