package com.nja7.chatapp;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendNotification {

    private final String postUrl = "https://fcm.googleapis.com/v1/projects/chatapplication-114d6/messages:send";
    private final String userFcmToken;
    private final String title;
    private final String body;
    private final Context context;
    private final String userId;

    public SendNotification(String userFcmToken, String title, String body, Context context, String userId) {
        this.userFcmToken = userFcmToken;
        this.title = title;
        this.body = body;
        this.context = context;
        this.userId = userId;
    }

    public void sendNotification() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject mainObj = new JSONObject();
        try {
            JSONObject messageObj = new JSONObject();
            JSONObject notificationObj = new JSONObject();
            JSONObject dataObj = new JSONObject();

            // Notification content
            notificationObj.put("title", title);
            notificationObj.put("body", body);

            // Custom data
            dataObj.put("userId", userId);

            messageObj.put("token", userFcmToken);
            messageObj.put("notification", notificationObj);
            messageObj.put("data", dataObj);

            mainObj.put("message", messageObj);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, postUrl, mainObj, response -> {
                // Handle the response
                Toast.makeText(context, "Notification sent successfully", Toast.LENGTH_SHORT).show();
            }, volleyError -> {
                // Handle the error
                volleyError.printStackTrace();
                Toast.makeText(context, "Error: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }) {
                @NonNull
                @Override
                public Map<String, String> getHeaders() {
                    AccessToken accessToken = new AccessToken();
                    String accesskey = accessToken.getAccessToken();
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + accesskey);
                    return headers;
                }
            };
            requestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
