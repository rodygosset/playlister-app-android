package com.example.playlister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MusicLibraryActivity extends AppCompatActivity {

    String data;

    AppContext appContext;
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_music_library);
        appContext = (AppContext) getApplicationContext();
        userData = appContext.getUserData();
        Utils.initNav(this);
        getData();
    }

    public void getData() {
        Context thisContext = this;
        // get the URL to make the request to
        String songsDataURL = Utils.getString(this, R.string.songsURL);
        // make a request to the API
        // --> create a Request object
        StringRequest songsDataRequest = new StringRequest(Request.Method.GET,
                songsDataURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //
                Utils.alert(thisContext,
                        "SUCCESS",
                        //Integer.toString(error.networkResponse.statusCode)
                        response);
            }
        }, new Response.ErrorListener() {
            // in case there's an error
            // let the user know
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.alert(thisContext,
                        "AUTH ERROR",
                        //Integer.toString(error.networkResponse.statusCode)
                        error.toString());
            }
        }) {
            // this is used to authenticate the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> hdrs = new HashMap<String, String>();
                hdrs.put("Content-Type", "application/json; charset=UTF-8");
                hdrs.put("Authorization", userData.getAuthHeader());
                return hdrs;
            }
        };
        NetworkRequestQueue.getInstance(this).addToRequestQueue(songsDataRequest);
    }

}