package ru.fintech.qa.homework.db.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.PersistenceException;
import java.util.List;

public class DbHibernateService {

    public Session createSession(Class className) {
        SessionFactory sessionFactory = new HibernateSessionCreator().getSessionFactory(className);
        Session session = sessionFactory.openSession();
        return session;
    }

    public List getAllFromTable(Class className) {
        Session session = createSession(className);

        return session.createNativeQuery("select * from "
                        + className.getSimpleName()
                , className)
                .getResultList();
    }

    public <T> TransactionStatus putRow(Class className, T object) {
        Session session = createSession(className);
        session.beginTransaction();
        session.save(object);
        try {
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println("Невозможно добавить строку!!! Поймано исключение!!!");
        }

        TransactionStatus transactionStatus = session.getTransaction().getStatus();
        session.close();
        return transactionStatus;
    }

    public Integer getMaxId(Class className) {
        Session session = createSession(className);

        Integer maxId = (Integer) session.createNativeQuery("select id from "
                + className.getSimpleName()
                + " order by id desc")
                .getResultList().get(0);

        session.close();
        return maxId;
    }

    public Integer getRowsCountByName(Class className) {
        Session session = createSession(className);

        Integer count = session.createNativeQuery("select distinct \"name\" from "
                + className.getSimpleName()
                + " where \"name\" = 'Центральный'"
                + " or \"name\" = 'Северный'"
                + " or \"name\" = 'Западный'")
                .getResultList().size();
        return count;
    }
}
