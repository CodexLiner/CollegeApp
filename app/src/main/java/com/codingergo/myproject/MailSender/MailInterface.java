package com.codingergo.myproject.MailSender;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MailInterface {
    @FormUrlEncoded
    @POST("LoginSuccess")
    Call<MailModel> RequestEmail(@Field("Email") String email );
    @POST("loginsuccess")
    Call<Void> sendMymail (@Body HashMap<String, String> map);
}
