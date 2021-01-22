package com.codingergo.myproject.photoGallery;

public class imageModel {
    String name , Url , About ;

    public imageModel(String name, String url, String about) {
        this.name = name;
        Url = url;
        About = about;
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

}
