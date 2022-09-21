package com.i2i.dto;

import java.util.Date;
import java.util.Set;


/**
 * <p>
 * EmployeeDto is a Data Transfer Object used to store and retrieve data to the user.
 * </p>
 * 
 * @author Mokeshwaran
 * @version 1.0 2022-08-31
 */
public class EmployeeDto {
    private int id;
    private Date dateOfBirth;
    private int age;
    private int previousExperience;
    private String name;
    private Date dateOfJoining;
    private String currentExperience;
    private String resiAddress;
    private String emailId;
    private String gender;
    private String trainersName;
    private String batchId;
    private String trainingCourse;
    private int traineeCount;
    private int trainingExperience;
    private Set<String> prefTrainingSkill;

    public EmployeeDto() {}

    public EmployeeDto(int id, String name, String gender, String emailId,
                       String resiAddress, int previousExperience,
                       Date dateOfBirth, Date dateOfJoining,
                       String trainersName, String batchId,
                       String trainingCourse, int traineeCount,
                       int trainingExperience, Set<String> prefTrainingSkill) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.emailId = emailId;
        this.resiAddress = resiAddress;
        this.previousExperience = previousExperience;
        this.dateOfBirth = dateOfBirth;
        this.dateOfJoining = dateOfJoining;
        this.trainersName = trainersName;
        this.batchId = batchId;
        this.trainingCourse = trainingCourse;
        this.traineeCount = traineeCount;
        this.trainingExperience = trainingExperience;
        this.prefTrainingSkill = prefTrainingSkill;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setAge(int age) {
        this.age = age;
    } 
    
    public int getAge() {
        return age;
    }

    public void setDateOfJoining(Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    public void setCurrentExperience(String currentExperience) {
        this.currentExperience = currentExperience;
    }

    public String getCurrentExperience() {
        return currentExperience;
    }

    public void setResiAddress(String resiAddress) {
        this.resiAddress = resiAddress;
    }

    public String getResiAddress() {
        return resiAddress;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setPreviousExperience(int previousExperience) {
        this.previousExperience = previousExperience;
    }

    public int getPreviousExperience() {
        return previousExperience;
    }

    public void setTrainersName(String trainersName) {
        this.trainersName = trainersName;
    }

    public String getTrainersName() {
        return trainersName;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setTrainingCourse(String trainingCourse) {
        this.trainingCourse = trainingCourse;
    }

    public String getTrainingCourse() {
        return trainingCourse;
    }

    public void setTraineeCount (int traineeCount) {
        this.traineeCount = traineeCount;
    }

    public int getTraineeCount() {
        return traineeCount;
    }

    public void setTrainingExperience (int trainingExperience) {
        this.trainingExperience = trainingExperience;
    }

    public int getTrainingExperience() {
        return trainingExperience;
    }

    public void setPrefTrainingSkill (Set<String> prefTrainingSkill) {
        this.prefTrainingSkill = prefTrainingSkill;
    }

    public Set<String> getPrefTrainingSkill() {
        return prefTrainingSkill;
    }

    public String toString() {
        return "ID: " + id + "\nName: " + name + "\nGender: " + gender +
        "\nResidential Address: " + resiAddress + "\nEmailID: " + emailId +
        "\nAge: " + getAge() + "\nDate Of Joining: " + dateOfJoining +
        "\nCurrent Experience: " + getCurrentExperience() +
        "\nPrevious Experience: " + previousExperience +
        "\nTrainer's Name: " + trainersName + "\nBatch ID: " + batchId +
        "\nTraining Course: " + trainingCourse +
        "\nTrainee Count: " + traineeCount + "\nTraining Experience: " +
        trainingExperience + "\nPreferred Skill For Training: " +
        prefTrainingSkill.toString().replace("[", "").replace("]", "") + "\n";
    }
}