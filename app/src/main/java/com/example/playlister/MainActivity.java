package com.example.playlister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView signUpLink;
    Button loginButton;
    EditText loginFormUserName;
    EditText loginFormPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signUpLink = (TextView) findViewById(R.id.loginFormSignUpLink);
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpActivity();
            }
        });
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlelogin();
            }
        });
        loginFormUserName = (EditText) findViewById(R.id.loginFormUserName);
        loginFormPassword = (EditText) findViewById(R.id.loginFormPassword);
    }

    public void openSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void handlelogin() {
        String username = loginFormUserName.getText().toString();
        String password = loginFormPassword.getText().toString();
        String apiResponse = "No response yet.";
        String requestURL = Utils.getString(this, R.string.authURL);
        Context thisContext = this;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                requestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*new AlertDialog.Builder(thisContext)
                                .setTitle("API AUTH TOKEN")
                                .setMessage(response.toString())
                                .show();*/
                        try {
                            JSONObject tokenAsJSON = new JSONObject(response);
                            AuthToken token = new AuthToken(tokenAsJSON.get("token_type").toString(),
                                    tokenAsJSON.get("access_token").toString());
                            // get user data
                            String userDataReqURL = Utils.getString(thisContext, R.string.userInfoURL);
                            StringRequest userDataRequest = new StringRequest(Request.Method.GET,
                                    userDataReqURL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject userDataAsJSON = new JSONObject(response);
                                        UserData.getUserData().setUserData(tokenAsJSON.get("token_type").toString(),
                                                tokenAsJSON.get("access_token").toString(),
                                                userDataAsJSON.get("username").toString(),
                                                userDataAsJSON.get("first_name").toString(),
                                                userDataAsJSON.get("family_name").toString());
                                        Utils.alert(thisContext,
                                                "Hello",
                                                UserData.getUserData().getFirstName() + " " + UserData.getUserData().getLastName());
                                    } catch (JSONException e) {
                                        Utils.alert(thisContext,
                                                "JSON ERROR",
                                                e.getMessage());
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Utils.alert(thisContext,
                                            "AUTH ERROR",
                                            //Integer.toString(error.networkResponse.statusCode)
                                            error.toString());
                                }
                            }) {
                                @Override
                                public  Map<String, String> getHeaders() {
                                    Map<String, String> hdrs = new HashMap<String, String>();
                                    hdrs.put("Content-Type", "application/json; charset=UTF-8");
                                    hdrs.put("Authorization", token.getAuthHeader());
                                    return hdrs;
                                }
                            };
                            NetworkRequestQueue.getInstance(thisContext).addToRequestQueue(userDataRequest);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse.statusCode == 401) {
                    new AlertDialog.Builder(thisContext)
                            .setMessage(Utils.getString(thisContext, R.string.login_form_auth_error))
                            .show();
                } else {
                    new AlertDialog.Builder(thisContext)
                            .setTitle("AUTH ERROR")
                            .setMessage(Integer.toString(error.networkResponse.statusCode))
                            .show();
                }
            }
        }) {
            @Override
            public byte[] getBody() {
                Map<String, String> body = new HashMap<String, String>();
                body.put("username", username);
                body.put("password", password);
                String bodyString = "username=" + username + "&password=" + password;
                return bodyString.getBytes(StandardCharsets.UTF_8);
            }
        };
        NetworkRequestQueue.getInstance(this).addToRequestQueue(stringRequest);
    }
}