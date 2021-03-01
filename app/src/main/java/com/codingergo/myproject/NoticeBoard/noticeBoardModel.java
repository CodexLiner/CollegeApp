package com.codingergo.myproject.NoticeBoard;

public class noticeBoardModel {
    String name ,Date, Url;

    public noticeBoardModel(String url, String name ,String Date) {
        Url = url;
        this.name = name;
        this.Date =Date;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
