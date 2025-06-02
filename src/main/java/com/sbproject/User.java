package com.sbproject;

public class User {
    private String userName;
    private String passWord;
    // private Date lastloginDate;

    public User() {

    }
    // All Setters and Getters

    public String getuserName() {
        return this.userName;
    }

    public void setuserName(String userName) {
        this.userName=userName;
    }

    public String getpassWord() {
        return this.passWord;
    }

    public void setpassWord(String password) {
        this.passWord=password;
    }

}
