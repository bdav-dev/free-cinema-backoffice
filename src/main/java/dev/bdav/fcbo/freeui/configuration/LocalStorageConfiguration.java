package dev.bdav.fcbo.freeui.configuration;

import java.util.Optional;

public class LocalStorageConfiguration {
    private static String nodeName = null;

    private LocalStorageConfiguration() {
    }

    public static void configureNodeName(String node) {
        nodeName = node;
    }

    public static Optional<String> getConfiguredNodeName() {
        return Optional.ofNullable(nodeName);
    }

}
