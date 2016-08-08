package com.alexcrist.compactadventure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alexcrist.compactadventure.R;

public class TitleActivity extends AppCompatActivity {

    Button storyButton;
    Button freePlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        storyButton = (Button) findViewById(R.id.story_button);
        storyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TitleActivity.this, SaveActivity.class);
                startActivity(intent);
            }
        });

        freePlayButton = (Button) findViewById(R.id.free_play_button);
        freePlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TitleActivity.this, LevelActivity.class);
                startActivity(intent);
            }
        });
    }
}
