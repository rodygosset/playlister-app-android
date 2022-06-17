package com.example.playlister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MusicLibraryActivity extends AppCompatActivity {


    AppContext appContext;
    UserData userData;

    Context thisContext = this;

    String data;

    LinearLayout[] UIListElements;

    JSONArray listElements;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_music_library);
        appContext = (AppContext) getApplicationContext();
        userData = appContext.getUserData();
        Utils.initNav(this);
        getData();
    }

    public void buildUIList() {
        LinearLayout listContainer = (LinearLayout) findViewById(R.id.mainListContainer);
        this.UIListElements = new LinearLayout[listElements.length()];
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // go through the array of JSON objects
        for(int i = 0; i < listElements.length(); i++) {
            // create a view for each element in the array
            UIListElements[i] = (LinearLayout) layoutInflater.inflate(R.layout.songs_list_item, null);
            try {
                JSONObject element = listElements.getJSONObject(i);
                ((TextView)UIListElements[i].findViewById(R.id.songsListElementName)).setText(element.getString("title").toUpperCase());
                JSONArray artists = element.getJSONArray("artists");
                ((TextView)UIListElements[i].findViewById(R.id.songsListElementArtist)).setText(artists.getString(0).toUpperCase());
                ((TextView)UIListElements[i].findViewById(R.id.songsListElementKey)).setText(element.getString("key").toUpperCase());
                ((TextView)UIListElements[i].findViewById(R.id.songsListElementBPM)).setText(element.getString("bpm").toUpperCase());
                int songDuration = element.getInt("duration");
                int minutes = Math.floorDiv(songDuration, 60);
                int seconds = Math.floorMod(songDuration, 60);
                String minuteString = minutes == 1 ? Utils.getString(thisContext, R.string.minute_singular) : Utils.getString(thisContext, R.string.minute_plural);
                String secondString = seconds == 1 ? Utils.getString(thisContext, R.string.second_plural) : Utils.getString(thisContext, R.string.second_plural);
                String durationString = minutes + " " + minuteString +  ", " + seconds + " " + secondString;
                ((TextView)UIListElements[i].findViewById(R.id.songsListElementDuration)).setText(durationString);
                listContainer.addView(UIListElements[i], listContainer.getChildCount() - 1);
            } catch (JSONException e) {
                Utils.alert(this,
                        "JSON ERROR",
                        //Integer.toString(error.networkResponse.statusCode)
                        e.toString());
            }
        }
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
                /*Utils.alert(thisContext,
                        "SUCCESS",
                        //Integer.toString(error.networkResponse.statusCode)
                        response);*/
                try {
                    listElements = new JSONArray(response);
                    buildUIList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
