package com.i2i.util;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;


/**
 * <p>
 * This class will have the main methods to instantiate hibernate session.
 * </p>
 * 
 * @author Mokeshwaran
 * @version 1.0 2022-09-16
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory = null;

    static {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(com.i2i.model.Employee.class);
        configuration.addAnnotatedClass(com.i2i.model.Role.class);
        configuration.configure();
        StandardServiceRegistryBuilder serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        sessionFactory =
                configuration.buildSessionFactory(serviceRegistry.build());
    }

    /**
     * <p>
     * This method will return the SessionFactory to DAO.
     * </p>
     * 
     * @return SessionFactory sessionFactory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}