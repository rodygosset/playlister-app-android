package com.example.playlister;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static void alert(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .show();
    }


    // open an activity

    public static void openActivity(Context ctx, Class Activity) {
        Intent intent = new Intent(ctx, Activity);
        ctx.startActivity(intent);
    }

    // Init the nav

    public static void initNav(Context ctx) {
        View homeButton = ((Activity) ctx).findViewById(R.id.navHomeButton);
        View musicLibraryButton = ((Activity) ctx).findViewById(R.id.navMusicLibraryButton);
        View genresListButton = ((Activity) ctx).findViewById(R.id.navGenresButton);
        View artistsList = ((Activity) ctx).findViewById(R.id.navArtistButton);
        View tagsListActivity = ((Activity) ctx).findViewById(R.id.navTagsButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openActivity(ctx, HomeActivity.class);
            }
        });

        musicLibraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openActivity(ctx, MusicLibraryActivity.class);
            }
        });

        genresListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openActivity(ctx, GenresListActivity.class);
            }
        });

        artistsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openActivity(ctx, ArtistsListActivity.class);
            }
        });

        tagsListActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openActivity(ctx, TagsListActivity.class);
            }
        });
    }

    // Make a GET request

    // this function takes the context, the URL
    // and a reference to a RequestResponse object
    // that will be modified when the Network request is complete

    public static void getData(Context ctx, String URL, Response.Listener<String> onResponse) {
        AppContext appContext = (AppContext) ctx.getApplicationContext();
        UserData userData = appContext.getUserData();
        // make a request to the API
        // --> create a Request object
        StringRequest dataRequest = new StringRequest(Request.Method.GET,
                URL, onResponse, new Response.ErrorListener() {
            // in case there's an error
            // let the user know
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.alert(ctx,
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
        NetworkRequestQueue.getInstance(ctx).addToRequestQueue(dataRequest);
    }


    // Make a POST request

    // this function takes the context, the URL
    // the postData in as a JSON String
    // and an onResponse listener

    public static void postData(Context ctx, String URL, JSONObject postData, Response.Listener onResponse) {
        AppContext appContext = (AppContext) ctx.getApplicationContext();
        UserData userData = appContext.getUserData();
        // make a request to the API
        // --> create a Request object
        JsonObjectRequest dataRequest = new JsonObjectRequest(Request.Method.POST,
                URL, postData, onResponse, new Response.ErrorListener() {
            // in case there's an error
            // let the user know
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.alert(ctx,
                        "AUTH ERROR",
                        //Integer.toString(error.networkResponse.statusCode)
                        error.toString());
            }
        }) {
            // this is used to authenticate the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> hdrs = new HashMap<String, String>();
                hdrs.put("Content-Type", "application/json");
                hdrs.put("Authorization", userData.getAuthHeader());
                return hdrs;
            }
        };
        NetworkRequestQueue.getInstance(ctx).addToRequestQueue(dataRequest);
    }

}
