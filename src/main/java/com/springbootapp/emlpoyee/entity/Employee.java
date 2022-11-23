package com.springbootapp.emlpoyee.entity;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "employees")
public class Employee {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String department;
    private String gender;
    private String email;
    private String jobTitle;
    private double salary;
    private String password;
    private String roles;

    public List<String> getRolesList() {
        return Arrays.asList(this.roles.split(","));
    }
}
