package com.codingergo.myproject.NotesManager;

public class NotesModel {
    String subject , faculty , desc , date , url , branch , sem;

    public NotesModel(String subject, String faculty, String desc, String date, String url, String branch, String sem) {
        this.subject = subject;
        this.faculty = faculty;
        this.desc = desc;
        this.date = date;
        this.url = url;
        this.branch = branch;
        this.sem = sem;
    }

    public NotesModel() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
