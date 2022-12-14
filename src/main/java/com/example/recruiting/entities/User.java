package com.example.recruiting.entities;

public class User {
    private int userId;
    private String category;
    private String firstname;
    private String middlename;
    private String lastname;
    private String militaryId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMilitaryId() {
        return militaryId;
    }

    public void setMilitaryId(String militaryId) {
        this.militaryId = militaryId;
    }

    public User(int userId, String category, String firstname, String middlename, String lastname, String militaryId) {
        this.userId = userId;
        this.category = category;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.militaryId = militaryId;
    }
}
