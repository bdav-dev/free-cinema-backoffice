package dev.bdav.fcbo.frontend.storage;

import dev.bdav.fcbo.backend.model.misc.DatabaseCredentials;
import dev.bdav.fcbo.freeui.localstorage.LocalStorage;
import dev.bdav.fcbo.freeui.localstorage.LocalStorageSetting;

public class DatabaseCredentialsStorage {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.pw";

    private final LocalStorageSetting<String> url;
    private final LocalStorageSetting<String> username;
    private final LocalStorageSetting<String> password;

    private static DatabaseCredentialsStorage instance;

    private DatabaseCredentialsStorage() {
        this.url = new LocalStorageSetting<>(
                LocalStorage.defaultStorage(),
                URL_KEY,
                LocalStorageSetting.FromStringConverters.STRING
        );
        this.username = new LocalStorageSetting<>(
                LocalStorage.defaultStorage(),
                USERNAME_KEY,
                LocalStorageSetting.FromStringConverters.STRING
        );
        this.password = new LocalStorageSetting<>(
                LocalStorage.defaultStorage(),
                PASSWORD_KEY,
                LocalStorageSetting.FromStringConverters.STRING
        );
    }

    public static DatabaseCredentialsStorage get() {
        if (instance == null) {
            instance = new DatabaseCredentialsStorage();
        }

        return instance;
    }

    public LocalStorageSetting<String> url() {
        return url;
    }

    public LocalStorageSetting<String> username() {
        return username;
    }

    public LocalStorageSetting<String> password() {
        return password;
    }

    public void save(DatabaseCredentials credentials) {
        url.set(credentials.url().orElseThrow());
        username.set(credentials.username().orElseThrow());
        password.set(credentials.password().orElseThrow());
    }

    public DatabaseCredentials pack() {
        return DatabaseCredentials.fromOptionals(
                url.get(), username.get(), password.get()
        );
    }

}
