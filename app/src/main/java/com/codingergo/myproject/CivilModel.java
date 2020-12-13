package com.codingergo.myproject;

public class CivilModel {

    String name, email, address , mobile , roll;
    CivilModel(){

    }

    public CivilModel(String name, String email, String address, String mobile, String roll) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.mobile = mobile;
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }
}
