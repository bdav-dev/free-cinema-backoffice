package dev.bdav.fcbo.backend;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import dev.bdav.fcbo.backend.model.DbMember;

public class Database {

    private static SessionFactory sessionFactory;

    private Database() {
    }

    public static void initialize() {
        Configuration config = new Configuration()
            .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
            .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/fcbo")
            //.setProperty("hibernate.connection.username", Secrets.DB_USER)
            //.setProperty("hibernate.connection.password", Secrets.DB_PASSWORD)
            .setProperty("hibernate.hbm2ddl.auto", "update")
            .setProperty("hibernate.show_sql", "true")
            .addAnnotatedClass(DbMember.class);

        sessionFactory = config.buildSessionFactory();
    }

    public static SessionFactory sessionFactory() {
        return sessionFactory;
    }

}
