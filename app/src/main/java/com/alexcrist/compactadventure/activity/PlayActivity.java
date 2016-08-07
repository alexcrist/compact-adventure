package com.alexcrist.compactadventure.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alexcrist.compactadventure.core.Game;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int level = 2; // TEMPORARY VALUE
        Game game = new Game(this, level);

        setContentView(game);
    }
}
