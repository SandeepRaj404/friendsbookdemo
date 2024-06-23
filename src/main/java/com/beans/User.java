package com.beans;


import java.time.LocalDate;

public class User {
    private int userId;
    private String userName;
    private String gender;
    private LocalDate dob;
    private String email;
    private String password;
    private String dp;
    public User(){}
    public User(int userId, String userName, String gender, LocalDate dob, String email, String password, String dp) {
        this.userId = userId;
        this.userName = userName;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.password = password;
        this.dp = dp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getDp() {
        return dp;
    }
    public void setDp(String dp) {
        this.dp = dp;
    }
}
