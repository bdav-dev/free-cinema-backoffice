package utility;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;

public class Concurrency {
    private static final Executor executor = Executors.newCachedThreadPool();
    
    public static void async(Runnable r) {
        SwingUtilities.invokeLater(() -> {
            CompletableFuture.runAsync(r, executor);
        });
    }
    
}