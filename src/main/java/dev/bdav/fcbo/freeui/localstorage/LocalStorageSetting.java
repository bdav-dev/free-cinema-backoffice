package dev.bdav.fcbo.freeui.localstorage;

import java.util.Optional;
import java.util.function.Function;

public class LocalStorageSetting<T> {
    private final String localStorageKey;
    private final Function<String, Optional<T>> fromStringConverter;
    private final Function<T, String> toStringConverter;
    private final LocalStorage localStorage;

    public LocalStorageSetting(
            LocalStorage localStorage,
            String localStorageKey,
            Function<String, Optional<T>> fromStringConverter,
            Function<T, String> toStringConverter
    ) {
        this.localStorage = localStorage;
        this.localStorageKey = localStorageKey;
        this.fromStringConverter = fromStringConverter;
        this.toStringConverter = toStringConverter;
    }

    public LocalStorageSetting(
            LocalStorage localStorage,
            String localStorageKey,
            Function<String, Optional<T>> fromStringConverter
    ) {
        this(localStorage, localStorageKey, fromStringConverter, Object::toString);
    }

    public Optional<T> get() {
        var localStorageValue = localStorage.getString(localStorageKey);

        if (localStorageValue.isEmpty()) {
            return Optional.empty();
        }

        return fromStringConverter.apply(localStorageValue.orElseThrow());
    }

    public void set(T value) {
        localStorage.set(localStorageKey, toStringConverter.apply(value));
    }

    public void remove() {
        localStorage.remove(localStorageKey);
    }

    public static class FromStringConverters {
        public final static Function<String, Optional<String>> STRING = Optional::ofNullable;

        public final static Function<String, Optional<Integer>> INTEGER = string -> {
            try {
                return Optional.of(Integer.parseInt(string));
            } catch (Exception e) {
                return Optional.empty();
            }
        };

        public final static Function<String, Optional<Double>> DOUBLE = string -> {
            try {
                return Optional.of(Double.parseDouble(string));
            } catch (Exception e) {
                return Optional.empty();
            }
        };

        public final static Function<String, Optional<Boolean>> BOOLEAN = string -> {
            try {
                try {
                    return Optional.of(Boolean.parseBoolean(string));
                } catch (Exception e) {
                    return Optional.empty();
                }
            } catch (Exception e) {
                return Optional.empty();
            }
        };

        private FromStringConverters() {
        }
    }

}
