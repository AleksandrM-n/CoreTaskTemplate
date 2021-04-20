package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction tx = null;
        try {
            Session session = Util.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS Users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)";
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (tx != null) {
                tx.commit();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tx = null;
        try {
            Session session = Util.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS Users";
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (tx != null) {
                tx.commit();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        try {
            Session session = Util.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            session.save(new User(name, lastName, age));
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (tx != null) {
                tx.commit();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try {
            Session session = Util.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("DELETE User WHERE id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (tx != null) {
                tx.commit();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction tx = null;
        List<User> list = null;
        try {
            Session session = Util.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            list = session.createQuery("FROM " + User.class.getSimpleName()).list();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (tx != null) {
                tx.commit();
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        try {
            Session session = Util.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            Query query = session.createSQLQuery("TRUNCATE TABLE Users");
            query.executeUpdate();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (tx != null) {
                tx.commit();
            }
        }
    }
}
