package localstorage;

import java.util.Optional;
import java.util.prefs.Preferences;

import utility.CryptographicUtility;
import utility.CryptographicUtility.EncryptionKeys;

public class LocalStorage {
    private Preferences userPreferences;
    private CryptographicUtility cryptographicUtilitly;

    private static LocalStorage instance;

    private LocalStorage() {
        userPreferences = Preferences.userRoot().node("fcbo");
        cryptographicUtilitly = CryptographicUtility.getInstance();
    }

    public static LocalStorage getInstance() {
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

        String encryptedValue = cryptographicUtilitly.encrypt(value.toString(), EncryptionKeys.REGISTRY);
        userPreferences.put(key, encryptedValue);
    }

    public Optional<String> get(String key) {
        Optional<String> encryptedValue = Optional.ofNullable(userPreferences.get(key, null));

        if (encryptedValue.isEmpty())
            return Optional.empty();

        return Optional.of(cryptographicUtilitly.decrypt(encryptedValue.orElseThrow(), EncryptionKeys.REGISTRY));
    }

    public Optional<Integer> getInt(String key) {
        Optional<String> value = get(key);

        if (value.isEmpty())
            return Optional.empty();

        try {
            return Optional.of(Integer.valueOf(value.get()));
        } catch (Exception e) {
        }

        return Optional.empty();
    }

    public Optional<Double> getDouble(String key) {
        Optional<String> value = get(key);

        if (value.isEmpty())
            return Optional.empty();

        try {
            return Optional.of(Double.valueOf(value.get()));
        } catch (Exception e) {
        }

        return Optional.empty();
    }

}