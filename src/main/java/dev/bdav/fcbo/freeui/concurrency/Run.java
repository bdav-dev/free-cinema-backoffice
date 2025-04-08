package dev.bdav.fcbo.freeui.concurrency;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Run<I, R> {
    private final Function<InterimPublisher<I>, R> asyncTask;
    private final List<Consumer<Exception>> exceptionHandlers;

    private Consumer<R> then;
    private Consumer<I> interimProcessor;

    private final List<JComponent> lockComponents;
    private final List<JComponent> lockedComponents;

    private Run(Function<InterimPublisher<I>, R> asyncTask) {
        this.asyncTask = asyncTask;
        then = r -> { };
        interimProcessor = i -> { };
        exceptionHandlers = new ArrayList<>();
        lockComponents = new ArrayList<>();
        lockedComponents = new ArrayList<>();
    }

    public static <I, R> Run<I, R> async(Function<InterimPublisher<I>, R> asyncTask) {
        return new Run<>(asyncTask);
    }

    public static <R> Run<Void, R> async(Supplier<R> asyncTask) {
        return new Run<>(interimPublisher -> asyncTask.get());
    }

    public static Run<Void, Void> async(Runnable asyncTask) {
        return new Run<>(interimPublisher -> {
            asyncTask.run();
            return null;
        });
    }

    public Run<I, R> then(Consumer<R> then) {
        this.then = then;
        return this;
    }

    public Run<I, R> processInterim(Consumer<I> processor) {
        interimProcessor = processor;
        return this;
    }

    public <E extends Exception> Run<I, R> handle(Class<E> clazz, Consumer<E> handler) {
        exceptionHandlers.add(exception -> handler.accept(clazz.cast(exception)));
        return this;
    }


    public Run<I, R> handle(Consumer<Exception> handler) {
        exceptionHandlers.add(handler);
        return this;
    }

    public Run<I, R> lock(JComponent... components) {
        lockComponents.addAll(Arrays.asList(components));
        return this;
    }

    public void run() {
        lockComponents();
        createSwingWorker().execute();
    }

    private void lockComponents() {
        for (var component : lockComponents) {
            if (component.isEnabled()) {
                component.setEnabled(false);
                lockedComponents.add(component);
            }
        }
    }

    private void unlockComponents() {
        lockedComponents.forEach(component -> component.setEnabled(true));
    }

    private void handleException(Exception e) {
        for (var exceptionHandler : exceptionHandlers) {
            try {
                exceptionHandler.accept(e);
                return;
            } catch (ClassCastException ignored) {
            }
        }
    }

    private SwingWorker<R, I> createSwingWorker() {
        return new SwingWorker<>() {
            @Override
            protected R doInBackground() {
                R result = null;

                try {
                    result = asyncTask.apply(this::publish);
                } catch (Exception e) {
                    handleException(e);
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

                unlockComponents();

                if (result != null) {
                    then.accept(result);
                }
            }
        };
    }


}
