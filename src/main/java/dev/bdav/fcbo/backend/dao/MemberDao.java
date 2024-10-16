package dev.bdav.fcbo.backend.dao;

import org.hibernate.Session;

import dev.bdav.fcbo.backend.Database;
import dev.bdav.fcbo.backend.model.DbMember;

public class MemberDao {
    private static MemberDao instance;

    private MemberDao() {
    }

    public static MemberDao get() {
        if (instance == null) {
            instance = new MemberDao();
        }

        return instance;
    }

    public void save() {
        var t = new DbMember();

        t.setFirstName("David");
        t.setLastName("Berezowski");
        t.setUsername("bdav");
        t.setIsActive(true);

        try(Session session = Database.sessionFactory().openSession()) {
            session.beginTransaction();

            session.persist(t);

            session.getTransaction().commit();
        }
    }

}
