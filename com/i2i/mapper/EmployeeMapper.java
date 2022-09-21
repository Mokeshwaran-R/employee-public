package com.i2i.mapper;

import java.util.ArrayList;
import java.util.List;

import com.i2i.dto.EmployeeDto;
import com.i2i.model.Employee;
import com.i2i.util.DateUtil;


/**
 * <p>
 * EmployeeMapper will convert entity to dto and vice versa.
 * </p>
 * 
 * @author Mokeshwaran
 * @version 1.0 2022-08-31
 */
public class EmployeeMapper {
    /**
     * <p>
     * This method will map from DTO to entity.
     * </p>
     * 
     * @param EmployeeDto employeeDto
     * @return Employee employee
     */
    public static Employee dtoToEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setGender(employeeDto.getGender());
        employee.setEmailId(employeeDto.getEmailId());
        employee.setResiAddress(employeeDto.getResiAddress());
        employee.setPreviousExperience(employeeDto.getPreviousExperience());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setDateOfJoining(employeeDto.getDateOfJoining());
        employee.setTrainersName(employeeDto.getTrainersName());
        employee.setBatchId(employeeDto.getBatchId());
        employee.setTrainingCourse(employeeDto.getTrainingCourse());
        employee.setTraineeCount(employeeDto.getTraineeCount());
        employee.setTrainingExperience(employeeDto.getTrainingExperience());
        employee.setPrefTrainingSkill(employeeDto.getPrefTrainingSkill());
        return employee;
    }

    /**
     * <p>
     * This method will map from entity to DTO.
     * </p>
     * 
     * @param Employee employee
     * @return EmployeeDto employeeDto
     */
    public static EmployeeDto entityToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setGender(employee.getGender());
        employeeDto.setEmailId(employee.getEmailId());
        employeeDto.setResiAddress(employee.getResiAddress());
        employeeDto.setPreviousExperience(employee.getPreviousExperience());
        employeeDto.setDateOfBirth(employee.getDateOfBirth());
        employeeDto.setAge(DateUtil.calcAge(employee.getDateOfBirth().toString()));
        employeeDto.setDateOfJoining(employee.getDateOfJoining());
        employeeDto.setCurrentExperience(DateUtil.calcExperience(employee.getDateOfJoining().toString()));
        employeeDto.setTrainersName(employee.getTrainersName());
        employeeDto.setBatchId(employee.getBatchId());
        employeeDto.setTrainingCourse(employee.getTrainingCourse());
        employeeDto.setTraineeCount(employee.getTraineeCount());
        employeeDto.setTrainingExperience(employee.getTrainingExperience());
        employeeDto.setPrefTrainingSkill(employee.getPrefTrainingSkill());
        return employeeDto;
    }

    /**
     * <p>
     * This method will convert entity list to DTO list
     * </p>
     * 
     * @param List<Employee> employees
     * @return List<EmployeeDto> employeesDto
     */
    public static List<EmployeeDto> entityToDtoList(List<Employee> employees) {
        List<EmployeeDto> employeesDto = new ArrayList<EmployeeDto>();
        for (Employee employee : employees) {
            employeesDto.add(entityToDto(employee));
        }
        return employeesDto; 
    }
}