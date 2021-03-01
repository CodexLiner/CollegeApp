package com.codingergo.myproject.StudentList;

public class StudentListModel {
    String fullname , branch , sem , url;

    public StudentListModel() {
    }

    public StudentListModel(String fullname, String branch, String sem, String url) {
        this.fullname = fullname;
        this.branch = branch;
        this.sem = sem;
        this.url = url;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
