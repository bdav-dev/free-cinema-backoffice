package dev.bdav.fcbo.backend.service;

import dev.bdav.fcbo.backend.Database;
import dev.bdav.fcbo.backend.exception.DatabaseConfigurationException;

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

        try (var session = Database.sessionFactory().openSession()) {
            var size = session.createQuery("SELECT count(*) FROM DbMember", Long.class).getSingleResult();
            doesExist = size != 0;

        } catch (DatabaseConfigurationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return doesExist;
    }


}
