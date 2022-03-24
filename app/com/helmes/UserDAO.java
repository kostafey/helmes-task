package com.helmes;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class UserDAO {

    public static void saveOrUpdate(User user) {
        Session session = HibernateUtil.getSession();
        try {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(user);
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

    public static Optional<User> getByName(String name) {
        Optional<User> user = Optional.empty();
        try {
            TypedQuery<User> q = HibernateUtil.getSession().createQuery(
                    "SELECT u FROM User u WHERE u.name = :name",
                User.class);
            List<User> result = q.setParameter("name", name).getResultList();
            if (result.size() > 0) {
                user = Optional.of(result.get(0));
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return user;
    }
}
