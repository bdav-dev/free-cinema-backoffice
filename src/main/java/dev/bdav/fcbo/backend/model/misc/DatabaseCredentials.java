package dev.bdav.fcbo.backend.model.misc;

import dev.bdav.fcbo.util.StringUtils;

import java.util.Optional;
import java.util.stream.Stream;

public class DatabaseCredentials {
    private final String url, username, password;

    public DatabaseCredentials(
            String url,
            String username,
            String password
    ) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DatabaseCredentials fromOptionals(
            Optional<String> url,
            Optional<String> username,
            Optional<String> password
    ) {
        return new DatabaseCredentials(
                url.orElse(null),
                username.orElse(null),
                password.orElse(null)
        );
    }

    public boolean isComplete() {
        return Stream.of(url, username, password)
                .noneMatch(StringUtils::isBlank);
    }

    public Optional<String> getUrl() {
        return Optional.ofNullable(url);
    }

    public Optional<String> getUsername() {
        return Optional.ofNullable(username);
    }

    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }
}
