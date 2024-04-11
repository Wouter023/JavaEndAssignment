package com.example.javaendassignment.Models;

import java.io.Serializable;

public class User implements Serializable {
    private final String username;
    private final String password;
    private final String fullName;
    private final Job jobRole;

    public User(String username, String password, String fullName, Job jobRole) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.jobRole = jobRole;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public Job getJobRole() { return jobRole; }
}
