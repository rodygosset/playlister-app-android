package com.example.playlister;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import java.util.Locale;
import java.util.Map;

public class GenresListActivity extends AppCompatActivity {

    Context thisContext = this;

    String data;

    UserData userData;

    JSONArray listElements;

    LinearLayout[] UIListElements;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_view_genres);
        AppContext appContext = (AppContext) getApplicationContext();
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
            UIListElements[i] = (LinearLayout) layoutInflater.inflate(R.layout.genres_list_item, null);
            try {
                JSONObject element = listElements.getJSONObject(i);
                ((TextView)UIListElements[i].findViewById(R.id.genresListElementName)).setText(element.getString("name").toUpperCase());

                // make a POST request to the API
                // to get the number of songs associated to this genre

                String postData = "{ genres: [ \"" + element.getString("name") + "\" ] }";
                String searchURL = Utils.getString(this, R.string.songsURL) + "search/nb";

                int finalI = i;
                Utils.postData(this, searchURL, new JSONObject(postData),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    ((TextView)UIListElements[finalI].findViewById(R.id.genresListElementSongs)).setText(response.getString("nb_results") + " " + Utils.getString(thisContext, R.string.genre_list_item_songs_placeholder));
                                    listContainer.addView(UIListElements[finalI], listContainer.getChildCount() - 1);
                                } catch (JSONException e) {
                                    e.printStackTrace();
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
    
    public void getData() {
        Context thisContext = this;
        // get the URL to make the request to 
        String genresDataURL = Utils.getString(this, R.string.genresURL);
        // make a request to the API
        // --> create a Request object
        StringRequest genresDataRequest = new StringRequest(Request.Method.GET,
                genresDataURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*Utils.alert(thisContext,
                        "SUCCESS",
                        //Integer.toString(error.networkResponse.statusCode)
                        response);*/

                // parse response as a JSON Array
                try {
                    listElements = new JSONArray(response);
                    /*Utils.alert(thisContext,
                            "1ST ELEMENT",
                            listElements.get(0).toString());*/
                    buildUIList();
                } catch (JSONException e) {
                    Utils.alert(thisContext,
                            "JSON ERROR",
                            //Integer.toString(error.networkResponse.statusCode)
                            e.toString());
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
        NetworkRequestQueue.getInstance(this).addToRequestQueue(genresDataRequest);
    }

}