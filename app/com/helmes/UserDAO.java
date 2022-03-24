package com.helmes;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.Session;

public class UserDAO {

    public static void add(User user) {
        Session session = HibernateUtil.getSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public static User get(Integer id) {
        User user = null;
        try {
            user = (User) HibernateUtil.getSession().get(
                    User.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return user;
    }
}
