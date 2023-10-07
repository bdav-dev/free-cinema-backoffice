package appsettings;

import localstorage.LocalStorage;

public class LocalStorageSetting<T> {
    private T value;
    private T fallback;
    private String localStorageKey;
    private FromString<T> fromString;

    public LocalStorageSetting(T value, String localStorageKey, T fallback, FromString<T> fromString) {
        this.localStorageKey = localStorageKey;
        this.fallback = fallback;
        this.fromString = fromString;
        set(value);
    }

    public LocalStorageSetting(String localStorageKey, T fallback, FromString<T> fromString) {
        this(null,
                localStorageKey,
                fallback,
                fromString);
        this.value = getValueInLocalStorage();
    }

    public T getValueInLocalStorage() {
        String valueAsString = LocalStorage.getInstance().get(localStorageKey).orElse(null);

        if (valueAsString == null)
            return fallback;

        return fromString.convert(valueAsString, fallback);
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
        LocalStorage.getInstance().set(localStorageKey, value);
    }

    public void setWithoutSavingToLocalStorage(T value) {
        this.value = value;
    }

    public void setOnlySavingToLocalStorage(T value) {
        LocalStorage.getInstance().set(localStorageKey, value);
    }

    public void setString(String valueAsString) {
        this.value = fromString.convert(valueAsString, fallback);
        LocalStorage.getInstance().set(localStorageKey, this.value);
    }

    public void setStringWithoutSavingToLocalStorage(String valueAsString) {
        this.value = fromString.convert(valueAsString, fallback);
    }

    public void setStringOnlySavingToLocalStorage(String valueAsString) {
        T value = fromString.convert(valueAsString, fallback);
        LocalStorage.getInstance().set(localStorageKey, value);
    }

    public T getFallback() {
        return fallback;
    }

}
