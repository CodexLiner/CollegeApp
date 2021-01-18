package com.codingergo.myproject.noticeBoard;

public class noticeModel {
    String Name ;
    String url;
    String Date;

    public noticeModel(String name, String url, String date) {
        Name = name;
        this.url = url;
        Date = date;
    }

    noticeModel()
    {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public noticeModel(String name) {
        Name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
