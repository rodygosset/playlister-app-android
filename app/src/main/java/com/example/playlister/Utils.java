package com.example.playlister;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

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

        genresListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openActivity(ctx, GenresListActivity.class);
            }
        });
    }
}
