package com.example.playlister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    Context thisContext = this;

    TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_signup);
        loginLink = (TextView) findViewById(R.id.signUpFormLoginLink);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openActivity(thisContext, MainActivity.class);
            }
        });
    }

}
