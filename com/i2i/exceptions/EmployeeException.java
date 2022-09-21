package com.i2i.exceptions;

import java.lang.Exception;


/**
 * <p>
 * EmployeeSQLException is a custom exception will be used in this project.
 * </p>
 * 
 * @author Mokeshwaran
 * @version 1.0 2022-09-10
 */
public class EmployeeException extends Exception {
    public EmployeeException(String message) {
        super(message);
    }
}