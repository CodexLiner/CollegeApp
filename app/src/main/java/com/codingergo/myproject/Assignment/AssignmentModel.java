package com.codingergo.myproject.Assignment;

public class AssignmentModel {
    String branch , Fname ,date , subject , url ,title ,sem ;

    public AssignmentModel() {
    }

    public AssignmentModel(String branch, String fname, String date, String subject, String url, String title, String sem) {
        this.branch = branch;
        Fname = fname;
        this.date = date;
        this.subject = subject;
        this.url = url;
        this.title = title;
        this.sem = sem;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }
}
