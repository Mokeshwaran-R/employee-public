package com.i2i.util;

import java.util.Date;
import java.util.regex.Pattern;


/**
 * <p>
 * ValidationUtil class will have validation for every operations inside cmd.
 * </p>
 * 
 * @author Mokeshwaran
 * @version 1.0 2022-08-10
 */
public class ValidationUtil {

    /**
     * <p>
     * This method will check the someString for numerics and special characters.
     * </p>
     * 
     * (e.g): someString = Sherlock Holmes (valid input), returns: true.
     * (e.g): someString = Sherlock221 (invalid input), returns: false.
     *
     * @param String someString
     *     String given by user in EmployeeController.
     * @return boolean true if someString doesn't contain any numerics or special characters,
     *     false if not.
     */
    public static boolean isValidString(String someString) {
        if (someString.isBlank()) {
            return false;
        }
        return Pattern.matches("[a-zA-Z]+[ ]?[a-zA-Z]*[ ]?[a-zA-Z]*", someString);
    }

    /**
     * <p>
     * This method will check the someListString for comma and spaces for
     * adding to list.
     * </p>
     * 
     * (e.g): someListString = Sherlock,Holmes (valid input), returns: true.
     * (e.g): someString = Sherlock , Holmes (invalid input), returns: false.
     *
     * @param String someListString
     *     String given by user in EmployeeController.
     * @return boolean true if someListString contains any comma and string,
     *     false if not.
     */
    public static boolean isValidListString(String someListString) {
        return Pattern.matches("[a-zA-Z?:,a-zA-Z]*", someListString);
    }

    /**
     * <p>
     * This method will check the emailId someString for valid email format.
     * </p>
     *
     * (e.g): EmailId = holmes@holmes.com (valid input), returns: true.
     * (e.g): EmailId = 221holmes@@holmes.com (invalid input), returns: false.
     *
     * @param emailId
     *     String given by user in EmployeeController.
     * @return boolean true if emailId is in valid format, false if not.
     */
    public static boolean isValidEmail(String emailId) {
        return Pattern.matches("^[a-zA-Z\\.]+@[a-zA-Z0-9]+\\.[com|org]{2,5}$", emailId);
    }

    /**
     * <p>
     * This method will check the numeric someString for valid number format.
     * </p>
     *
     * (e.g): Number = 123 (valid input), returns: true.
     * (e.g): Number = !2m (invalid input), returns: false.
     *
     * @param String number
     *     String given by user in EmployeeController.
     * @return boolean true if number is in valid format, false if not.
     */
    public static boolean isValidNumber(String number) {
        return Pattern.matches("\\d+", number);
    }

    /**
     * <p>
     * This method will check the yesOrNo String for valid format.
     * </p>
     *
     * (e.g): YesOrNo = Y (or y, N or n) (valid input), returns: true.
     * (e.g): YesOrNo = yes (invalid input), returns: false.
     *
     * @param String yesOrNo
     *     String given by user in EmployeeController.
     * @return boolean true if yesOrNo is in valid format, false otherwise.
     */
    public static boolean isValidYesOrNo(String yesOrNo) {
        return Pattern.matches("^(?:y|n)$", yesOrNo);
    }

    public static boolean isValidDate(String someDate) {
        Date date = new Date();
        int day = 0;
        int month = 0;
        int year = 0;
        char hyphen1 = ' ';
        char hyphen2 = ' ';
             
        if (someDate.length() == 10) {
            hyphen1 = someDate.charAt(2);
            hyphen2 = someDate.charAt(5);
            day = Integer.parseInt(someDate.substring(0,2));
            month = Integer.parseInt(someDate.substring(3,5));
            year = Integer.parseInt(someDate.substring(6,10));

            if (hyphen1 == '-' && hyphen2 == '-') {
                if (year > date.getYear() + 2 && year < 1900) {
                    return false;
                } else if (month < 1 || month > 12) {
                    return false;
                } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                    if (day < 1 || day > 30) {
                        return false;
                    } else {
                        return true;
                    }
                } else if (month == 1 || month == 3 || month == 5 || month == 7
                           || month == 8 || month == 10 || month == 12) {
                    if (day < 1 || day > 31) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    if (year % 400 == 0 && month == 2 && day > 1 && day < 30) {
                        return true;
                    } else if (year % 100 == 0 && month == 2 && day > 1 && day < 30) {
                        return false;
                    } else if (year % 4 == 0 && month == 2 && day > 1 && day < 30) {
                        return true;
                    }
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;      
    }

    public static boolean isValidResiAddress(String resiAddress) {
        return resiAddress.length() < 10
            || resiAddress.length() > 200 ? true : false;
    }
}












