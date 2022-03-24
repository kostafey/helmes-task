package com.helmes;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import com.google.gson.Gson;

public class CategoryDAO {

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
            Transaction tx = HibernateUtil.getSession().beginTransaction();

            categories = HibernateUtil.getSession().createQuery(
                    "FROM Category WHERE parentId is null").list();

            tx.commit();
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
}
