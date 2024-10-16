package dev.bdav.fcbo.freeui.async;

import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.SwingWorker;

public class Concurrency {

    private Concurrency() {
    }

    public static <T> void async(
        Supplier<T> runAsync,
        Consumer<T> whenDone
    ) {
        new SwingWorker<T, T>() {
            @Override
            protected T doInBackground() throws Exception {
                return runAsync.get();
            }

            @Override
            protected void done() {
                try {
                    whenDone.accept(get());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } catch (ExecutionException ex) {
                }
            }

        }.execute();
    }

    public static void async(
        Runnable runAsync,
        Runnable whenDone
    ) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                runAsync.run();
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    whenDone.run();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } catch (ExecutionException ex) {
                }
            }

        }.execute();
    }

}
