package com.example.playlister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            UIListElements[i] = (LinearLayout) layoutInflater.inflate(R.layout.playlists_list_item, null);
            try {
                ((TextView)UIListElements[i].findViewById(R.id.playlistsListElementName)).setText(listElements.getString(i).toUpperCase());

                UIListElements[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(thisContext, PlaylistViewActivity.class);
                        intent.putExtra("jsonData", playlist.toString());
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


}
