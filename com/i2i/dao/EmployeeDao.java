package com.i2i.dao;

import java.util.List;

import com.i2i.exceptions.EmployeeException;
import com.i2i.model.Employee;
import com.i2i.model.Role;


/**
 * <p>
 * TraineeDao interface contains the methods from TraineeDaoImpl.
 * </p>
 *
 * @author Mokeshwaran
 * @version 1.0 2022-08-12
 */
public interface EmployeeDao {

    /**
     * {@inheritDoc}
     */
    int retrieveId() throws EmployeeException;

    /**
     * {@inheritDoc}
     */
    String retrieveTraineeBatchId(int id, String employeeRole) throws EmployeeException;

    /**
     * {@inheritDoc}
     */
    long retrieveTraineeCount() throws EmployeeException;

    /**
     * {@inheritDoc}
     */
    void insertEmployee(Employee employee) throws EmployeeException;

    /**
     * {@inheritDoc}
     */
    Role getEmployeeRole(String employeeRole) throws EmployeeException;

    /**
     * {@inheritDoc}
     */
    List<Employee> retrieveEmployees(String employeeRole, int initialResult) throws EmployeeException;

    /**
     * {@inheritDoc}
     */
    Employee retrieveEmployeeById(int id, String employeeRole) throws EmployeeException;

    /**
     * {@inheritDoc}
     */
    boolean deleteEmployeeById(int id, String employeeRole) throws EmployeeException;

    /**
     * {@inheritDoc}
     */
    void updateEmployeeById(Employee employee, int id) throws EmployeeException;

    /**
     * {@inheritDoc}
     */
    boolean isEmployeeExist(int id, String employeeRole) throws EmployeeException; 
}
