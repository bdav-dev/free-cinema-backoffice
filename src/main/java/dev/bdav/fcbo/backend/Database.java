package dev.bdav.fcbo.backend;

import dev.bdav.fcbo.backend.exception.DatabaseConfigurationException;
import dev.bdav.fcbo.backend.model.DbMember;
import dev.bdav.fcbo.backend.model.misc.DatabaseCredentials;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Optional;

public class Database {
    private static DatabaseCredentials credentialsUsedForConfiguration;
    private static SessionFactory sessionFactory;

    private Database() {
    }

    public static void configure(DatabaseCredentials credentials) {
        if (!credentials.isComplete()) {
            throw new DatabaseConfigurationException("Invalid database credentials.");
        }

        var config = createConfiguration(
                "jdbc:mysql://" + credentials.url().orElseThrow(),
                credentials.username().orElseThrow(),
                credentials.password().orElseThrow()
        );

        credentialsUsedForConfiguration = credentials;

        sessionFactory = config.buildSessionFactory();
    }

    public static boolean isConfigured() {
        return sessionFactory != null;
    }

    public static SessionFactory sessionFactory() {
        if (sessionFactory == null) {
            throw new DatabaseConfigurationException("Database was not configured");
        }

        return sessionFactory;
    }

    public static Optional<DatabaseCredentials> getCredentialsUsedForConfiguration() {
        return Optional.ofNullable(credentialsUsedForConfiguration);
    }

    private static Configuration createConfiguration(String url, String username, String password) {
        return new Configuration()
                .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .setProperty("hibernate.connection.url", url)
                .setProperty("hibernate.connection.username", username)
                .setProperty("hibernate.connection.password", password)
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .setProperty("hibernate.show_sql", "true")
                .addAnnotatedClass(DbMember.class);
    }

}
