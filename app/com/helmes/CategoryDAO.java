package com.helmes;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

class CategoryDAO {
    private static SessionFactory factory = new Configuration().configure().buildSessionFactory();

    public static List<Category> list() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Category> categories = null;
        try {
            tx = session.beginTransaction();

            categories = session.createQuery(
                "FROM Category WHERE parentId is null").list();

            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return categories;
    }
}
