package dev.bdav.fcbo.backend;

import dev.bdav.fcbo.backend.model.DbMember;
import dev.bdav.fcbo.backend.model.misc.DatabaseCredentials;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Database {

    private static SessionFactory sessionFactory;

    private Database() {
    }

    public static void initialize(DatabaseCredentials credentials) {
        if (!credentials.isComplete()) {
            throw new RuntimeException("Database credentials is incomplete"); // TODO change to custom exception
        }

        Configuration config = new Configuration()
                .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .setProperty(
                        "hibernate.connection.url",
                        "jdbc:mysql://" + credentials.getUrl().orElseThrow()
                ) //localhost:3306/fcbo
                .setProperty("hibernate.connection.username", credentials.getUsername().orElseThrow())
                .setProperty("hibernate.connection.password", credentials.getPassword().orElseThrow())
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .setProperty("hibernate.show_sql", "true")
                .addAnnotatedClass(DbMember.class);

        sessionFactory = config.buildSessionFactory();
    }

    public static SessionFactory sessionFactory() {
        return sessionFactory;
    }

}
