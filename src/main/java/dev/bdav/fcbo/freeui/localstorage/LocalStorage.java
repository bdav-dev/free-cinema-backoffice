package dev.bdav.fcbo.freeui.localstorage;

import java.util.Optional;
import java.util.prefs.Preferences;

public class LocalStorage {
    private final Preferences preferences;

    private static LocalStorage defaultStorage;

    public LocalStorage(String nodeName) {
        preferences = Preferences.userRoot().node(nodeName);
    }

    public static void initializeDefaultStorage(String defaultNodeName) {
        defaultStorage = new LocalStorage(defaultNodeName);
    }

    public static LocalStorage defaultStorage() {
        if (defaultStorage == null) {
            throw new RuntimeException("Default not initialized.");
        }

        return defaultStorage;
    }

    public void remove(String key) {
        preferences.remove(key);
    }

    public void set(String key, Object value) {
        preferences.put(key, value.toString());
    }

    public Optional<String> getString(String key) {
        return Optional.ofNullable(preferences.get(key, null));
    }

    public Optional<Integer> getInt(String key) {
        return getString(key).map(string -> {
            try {
                return Integer.parseInt(string);
            } catch (NumberFormatException e) {
                return null;
            }
        });
    }

    public Optional<Double> getDouble(String key) {
        return getString(key).map(string -> {
            try {
                return Double.parseDouble(string);
            } catch (NumberFormatException e) {
                return null;
            }
        });
    }

}