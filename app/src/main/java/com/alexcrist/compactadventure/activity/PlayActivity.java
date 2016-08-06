package com.alexcrist.compactadventure.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alexcrist.compactadventure.core.Game;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int level = 1; // TEMPORARY VALUE
        Game game = new Game(this, level);

        setContentView(game);
    }
}
