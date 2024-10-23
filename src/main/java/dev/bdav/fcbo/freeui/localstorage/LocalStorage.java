package dev.bdav.fcbo.freeui.localstorage;

import dev.bdav.fcbo.freeui.configuration.LocalStorageConfiguration;

import java.util.Optional;
import java.util.prefs.Preferences;

public class LocalStorage {
    private Preferences userPreferences;

    private static LocalStorage instance;
    private static String nodeName;

    static {
        nodeName = "default";
    }

    private LocalStorage() {
        userPreferences = Preferences.userRoot().node("fcbo");
    }

    public static void initialize() {
        var configuredNodeName = LocalStorageConfiguration.getConfiguredNodeName();
        if (configuredNodeName.isEmpty()) {
            throw new RuntimeException("is not configured.");
        }
        nodeName = configuredNodeName.orElseThrow();
    }

    public static LocalStorage get() {
        if (instance == null)
            instance = new LocalStorage();

        return instance;
    }

    public void remove(String key) {
        userPreferences.remove(key);
    }

    public void set(String key, Object value) {
        if (value == null)
            return;

        userPreferences.put(key, encryptedValue);
    }

    public Optional<String> getString(String key) {
        Optional<String> encryptedValue = Optional.ofNullable(userPreferences.get(key, null));

        if (encryptedValue.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(encryptedValue.orElseThrow());
    }

    public Optional<Integer> getInt(String key) {
        Optional<String> value = getString(key);

        if (value.isEmpty()) {
            return Optional.empty();
        }


        try {
            return Optional.of(Integer.valueOf(value.get()));
        } catch (Exception e) {
        }

        return Optional.empty();
    }

    public Optional<Double> getDouble(String key) {
        Optional<String> value = getString(key);

        if (value.isEmpty()) {
            return Optional.empty();
        }


        try {
            return Optional.of(Double.valueOf(value.get()));
        } catch (Exception e) {
        }

        return Optional.empty();
    }

}