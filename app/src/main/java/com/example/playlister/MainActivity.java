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
        JSONObject formData = new JSONObject();
        try {
            formData.put("username", username);
            formData.put("password", password);
            RequestQueue queue = Volley.newRequestQueue(this);
            Context thisContext = this;
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    requestURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            new AlertDialog.Builder(thisContext)
                                    .setTitle("API AUTH TOKEN")
                                    .setMessage(response.toString())
                                    .show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    new AlertDialog.Builder(thisContext)
                            .setTitle("ERROR")
                            .setMessage(error.toString())
                            .show();

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
            queue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}