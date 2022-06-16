package com.example.playlister;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

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
}
