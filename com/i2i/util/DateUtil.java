package com.i2i.util;

import java.time.LocalDate;
import java.time.Period;


/**
 * <p>
 * DateUtil class will have the utilities for Date datatype.
 * </p>
 * 
 * @author Mokeshwaran
 * @version 1.0 2022-08-10
 */
public class DateUtil {

    /**
     * <p>
     * This method will calculate the age of the user from date of birth given by user.
     * </p>
     * 
     * (e.g): dateOfBirth = 10-08-1998, will calculate the age as 24 (as of 16-08-2022). 
     * 
     * @param String birthDate
     * @return int age calculated from the dateOfBirth.
     */
    public static int calcAge(String birthDate) {
        LocalDate currentDate = LocalDate.now();
        int dayOfBirth = Integer.parseInt(birthDate.substring(8, 10));
        int yearOfBirth = Integer.parseInt(birthDate.substring(0, 4));
        int monthOfBirth = Integer.parseInt(birthDate.substring(5, 7));

        if (monthOfBirth == 0) {
            return 0;
        } else {
            LocalDate birthdayDate = LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth);
            int age = Period.between(birthdayDate, currentDate).getYears();
            return age;
        }
    }

    /**
     * <p>
     * This method will calculate the experience of the user from date of joining given by user.
     * </p>
     * 
     * (e.g): joinDate = 12-12-2020, will calculate the currentExperience as 1 Year and 8 Months (as of 16-08-2022).
     * 
     * @param String joinDate
     * @return String currentExperience calculated from the dateOfJoining.
     */
    public static String calcExperience(String joinDate) {
        LocalDate currentDate = LocalDate.now();
        int dayOfJoining = Integer.parseInt(joinDate.substring(8, 10));
        int yearOfJoining = Integer.parseInt(joinDate.substring(0, 4));
        int monthOfJoining = Integer.parseInt(joinDate.substring(5, 7));

        if (monthOfJoining == 0) {
            return "";
        } else {
            LocalDate joiningDate =
                LocalDate.of(yearOfJoining, monthOfJoining, dayOfJoining);
            int currentExperienceYears =
                Period.between(joiningDate, currentDate).getYears();
            int currentExperienceMonths =
                Period.between(joiningDate, currentDate).getMonths();
            String currentExperience = currentExperienceYears +
                " Years and " + currentExperienceMonths + " Month(s)";
            if (currentExperienceMonths >= 0) {
                return currentExperience;
            } else {
                return "0 Years and 0 Month(s)";
            }
        }
    }

    /**
     * <p>
     * This method will calculate the experience of the user from date of joining given by user.
     * </p>
     * 
     * (e.g): joinDate = 12-12-2020, will calculate the currentExperience as 1 Year and 8 Months (as of 16-08-2022).
     * 
     * @param String date
     * @return String date with dd-MM-yyyy format.
     */
    public static String dateToSimpleDateFormat(String date) {
        int day = Integer.parseInt(date.substring(8, 10));
        int year = Integer.parseInt(date.substring(0, 4));
        String month = null;
        String monthString = date.substring(5, 7);
        switch (monthString) {
            case "Jan":
                month = "01";
                break;
            case "Feb":
                month = "02";
                break;
            case "Mar":
                month = "03";
                break;
            case "Apr":
                month = "04";
                break;
            case "May":
                month = "05";
                break;
            case "Jun":
                month = "06";
                break;
            case "Jul":
                month = "07";
                break;
            case "Aug":
                month = "08";
                break;
            case "Sep":
                month = "09";
                break;
            case "Oct":
                month = "10";
                break;
            case "Nov":
                month = "11";
                break;
            case "Dec":
                month = "12";
                break;
            default:
                break;
        }

        if (month == null) {
            return null;
        } else {
            return day + "-" + month + "-" + year;
        }
    }
}