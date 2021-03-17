package com.codingergo.myproject.MailSender;

import android.util.Log;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class MailFunctions {
    String url = "http://192.168.0.105:3000/";
      public void MailSender(String Email){
          Retrofit retrofit = new Retrofit.Builder()
                  .baseUrl(url)
                  .addConverterFactory(GsonConverterFactory.create())
                  .build();
          MailerApis mailerApis = retrofit.create(MailerApis.class);
           HashMap<String, String> map = new HashMap<>();
           map.put("Email",Email);
           Call<Void> docall = mailerApis.sendMymail(map);
           docall.enqueue(new Callback<Void>() {
               @Override
               public void onResponse(Call<Void> call, Response<Void> response) {
                   Log.d(TAG, "onResponseMailApiFail2: " +response.code());
               }

               @Override
               public void onFailure(Call<Void> call, Throwable t) {
                   Log.d(TAG, "onResponseMailApi2: " +t);
               }
           });


      }

}
