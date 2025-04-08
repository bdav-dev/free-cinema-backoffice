package dev.bdav.fcbo.backend.model.misc;

import dev.bdav.fcbo.util.StringUtils;

import java.util.Objects;
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

    public Optional<String> url() {
        return Optional.ofNullable(url);
    }

    public Optional<String> username() {
        return Optional.ofNullable(username);
    }

    public Optional<String> password() {
        return Optional.ofNullable(password);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DatabaseCredentials that)) return false;
        return Objects.equals(url, that.url)
                && Objects.equals(username, that.username)
                && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, username, password);
    }

}
