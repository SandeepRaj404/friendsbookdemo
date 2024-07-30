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
    private UserPost userPost;
    private LocalDate start_date;
    private Comments comments;
    private Likes likes;

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

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
    public void populateBasicUserInfo(int userId, String userName, String gender, LocalDate dob, String email, String dp) {
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
    public UserPost getUserPost() {
        return userPost;
    }
    public void setUserPost(UserPost userPost) {
        this.userPost = userPost;
    }

}
