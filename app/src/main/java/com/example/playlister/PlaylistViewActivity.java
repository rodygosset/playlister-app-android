package com.example.playlister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;

public class PlaylistViewActivity extends AppCompatActivity {

    UserData userData;

    Context thisContext = this;

    String data;

    JSONObject playlist;

    LinearLayout[] UIListElements;

    JSONArray listElements;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_view_playlist);
        AppContext appContext = (AppContext) getApplicationContext();
        try {
            playlist = new JSONObject(getIntent().getStringExtra("jsonData"));
            listElements = playlist.getJSONArray("songs");
            TextView playlistTitle = (TextView) findViewById(R.id.viewPlaylistHomeTitle);
            playlistTitle.setText(playlist.getString("name").toUpperCase());
            buildUIList();
        } catch (JSONException e) {
            Utils.alert(this,
            "JSON ERROR",
                    e.toString());
        }
        userData = appContext.getUserData();
        Utils.initNav(this);

        TextView homeLink = (TextView) findViewById(R.id.viewPlaylistHomeLink);
        homeLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openActivity(thisContext, HomeActivity.class);
            }
        });
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
                // get the song as a json object
                String artistName = listElements.getString(i).split(" - ")[0];
                String songTitle = listElements.getString(i).split(" - ")[1];
                String URL = Utils.getString(thisContext, R.string.artistsURL) + artistName + "/" + songTitle;
                int finalI = i;
                Utils.getData(thisContext, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject song = new JSONObject(response);
                            ((TextView)UIListElements[finalI].findViewById(R.id.songsListElementName)).setText(song.getString("title").toUpperCase());
                            JSONArray artists = song.getJSONArray("artists");
                            ((TextView)UIListElements[finalI].findViewById(R.id.songsListElementArtist)).setText(artists.getString(0).toUpperCase());
                            ((TextView)UIListElements[finalI].findViewById(R.id.songsListElementKey)).setText(song.getString("key").toUpperCase());
                            ((TextView)UIListElements[finalI].findViewById(R.id.songsListElementBPM)).setText(song.getString("bpm").toUpperCase());
                            int songDuration = song.getInt("duration");
                            int minutes = Math.floorDiv(songDuration, 60);
                            int seconds = Math.floorMod(songDuration, 60);
                            String minuteString = minutes == 1 ? Utils.getString(thisContext, R.string.minute_singular) : Utils.getString(thisContext, R.string.minute_plural);
                            String secondString = seconds == 1 ? Utils.getString(thisContext, R.string.second_plural) : Utils.getString(thisContext, R.string.second_plural);
                            String durationString = minutes + " " + minuteString +  ", " + seconds + " " + secondString;
                            ((TextView)UIListElements[finalI].findViewById(R.id.songsListElementDuration)).setText(durationString);
                            listContainer.addView(UIListElements[finalI], listContainer.getChildCount() - 1);
                        } catch (JSONException e) {
                            Utils.alert(thisContext,
                                    "JSON ERROR",
                                    //Integer.toString(error.networkResponse.statusCode)
                                    e.toString());
                        }
                    }
                });


            } catch (JSONException e) {
                Utils.alert(this,
                        "JSON ERROR",
                        //Integer.toString(error.networkResponse.statusCode)
                        e.toString());
            }
        }
    }


}
