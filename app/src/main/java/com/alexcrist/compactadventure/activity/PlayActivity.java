package com.alexcrist.compactadventure.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.alexcrist.compactadventure.core.Game;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int level = intent.getIntExtra("level", -1);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Create modal for victory
        builder.setTitle("You are victorious!");
        builder.setPositiveButton("Continue...", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                finish();
            }
        });
        AlertDialog successDialog = builder.create();

        // Create modal for defeat
        builder.setTitle("You have fallen.");
        builder.setPositiveButton("Continue...", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                finish();
            }
        });
        AlertDialog failureDialog = builder.create();

        // Start up a new game
        Game game = new Game(this, level, successDialog, failureDialog);
        setContentView(game);
    }

}
