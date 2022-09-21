package com.i2i.service;

import java.util.Date;
import java.util.List;

import com.i2i.exceptions.EmployeeException;
import com.i2i.model.Employee;


/**
 * <p>
 * TraineeService interface will do the CRUD operation on data.
 * </p>
 * 
 * @author Mokeshwaran
 * @version 1.0 2022-08-10
 */
public interface EmployeeService {

    /**
     * {@inheritDoc}
     */
    int generateId() throws EmployeeException;

    /**
     * {@inheritDoc}
     */
    String generateBatchId() throws EmployeeException;

    /**
     * {@inheritDoc}
     */  
    String getTraineeBatchId(int id) throws EmployeeException;

    /**
     * {@inheritDoc}
     */
    List<Date> getTraineeDates(int id) throws EmployeeException;

    /**
     * {@inheritDoc}n
     */
    void createNewTrainee(Employee employee) throws EmployeeException;

    /**
     * {@inheritDoc}
     */   
    List<Employee> getAllTrainees(int initialResult) throws EmployeeException;

    /**
     * {@inheritDoc}
     */  
    Employee searchTraineeById(int id) throws EmployeeException;

    /**
     * {@inheritDoc}
     */ 
    boolean checkTrainee(int id) throws EmployeeException;

    /**
     * {@inheritDoc}
     */
    boolean deleteTrainee(int id) throws EmployeeException;

    /**
     * {@inheritDoc}
     */
    void updateTrainee(Employee employee) throws EmployeeException;

    /**
     * {@inheritDoc}
     */
    List<Date> getTrainerDates(int id) throws EmployeeException;
    
    /**
     * {@inheritDoc}
     */
    void createNewTrainer(Employee employee) throws EmployeeException;

    /**
     * {@inheritDoc}
     */   
    List<Employee> getAllTrainers(int initialResult) throws EmployeeException;

    /**
     * {@inheritDoc}
     */ 
    Employee searchTrainerById(int id) throws EmployeeException;

    /**
     * {@inheritDoc}
     */
    boolean checkTrainer(int id) throws EmployeeException;

    /**
     * {@inheritDoc}
     */
    boolean deleteTrainer(int id) throws EmployeeException;

    /**
     * {@inheritDoc}
     */
    void updateTrainer(Employee employee) throws EmployeeException;

}