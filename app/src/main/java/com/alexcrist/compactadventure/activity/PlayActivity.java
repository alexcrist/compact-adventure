package com.alexcrist.compactadventure.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.alexcrist.compactadventure.core.Game;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        int level = intent.getIntExtra("level", -1);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        // Create modal for victory
        builder.setTitle("You are victorious!");
        builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                levelSuccess();
            }
        });
        AlertDialog successDialog = builder.create();

        // Create modal for defeat
        builder.setTitle("You have fallen.");
        builder.setPositiveButton("Continue...", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                levelFailure();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                levelFailure();
            }
        });
        AlertDialog failureDialog = builder.create();

        // Start up a new game
        Game game = new Game(this, level, successDialog, failureDialog);
        setContentView(game);
    }

    private void levelSuccess() {
        finish();
    }

    private void levelFailure() {
        finish();
    }

}
