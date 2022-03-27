package com.helmes.db;

import java.util.List;

import com.helmes.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.Session;
import com.google.gson.Gson;

public class CategoryDAO {

    public static void saveOrUpdate(Category category) {
        Session session = HibernateUtil.getSession();
        try {
            Transaction tx = session.beginTransaction();
            if (category.getId() == null) {
                session.save(category);
            } else {
                session.update(category);
            }
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public static void delete(Category category) {
        Session session = HibernateUtil.getSession();
        try {
            Transaction tx = session.beginTransaction();
            session.delete(category);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
    }    

    public static Category get(Integer id) {
        Category category = null;
        try {
            category = (Category) HibernateUtil.getSession().get(
                    Category.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return category;
    }

    public static List<Category> list() {
        List<Category> categories = null;
        try {
            categories = HibernateUtil.getSession().createQuery(
                    "FROM Category WHERE parentId is null").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return categories;
    }

    public static String getCategoriesAsJson() {
        Gson gson = new Gson();
        return gson.toJson(CategoryDAO.list());
    }

    public static List<Category> flatList() {
        List<Category> categories = null;
        try {
            categories = HibernateUtil.getSession().createQuery(
                    "FROM Category").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return categories;
    }

    public static String getCategoriesFlatAsJson() {
        Gson gson = new Gson();
        return gson.toJson(CategoryDAO.flatList());
    }
}
