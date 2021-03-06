package com.codingergo.myproject.FacultyList;

public class FacultyModel {
    String fullname , branch , sem , url , isUser ;

    public String getFullname() {
        return fullname;
    }

    public FacultyModel(String fullname, String branch, String sem, String url, String isUser) {
        this.fullname = fullname;
        this.branch = branch;
        this.sem = sem;
        this.url = url;
        this.isUser = isUser;
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

    public String getIsUser() {
        return isUser;
    }

    public void setIsUser(String isUser) {
        this.isUser = isUser;
    }

    public FacultyModel() {
    }
}
