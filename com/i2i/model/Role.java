package com.i2i.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


/**
 * <p>
 * Role class will be a POJO class which stores and retrieves the data
 * using getters and setters.
 * </p>
 * 
 * @author Mokeshwaran
 * @version 1.0 2022-09-06
 */
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<Employee> employees = new HashSet<>();

    public Role() {}

    /**
     * <p>
     * This is a constructor for role.
     * </p>
     * 
     * @param roleId
     * @param roleName
     * @param employees
     */
    public Role(int roleId, String roleName, Set<Employee> employees) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.employees = employees;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
    
    public Set<Employee> getEmployee() {
        return employees;
    }
}