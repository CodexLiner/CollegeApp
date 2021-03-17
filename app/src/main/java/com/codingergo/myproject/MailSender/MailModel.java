package com.codingergo.myproject.MailSender;

public class MailModel {
    String Email ;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public MailModel() {
    }

    public MailModel(String email) {
        Email = email;
    }
}
