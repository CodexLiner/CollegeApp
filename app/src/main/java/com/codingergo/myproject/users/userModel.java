package com.codingergo.myproject.users;

public class userModel {
    public String name , email, roll , mobile , address , image;
    public userModel()
    {

    }
 public userModel(String name, String email, String roll, String mobile , String address) {
        this.name = name;
        this.email = email;
        this.roll = roll;
        this.mobile = mobile;
        this.address= address;
//        this.image = image;

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

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
}
