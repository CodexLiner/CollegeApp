package com.codingergo.myproject;

public class noticeBoardModel {
    String name , Url;

    public noticeBoardModel(String url, String name) {
        Url = url;
        this.name = name;
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
}
