package com.i2i.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.CollectionTable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


/**
 * <p>
 * Employee class will be a pojo class which stores the data
 * using getters and setters.
 * </p>
 *
 * @author Mokeshwaran
 * @version 1.0 2022-08-10
 */

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "resi_address")
    private String resiAddress;

    @Column(name = "email_id")
    private String emailId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "employee_role", 
        joinColumns = {
            @JoinColumn(name = "id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "role_id")
        }
    )
    private Set<Role> roles = new HashSet<>();

    @Column(name = "date_of_joining")
    private Date dateOfJoining;

    @Column(name = "previous_experience")
    private int previousExperience;

    @Column(name = "trainers_name")
    private String trainersName;

    @Column(name = "batch_id")
    private String batchId;

    @Column(name = "training_course")
    private String trainingCourse;

    @Column(name = "trainee_count")
    private int traineeCount;

    @Column(name = "training_experience")
    private int trainingExperience;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable
    @Column(name = "pref_training_skill")
    private Set<String> prefTrainingSkill;

    public Employee() {}

    /**
     * <p>
     * This is a constructor for employee
     * </p>
     * 
     * @param id
     * @param name
     * @param gender
     * @param emailId
     * @param roles
     * @param resiAddress
     * @param previousExperience
     * @param dateOfBirth
     * @param dateOfJoining
     * @param trainersName
     * @param batchId
     * @param trainingCourse
     * @param traineeCount
     * @param trainingExperience
     * @param prefTrainingSkill
     */
    public Employee(int id, String name, String gender, String emailId,
                    Set<Role> roles, String resiAddress,
                    int previousExperience, Date dateOfBirth, Date dateOfJoining,
                    String trainersName, String batchId, String trainingCourse,
                    int traineeCount, int trainingExperience,
                    Set<String> prefTrainingSkill) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.emailId = emailId;
        this.roles = roles;
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

    public void setName (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDateOfBirth (Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfJoining (Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    public void setResiAddress (String resiAddress) {
        this.resiAddress = resiAddress;
    }

    public String getResiAddress() {
        return resiAddress;
    }

    public void setEmailId (String emailId) {
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setGender (String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setPreviousExperience (int previousExperience) {
        this.previousExperience = previousExperience;
    }
    
    public int getPreviousExperience() {
        return previousExperience;
    }

    public void setRoles (Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setTrainersName (String trainersName) {
        this.trainersName = trainersName;
    }

    public String getTrainersName () {
        return trainersName;
    }

    public void setBatchId (String batchId) {
        this.batchId = batchId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setTrainingCourse (String trainingCourse) {
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
}