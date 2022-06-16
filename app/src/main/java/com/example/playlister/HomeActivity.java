package com.example.playlister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

        TextView message;
        TextView logoutButton;

        UserData userData;

        @Override
        protected void onCreate(Bundle savedInstance) {
            super.onCreate(savedInstance);
            setContentView(R.layout.activity_home);
            AppContext appContext = (AppContext) getApplicationContext();
            userData = appContext.getUserData();
            Utils.initNav(this);
            message = (TextView) findViewById(R.id.homeMessage);
            message.setText("Hello, " + userData.getFirstName() + " " + userData.getLastName() + "!");
            logoutButton = (TextView) findViewById(R.id.homeLogoutButton);
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userData.emptyUserData();
                    openLoginActivity();
                }
            });
        }

    public void openLoginActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
