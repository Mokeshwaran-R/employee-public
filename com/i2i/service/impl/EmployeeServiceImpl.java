package com.i2i.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.i2i.constants.Constants;
import com.i2i.dao.EmployeeDao;
import com.i2i.dao.impl.EmployeeDaoImpl;
import com.i2i.exceptions.EmployeeException;
import com.i2i.model.Employee;
import com.i2i.model.Role;
import com.i2i.service.EmployeeService;


/**
 * <p>
 * TraineeServiceImpl will implements the TraineeService class.
 * </p>
 * 
 * @author Mokeshwaran
 * @version 1.0 2022-08-10
 */
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeDao employeeDao = new EmployeeDaoImpl();
    
    /**
     * <p>
     * This method will generate id for the employee.
     * </p>
     *
     * @return int id
     * @throws EmployeeException
     */
    public int generateId() throws EmployeeException {
        return employeeDao.retrieveId();
    }

    /**
     * <p>
     * This method will generate the batchId based on the count of trainees
     * for the trainee.
     * </p>
     * 
     * (e.g): If size of List<Trainee> is less than 5 then batchId = 1, less
     *     than 10 then batchId = 2 and so on.
     *
     * @return String batchId
     * @throws EmployeeException
     */
    public String generateBatchId() throws EmployeeException {
        Integer employeeCount = (int) employeeDao.retrieveTraineeCount();
        if (employeeCount != null) {
            return Constants.I2I_PREFIX + (int) ((employeeCount / 5) + 1);
        }
        return Constants.I2I_1;
    }

    /**
     * <p>
     * This method will get a specific trainee's Batch Id using ID from employees table.
     * </p>
     * 
     * (e.g): ID = 237, will return ID: 237 trainee's Batch ID.
     *
     * @param int id
     * @return specific trainee's Batch ID using ID from employeeDao to EmployeeController.
     * @throws EmployeeException
     */  
    public String getTraineeBatchId(int id) throws EmployeeException {
        return employeeDao.retrieveTraineeBatchId(id, Constants.TRAINEE_ROLE_NAME);
    }

    /**
     * <p>
     * This method will get the dates of birth and joining of trainees.
     * </p>
     * 
     * @param int id
     * @return List<Date>
     * @throws EmployeeException
     */
    public List<Date> getTraineeDates(int id) throws EmployeeException {
        List<Date> dates = new ArrayList<>(); 
        Employee trainee = employeeDao.retrieveEmployeeById(id, Constants.TRAINEE_ROLE_NAME);
        dates.add(trainee.getDateOfBirth());
        dates.add(trainee.getDateOfJoining());
        return dates;
    }

    /**
     * <p>
     * This method will add new trainee to employee table through EmployeeDAO.
     * </p>
     * 
     * (e.g): ID: 172 (auto-assigned), Name: Sherlock Holmes, Gender (M/F): M,
     * Email ID: holmes@holmes.com, Designation: Employee, Residential Address: 221B Baker Street,
     * Previous Experience: 6, Date Of Joining (dd-MM-yyyy): 12-12-2020,
     * Date Of Birth (dd-MM-yyyy): 10-08-1998, Employee's Name: Watson, Batch ID: 3,
     * Training Course: Java, Python, Dart
     *
     * @param Employee employee
     * @throws EmployeeException
     */
    public void createNewTrainee(Employee employee) throws EmployeeException {
        Set<Role> role = new HashSet<>();
        role.add(employeeDao.getEmployeeRole(Constants.TRAINEE_ROLE_NAME));
        employee.setRoles(role);
        employeeDao.insertEmployee(employee);
    }

    /**
     * <p>
     * This method will get all trainees from employees table through EmployeeDAO.
     * </p>
     *
     * @return List<Employee> trainees
     * @throws EmployeeException
     */    
    public List<Employee> getAllTrainees(int initialResult) throws EmployeeException {   
        return employeeDao.retrieveEmployees(Constants.TRAINEE_ROLE_NAME, initialResult);
    }

    /**
     * <p>
     * This method will get a specific trainee using ID from employees table through EmployeeDAO.
     * </p>
     * 
     * (e.g): ID = 237, will return specific trainee of ID: 237.
     *
     * @param int id
     * @return Employee employee
     * @throws EmployeeException
     */    
    public Employee searchTraineeById(int id) throws EmployeeException {
        return employeeDao.retrieveEmployeeById(id, Constants.TRAINEE_ROLE_NAME);
    }

    /**
     * <p>
     * This method will check for a specific trainee using ID in employees table through EmployeeDAO.
     * </p>
     * 
     * (e.g): ID = 125, will check for ID: 125 exist or not.
     *
     * @param int id
     * @return boolean true if trainee is present or false otherwise.
     * @throws EmployeeException
     */ 
    public boolean checkTrainee(int id) throws EmployeeException {
        return employeeDao.isEmployeeExist(id, Constants.TRAINEE_ROLE_NAME);
    }

    /**
     * <p>
     * This method will remove a specific trainee using ID from employees table through EmployeeDAO.
     * </p>
     *
     * (e.g): ID = 221, Trainee with ID: 221 will be removed from the employees table through EmployeeDAO.
     * 
     * @param int id
     * @return boolean true if trainee deleted or false otherwise.
     * @throws EmployeeException
     */
    public boolean deleteTrainee(int id) throws EmployeeException {
        return employeeDao.deleteEmployeeById(id, Constants.TRAINEE_ROLE_NAME);
    }
    
    /**
     * <p>
     * This method will update any trainee if exist in employees table through EmployeeDAO.
     * </p>
     * 
     * (e.g): ID: 172 (auto-assigned), Name: Sherlock Holmes, Gender (M/F): M,
     * Email ID: holmes@holmes.com, Designation: Employee, Residential Address: 221B Baker Street,
     * Previous Experience: 6, Date Of Joining (dd-MM-yyyy): 12-12-2020,
     * Date Of Birth (dd-MM-yyyy): 10-08-1998, Employee's Name: Watson, Batch ID: 3,
     * Training Course: Java, Python, Dart
     *
     * @param Employee employee
     * @throws EmployeeException
     */
    public void updateTrainee(Employee employee) throws EmployeeException {
        int id = employee.getId();
        Employee employee1 = employeeDao
                .retrieveEmployeeById(id, Constants.TRAINEE_ROLE_NAME);
        if (employee1.getId() == id) {
            if (!employee.getName().isEmpty()) {
                employee1.setName(employee.getName());
            }
                            
            if (!employee.getGender().isEmpty()) {
                employee1.setGender(employee.getGender());
            }
                        
            if (!employee.getEmailId().isEmpty()) {
                employee1.setEmailId(employee.getEmailId());
            }
                    
            if (!employee.getResiAddress().isEmpty()) {
                employee1.setResiAddress(employee.getResiAddress());
            }
                  
            if (employee.getPreviousExperience() >= 0) {
                employee1.setPreviousExperience(employee.getPreviousExperience());
            }
                      
            if (employee.getDateOfBirth() != null) {
                employee1.setDateOfBirth(employee.getDateOfBirth());
            }
                    
            if (employee.getDateOfJoining() != null) {
                employee1.setDateOfJoining(employee.getDateOfJoining());
            }
                   
            if (!employee.getTrainersName().isEmpty()) {
                employee1.setTrainersName(employee.getTrainersName());
            }
                    
            if (!employee.getTrainingCourse().isEmpty()) {
                employee1.setTrainingCourse(employee.getTrainingCourse());
            }
            employeeDao.updateEmployeeById(employee1, id);
        }
    }

    /**
     * <p>
     * This method will get the dates of birth and joining of trainers.
     * </p>
     * 
     * @param int id
     * @return List<Date> dates
     * @throws EmployeeException
     */
    public List<Date> getTrainerDates(int id) throws EmployeeException {
        List<Date> dates = new ArrayList<>(); 
        Employee trainer = employeeDao.retrieveEmployeeById(id, Constants.TRAINER_ROLE_NAME);
        dates.add(trainer.getDateOfBirth());
        dates.add(trainer.getDateOfJoining());
        return dates;
    }

    /**
     * <p>
     * This method will add new employee to employees table through EmployeeDAO.
     * </p>
     *
     * (e.g): ID: 172 (auto-assigned), Name: Holmes, Gender (M/F): M,
     * Email ID: holmes@holmes.com, Designation: Employee, Residential Address: 221B Baker Street,
     * Previous Experience: 6, Date Of Joining (dd-MM-yyyy): 12-12-2020,
     * Date Of Birth (dd-MM-yyyy): 10-08-1998, Trainee Count: 10, Training Experience: 3,
     * Preferred Skill for Training: Java, Python, Dart
     * 
     * @param Employee employee
     * @throws EmployeeException
     */
    public void createNewTrainer(Employee employee) throws EmployeeException {
        Set<Role> role = new HashSet<>();
        role.add(employeeDao.getEmployeeRole(Constants.TRAINER_ROLE_NAME));
        employee.setRoles(role);
        employeeDao.insertEmployee(employee);
    }

    /**
     * <p>
     * This method will get all employees from employees table through EmployeeDAO.
     * </p>
     *
     * @param int initialResult
     * @return List<Employee> employee
     * @throws EmployeeException 
     */    
    public List<Employee> getAllTrainers(int initialResult) throws EmployeeException {
        return employeeDao.retrieveEmployees(Constants.TRAINER_ROLE_NAME, initialResult);
    }

    /**
     * <p>
     * This method will get a specific employee using ID from employees table through EmployeeDAO.
     * </p>
     * 
     * (e.g): ID = 237, will return specific employee of ID: 237.
     *
     * @param int id
     * @return Employee employee
     * @throws EmployeeException
     */ 
    public Employee searchTrainerById(int id) throws EmployeeException {
        return employeeDao.retrieveEmployeeById(id, Constants.TRAINER_ROLE_NAME);
    }

    /**
     * <p>
     * This method will check for a specific employee using ID in employees table through EmployeeDAO.
     * </p>
     *
     * (e.g): ID = 125, will check for ID: 125 exist or not.
     * 
     * @param int id
     * @return boolean true if employee is present or else otherwise.
     * @throws EmployeeException
     */
    public boolean checkTrainer(int id) throws EmployeeException {
        return employeeDao.isEmployeeExist(id, Constants.TRAINER_ROLE_NAME);
    }

    /**
     * <p>
     * This method will remove a specific employee using ID from employees table through EmployeeDAO.
     * </p>
     *
     * (e.g): ID = 221, Employee with ID: 221 will be removed from the employees table through EmployeeDAO.
     * 
     * @param int id
     * @return boolean true if employee is present or else otherwise.
     * @throws EmployeeException
     */
    public boolean deleteTrainer(int id) throws EmployeeException {
        return employeeDao.deleteEmployeeById(id, Constants.TRAINER_ROLE_NAME);
    }

    /**
     * <p>
     * This method will take input from the user update any employee if exist in employees table through EmployeeDAO.
     * </p>
     *
     * (e.g): ID: 172 (auto-assigned), Name: Sherlock Holmes, Gender (M/F): M,
     * Email ID: holmes@holmes.com, Designation: Employee, Residential Address: 221B Baker Street,
     * Previous Experience: 6, Date Of Joining (dd-MM-yyyy): 12-12-2020,
     * Date Of Birth (dd-MM-yyyy): 10-08-1998, Trainee Count: 10, Training Experience: 3,
     * Preferred Skill for Training: Java, Python, Dart
     *  
     * @param Employee employee
     * @throws EmployeeException
     */
    public void updateTrainer(Employee employee) throws EmployeeException {
        int id = employee.getId();
        Employee employee1 = employeeDao
                .retrieveEmployeeById(id, Constants.TRAINER_ROLE_NAME);
        if (employee1.getId() == id) {
            if (!employee.getName().isEmpty()) {
                employee1.setName(employee.getName());
            }
                            
            if (!employee.getGender().isEmpty()) {
                employee1.setGender(employee.getGender());
            }
                        
            if (!employee.getEmailId().isEmpty()) {
                employee1.setEmailId(employee.getEmailId());
            }
                    
            if (!employee.getResiAddress().isEmpty()) {
                employee1.setResiAddress(employee.getResiAddress());
            }
                  
            if (employee.getPreviousExperience() >= 0) {
                employee1.setPreviousExperience(employee.getPreviousExperience());
            }
                      
            if (employee.getDateOfBirth() != null) {
                employee1.setDateOfBirth(employee.getDateOfBirth());
            }
                    
            if (employee.getDateOfJoining() != null) {
                employee1.setDateOfJoining(employee.getDateOfJoining());
            }

            if (employee.getTraineeCount() > 0) {
                employee1.setTraineeCount(employee.getTraineeCount());
            }

            if (employee.getTrainingExperience() > 0) {
                employee1.setTrainingExperience(employee.getTrainingExperience());
            }

            if (!employee.getPrefTrainingSkill().isEmpty()) {
                employee1.setPrefTrainingSkill(employee.getPrefTrainingSkill());
            }
            employeeDao.updateEmployeeById(employee1, id);
        }
    }
}