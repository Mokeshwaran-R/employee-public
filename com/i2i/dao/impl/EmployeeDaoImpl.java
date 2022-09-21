package com.i2i.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.ScrollableResults;
import org.hibernate.ScrollMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.SessionFactory;
import org.hibernate.Query;

import com.i2i.constants.Constants;
import com.i2i.dao.EmployeeDao;
import com.i2i.exceptions.EmployeeException;
import com.i2i.model.Employee;
import com.i2i.model.Role;
import com.i2i.util.HibernateUtil;


/**
 * <p>
 * TraineeDaoImpl will have the list as storage and will be used in db.
 * </p>
 * 
 * @author Mokeshwaran
 * @version 1.0 2022-08-11
 */
public class EmployeeDaoImpl implements EmployeeDao {
    
    List<Employee> trainees = new ArrayList<Employee>();
    private static Logger logger = LogManager.getLogger(EmployeeDaoImpl.class);
    
    /**
     * <p>
     * retrieveId will retrieve the id from the employees table.
     * </p>
     * 
     * @return int id
     */
    public int retrieveId() throws EmployeeException {
        int id = 0;
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Employee employee = (Employee) session.createQuery("FROM Employee ORDER BY id DESC").setMaxResults(1).uniqueResult();
            id = employee.getId() + 1;
        } catch (HibernateException hibernateException) {
            logger.error(Constants.HIBERNATE_EXCEPTION, hibernateException.getMessage());
            throw new EmployeeException(hibernateException.getMessage());
        }
        return id;
    }

    /**
     * <p>
     * This method is used to retrieve a trainee's batchId based on ID given
     * by the user.
     * </p>
     * 
     * (e.g): ID = 130 (existing value), will return Trainee's batchId with ID: 130.
     * 
     * @param int id
     *     id is given by the user from the EmployeeController.
     * @param String employeeRole
     * @return String batchId
     * @throws EmployeeException
     */
    public String retrieveTraineeBatchId(int id, String employeeRole) throws EmployeeException {
        Session session;
        List<String> batchIds = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("SELECT employee.batchId FROM Employee employee " + 
                                              "JOIN employee.roles roles WHERE employee.id = :id AND roles.roleName = :roleName");
            query.setParameter("id", id);
            query.setParameter("roleName", employeeRole);
            batchIds = query.list();
            session.close();
        } catch (HibernateException hibernateException) {
            logger.error(Constants.HIBERNATE_EXCEPTION, hibernateException.getMessage());
            throw new EmployeeException(hibernateException.getMessage());
        }
        return batchIds.get(0);
    }

    /**
     * <p>
     * This method will retrieve the total count of trainees in employees table.
     * </p>
     * 
     * (e.g): numberOfTrainees = 6 (total number of trainees)
     * 
     * @return long numberOfTrainees
     * @throws EmployeeException
     */
    public long retrieveTraineeCount() throws EmployeeException {
        Session session;
        long traineeCounts = 0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("SELECT COUNT(employee.id) FROM Employee employee " + 
                                              "JOIN employee.roles roles WHERE roles.roleName = :roleName");
            query.setParameter("roleName", Constants.TRAINEE_ROLE_NAME);
            traineeCounts = (long) query.uniqueResult();
            session.close();
        } catch (HibernateException hibernateException) {
            logger.error(Constants.HIBERNATE_EXCEPTION, hibernateException.getMessage());
            throw new EmployeeException(hibernateException.getMessage());
        }
        return traineeCounts;
    }


    /**
     * <p>
     * This method will will add new employee to the employees db.
     * </p>
     * 
     * @param Employee employee
     *     employee contains the list of employee details.
     * @throws EmployeeException
     */
    public void insertEmployee(Employee employee) throws EmployeeException {
        Session session;
        Transaction transaction;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
            session.close();
        } catch (HibernateException hibernateException) {
            logger.error(Constants.HIBERNATE_EXCEPTION, hibernateException.getMessage());
            throw new EmployeeException(hibernateException.getMessage());
        }
    }

    /**
     * <p>
     * This method will get the employee role from the database.
     * </p>
     * 
     * @param String employeeRole
     * @return Role roles
     * @throws EmployeeException
     */
    public Role getEmployeeRole(String employeeRole) throws EmployeeException {
        Session session;
        Transaction transaction;
        if (employeeRole == Constants.TRAINEE_ROLE_NAME) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();
                Query query = session.createQuery("SELECT role FROM Role role where role.roleName = :roleName");
                query.setParameter("roleName", employeeRole);
                List<Role> roles = query.list();
                transaction.commit();
                session.close();
                return roles.get(0);
            } catch (HibernateException hibernateException) {
                logger.error(Constants.HIBERNATE_EXCEPTION, hibernateException);
                throw new EmployeeException(hibernateException.getMessage());
            }
        } else {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();
                Query query = session.createQuery("SELECT role FROM Role role where role.roleName = :roleName");
                query.setParameter("roleName", employeeRole);
                List<Role> roles = query.list();
                transaction.commit();
                session.close();
                return roles.get(0);
            } catch (HibernateException hibernateException) {
                logger.error(Constants.HIBERNATE_EXCEPTION, hibernateException.getMessage());
                throw new EmployeeException(hibernateException.getMessage());
            }
        }
    }

    /**
     * <p>
     * This method will get the employees from the database.
     * </p>
     * 
     * @param String employeeRole
     * @param int initialResult
     * @return List<Employee> employees
     * @throws EmployeeException
     */
    public List<Employee> retrieveEmployees(String employeeRole, int initialResult) throws EmployeeException {
        Session session;     
        int pageSize = 5;
        if (employeeRole == Constants.TRAINEE_ROLE_NAME) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                Query query = session.createQuery("SELECT employee FROM Employee employee " + 
                                                  "JOIN employee.roles roles WHERE roles.roleName = :roleName");
                query.setParameter("roleName", employeeRole);
                query.setFirstResult(initialResult);
                query.setMaxResults(pageSize);
                List<Employee> trainees = query.list();
                session.close();
                return trainees;
            } catch (HibernateException hibernateException) {
                logger.error(Constants.HIBERNATE_EXCEPTION, hibernateException.getMessage());
                throw new EmployeeException(hibernateException.getMessage());
            }
        } else {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                Query query = session.createQuery("SELECT employee FROM Employee employee " + 
                                                  "JOIN employee.roles roles WHERE roles.roleName = :roleName");
                query.setParameter("roleName", employeeRole);
                query.setFirstResult(initialResult);
                query.setMaxResults(pageSize);
                List<Employee> trainers = query.list();
                session.close();
                return trainers;
            } catch (HibernateException hibernateException) {
                logger.error(Constants.HIBERNATE_EXCEPTION, hibernateException.getMessage());
                throw new EmployeeException(hibernateException.getMessage());
            }
        }
    }

    /**
     * <p>
     * This method is used to retrieve a trainee based on ID given by the user.
     * </p>
     * 
     * (e.g): ID = 130 (existing value), will return Trainee with ID: 130.
     * 
     * @param int id
     *     id is given by the user from the EmployeeController.
     * @param String employeeRole
     * @return Employee employee
     * @throws EmployeeException
     */
    public Employee retrieveEmployeeById(int id, String employeeRole) throws EmployeeException {
        Session session;
        if (employeeRole == Constants.TRAINEE_ROLE_NAME) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                Query query = session.createQuery("SELECT employee FROM Employee employee " + 
                                                  "JOIN employee.roles roles WHERE " +
                                                  "roles.roleName = :roleName AND employee.id = :id");
                query.setParameter("roleName", employeeRole);
                query.setParameter("id", id);
                List<Employee> trainers = query.list();
                session.close();
                return trainers.get(0);
            } catch (HibernateException hibernateException) {
                logger.error(Constants.HIBERNATE_EXCEPTION, hibernateException.getMessage());
                throw new EmployeeException(hibernateException.getMessage());
            }
        } else {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                Query query = session.createQuery("SELECT employee FROM Employee employee " + 
                                                  "JOIN employee.roles roles WHERE " +
                                                  "roles.roleName = :roleName AND employee.id = :id");
                query.setParameter("roleName", employeeRole);
                query.setParameter("id", id);
                List<Employee> trainers = query.list();
                session.close();
                return trainers.get(0);
            } catch (HibernateException hibernateException) {
                logger.error(Constants.HIBERNATE_EXCEPTION, hibernateException.getMessage());
                throw new EmployeeException(hibernateException.getMessage());
            }
        }
    }

    /**
     * <p>
     * This method will check if the employee exist in the database.
     * </p>
     *
     * @param int id
     * @param String employeeRole
     * @return boolean true if employee is present or else otherwise.
     * @throws EmployeeException
     */
    public boolean isEmployeeExist(int id, String employeeRole) throws EmployeeException {
        Session session;
        if (employeeRole == Constants.TRAINEE_ROLE_NAME) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                Query query = session.createQuery("SELECT employee FROM Employee " +
                                                  "employee JOIN employee.roles " + 
                                                  "roles WHERE roles.roleName = :roleName AND employee.id = :id");
                query.setParameter("roleName", employeeRole);
                query.setParameter("id", id);
                List<Employee> trainers = query.list();
                session.close();
                return trainers.isEmpty() ? false : true;
            } catch (HibernateException hibernateException) {
                logger.error(Constants.HIBERNATE_EXCEPTION, hibernateException.getMessage());
                throw new EmployeeException(hibernateException.getMessage());
            }
        } else {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                Query query = session.createQuery("SELECT employee FROM " + 
                                                  "Employee employee JOIN employee.roles " +
                                                  "roles WHERE roles.roleName = :roleName AND employee.id = :id");
                query.setParameter("roleName", employeeRole);
                query.setParameter("id", id);
                List<Employee> trainers = query.list();
                session.close();
                return trainers.isEmpty() ? false : true;
            } catch (HibernateException hibernateException) {
                logger.error(Constants.HIBERNATE_EXCEPTION, hibernateException.getMessage());
                throw new EmployeeException(hibernateException.getMessage());
            }
        }
    }

    /**
     * <p>
     * This method will delete a trainee based on id from the employees table.
     * </p>
     * 
     * @param int id
     *     id is given by the user from EmployeeController.
     * @param String employeeRole
     * @throws EmployeeException
     */
    public boolean deleteEmployeeById(int id, String employeeRole) throws EmployeeException {
        boolean isTransacted;
        Session session;
        Transaction transaction;
        if (employeeRole == Constants.TRAINEE_ROLE_NAME) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();
                Query query = session.createQuery("DELETE FROM com.i2i.model.Employee " + 
                                                  "WHERE id = :id");
                query.setParameter("id", id);
                int result = query.executeUpdate();
                transaction.commit();
                session.close();
                return isTransacted = result > 0 ? true : false;
            } catch (HibernateException hibernateException) {
                logger.error(Constants.HIBERNATE_EXCEPTION, hibernateException.getMessage());
                throw new EmployeeException(hibernateException.getMessage());
            }
        } else {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();
                Query query = session.createQuery("DELETE FROM com.i2i.model.Employee " + 
                                                  "WHERE id = :id");
                query.setParameter("id", id);
                int result = query.executeUpdate();
                transaction.commit();
                session.close();
                return isTransacted = result > 0 ? true : false;
            } catch (HibernateException hibernateException) {
                logger.error(Constants.HIBERNATE_EXCEPTION, hibernateException.getMessage());
                throw new EmployeeException(hibernateException.getMessage());
            }
        }
    }

    /**
     * <p>
     * This method will update a trainee based on id in the employees table. 
     * </p>
     * 
     * @param Employee employee
     * @param int id
     * @throws EmployeeException
     */
    public void updateEmployeeById(Employee employee, int id) throws EmployeeException {
        Session session;
        Transaction transaction;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
            session.close();
        } catch (HibernateException hibernateException) {
            logger.error(Constants.HIBERNATE_EXCEPTION, hibernateException.getMessage());
            throw new EmployeeException(hibernateException.getMessage());
        }
    }
}
