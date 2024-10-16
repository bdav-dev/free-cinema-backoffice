package dev.bdav.fcbo.backend.service;

import dev.bdav.fcbo.backend.Database;

public class AccountService {
    private static AccountService instance;

    private AccountService() {
    }

    public static AccountService get() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }

    public boolean doesAnyAccountExist() {
        boolean doesExist;

        try (var factory = Database.sessionFactory()) {
            var session = factory.openSession();
            session.beginTransaction();

            var size = session.createQuery("SELECT count(*) FROM DbMember", Long.class).getSingleResult();
            doesExist = size != 0;

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            return false;
        }

        return doesExist;
    }


}
