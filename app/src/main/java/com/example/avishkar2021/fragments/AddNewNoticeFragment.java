package com.example.avishkar2021.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.avishkar2021.databinding.FragmentAddNewNoticeBinding;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddNewNoticeFragment extends Fragment {

    FragmentAddNewNoticeBinding binding;
    FirebaseDatabase database;
    private RequestQueue requestQueue;
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAzVgJ_g8:APA91bGfYuEisV068QeFOH1BOurr5lodhrjG5QQzl6B4mwk1SRu2QYGcL98L6TZ0jKl2o_3XwrS84U8Csih-MDdH826qFuAeFXlzZ65CQtXUKshb14SF9xth8eTLOWIpQIm_giJ5idL_";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddNewNoticeBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();
        requestQueue = Volley.newRequestQueue(getContext());
        FirebaseMessaging.getInstance().subscribeToTopic("notice");
        binding.publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    if(binding.noticeSubject.getText()==null)
                    {
                        binding.noticeSubject.setText("");
                    }
                    if(binding.noticeDesc.getText()==null)
                    {
                        binding.noticeDesc.setText("");
                    }
                    String uid = Calendar.getInstance().getTime().toString();
                    database.getReference().child("notice").
                            child(uid).
                            child("subject")
                            .setValue(binding.noticeSubject.getText().toString());
                    database.getReference().child("notice").
                            child(Calendar.getInstance().getTime().toString()).
                            child("description")
                            .setValue(binding.noticeDesc.getText().toString());
                    String date = DateFormat.getDateTimeInstance().format(new Date());
                    database.getReference().child("notice").
                            child(Calendar.getInstance().getTime().toString()).
                            child("publish_date")
                            .setValue(date);
                    TOPIC = "/topics/notice"; //topic must match with what the receiver subscribed to
                    NOTIFICATION_TITLE = "Notice";
                    NOTIFICATION_MESSAGE = binding.noticeSubject.getText().toString();

                    JSONObject notification = new JSONObject();
                    JSONObject notificationBody = new JSONObject();
                    try {
                        notificationBody.put("title", NOTIFICATION_TITLE);
                        notificationBody.put("message", NOTIFICATION_MESSAGE);
                        notification.put("to", TOPIC);
                        notification.put("data", notificationBody);
                    } catch (JSONException e) {
                        Log.e(TAG, "onCreate: " + e.getMessage() );
                    }
                    sendNotification(notification);
                    Toast.makeText(getContext(), "Published", Toast.LENGTH_SHORT).show();
                }catch (Exception e)
                {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.clearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.noticeDesc.setText(null);
                binding.noticeSubject.setText(null);
            }
        });
        return binding.getRoot();

    }

    private void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                FCM_API, notification,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Log.i(TAG, "onResponse: " + response.toString());
//                        edtTitle.setText("");
//                        edtMessage.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String ,String> getHeaders() throws AuthFailureError {
                Map<String ,String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}