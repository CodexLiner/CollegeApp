package com.codingergo.myproject.PhotoGallery;

public class imageModel {
    String name , Url , About , time ;

    public imageModel(String name, String url, String about, String time) {
        this.name = name;
        Url = url;
        About = about;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public imageModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public imageModel(String time) {
        this.time = time;
    }
}
