package com.bunny.healthkitchengymtrainer.Model;

public class GymTrainer {

    private String emailID;
    private String password;
    private String phoneNo;
    private String name;

    public GymTrainer(){

    }

    public GymTrainer(String emailID, String password, String phoneNo, String name) {
        this.emailID = emailID;
        this.password = password;
        this.phoneNo = phoneNo;
        this.name = name;
    }

    public String getEmailID() {
        return emailID;

    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GymTrainer{" +
                "emailID='" + emailID + '\'' +
                ", password='" + password + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
