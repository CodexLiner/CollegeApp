package com.codingergo.myproject.facultyList;

public class FacultyModel {
    String name , branch , photo;

    public FacultyModel() {
    }

    public FacultyModel(String name, String branch, String photo) {
        this.name = name;
        this.branch = branch;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
