package ru.fintech.qa.homework.db.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateSessionCreator {
    private SessionFactory sessionFactory = null;

    public SessionFactory getSessionFactory(Class c) {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory(c);
        }
        return sessionFactory;
    }

    private SessionFactory buildSessionFactory(Class c) {
        return new Configuration()
                .configure()
                .addAnnotatedClass(c)
                .buildSessionFactory();
    }
}
