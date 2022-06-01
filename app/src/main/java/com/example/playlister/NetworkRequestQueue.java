package com.example.playlister;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NetworkRequestQueue {

    private static NetworkRequestQueue instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private NetworkRequestQueue(Context context) {
        this.ctx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized NetworkRequestQueue getInstance(Context context) {
        if(instance == null) {
            instance = new NetworkRequestQueue(context);
        }
        return instance;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
