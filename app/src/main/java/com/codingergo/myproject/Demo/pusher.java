package com.codingergo.myproject.Demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codingergo.myproject.R;
import com.onesignal.OSDeviceState;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

public class pusher extends AppCompatActivity {
    String externalUserId = "ComputerScience";
    private static final String ONESIGNAL_APP_ID = "61bf7c23-e222-4d1c-8297-7532813a8619";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pusher);
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        OSDeviceState device = OneSignal.getDeviceState();
        String email = device.getEmailAddress();
        String emailId = device.getEmailUserId();
        String pushToken = device.getPushToken();
        String userId = device.getUserId();
      //  exrternalid();
        Log.d("TAG", "onCreate: "+email);

    }


    private void exrternalid() {

       // You will supply the external user id to the OneSignal SDK

// Setting External User Id with Callback Available in SDK Version 4.0.0+
        OneSignal.setExternalUserId(externalUserId, new OneSignal.OSExternalUserIdUpdateCompletionHandler() {
            @Override
            public void onSuccess(JSONObject results) {
                // The results will contain push and email success statuses
                OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id done with results: " + results.toString());
                try {
                    if (results.has("push") && results.getJSONObject("push").has("success")) {
                        boolean isPushSuccess = results.getJSONObject("push").getBoolean("success");
                        OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id for push status: " + isPushSuccess);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (results.has("email") && results.getJSONObject("email").has("success")) {
                        boolean isEmailSuccess = results.getJSONObject("email").getBoolean("success");
                        OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Sets external user id for email status: " + isEmailSuccess);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(OneSignal.ExternalIdError error) {
                // The results will contain push and email failure statuses
                // Use this to detect if external_user_id was not set and retry when a better network connection is made
                OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id done with error: " + error.toString());
            }
        });

//------------------------------------------------------------------------

// Setting External User Id with Callback Available in SDK Version 3.13.0+
        OneSignal.setExternalUserId(externalUserId, new OneSignal.OSExternalUserIdUpdateCompletionHandler() {
            @Override
            public void onSuccess(JSONObject results) {
                OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id done with results: " + results.toString());

                // Push can be expected in almost every situation with a success status, but
                // as a pre-caution its good to verify it exists
                try {
                    if (results.has("push") && results.getJSONObject("push").has("success")) {
                        boolean isPushSuccess = results.getJSONObject("push").getBoolean("success");
                        OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id for push status: " + isPushSuccess);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Verify the email is set or check that the results have an email success status
                try {
                    if (results.has("email") && results.getJSONObject("email").has("success")) {
                        boolean isEmailSuccess = results.getJSONObject("email").getBoolean("success");
                        OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Sets external user id for email status: " + isEmailSuccess);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OneSignal.ExternalIdError error) {

            }
        });
    }
}