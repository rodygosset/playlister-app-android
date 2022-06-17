package com.example.playlister;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

        TextView logoutButton;

        UserData userData;

        Context thisContext = this;

        String data;

        JSONArray listElements;

        LinearLayout[] UIListElements;

        @Override
        protected void onCreate(Bundle savedInstance) {
            super.onCreate(savedInstance);
            setContentView(R.layout.activity_home);
            AppContext appContext = (AppContext) getApplicationContext();
            userData = appContext.getUserData();
            Utils.initNav(this);
            logoutButton = (TextView) findViewById(R.id.homeLogoutButton);
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userData.emptyUserData();
                    openLoginActivity();
                }
            });

            getData();
        }

    public void openLoginActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void buildUIList() {
        LinearLayout listContainer = (LinearLayout) findViewById(R.id.mainListContainer);
        this.UIListElements = new LinearLayout[listElements.length()];
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // go through the array of JSON objects
        for(int i = 0; i < listElements.length(); i++) {
            // create a view for each element in the array
            UIListElements[i] = (LinearLayout) layoutInflater.inflate(R.layout.playlists_list_item, null);
            try {
                JSONObject element = listElements.getJSONObject(i);
                ((TextView)UIListElements[i].findViewById(R.id.playlistsListElementName)).setText(element.getString("name").toUpperCase());

                int nbSongs = element.getJSONArray("songs").length();

                ((TextView)UIListElements[i].findViewById(R.id.playlistsListElementSongs)).setText(nbSongs + " " + Utils.getString(thisContext, R.string.playlist_list_item_songs_placeholder));

                UIListElements[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(thisContext, PlaylistViewActivity.class);
                        intent.putExtra("jsonData", element.toString());
                        startActivity(intent);
                    }
                });

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
        // get the URL to make the request to
        String playlistsDataURL = Utils.getString(this, R.string.playlistsURL);
        // make a request to the API
        // --> create a Request object
        Utils.getData(this,playlistsDataURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // parse response as a JSON Array
                try {
                    listElements = new JSONArray(response);
                    /*Utils.alert(thisContext,
                            "1ST ELEMENT",
                            response);*/
                    buildUIList();
                } catch (JSONException e) {
                    Utils.alert(thisContext,
                            "JSON ERROR",
                            //Integer.toString(error.networkResponse.statusCode)
                            e.toString());
                }
            }
        });
    }


}
