package com.alexcrist.compactadventure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alexcrist.compactadventure.R;

public class DetailsActivity extends AppCompatActivity {

    TextView detailsLevelText;
    Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        final int level = intent.getIntExtra("level", -1);

        detailsLevelText = (TextView) findViewById(R.id.details_level_text);
        String levelString = "Level " + Integer.toString(level);
        detailsLevelText.setText(levelString);

        goButton = (Button) findViewById(R.id.go_button);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, PlayActivity.class);
                intent.putExtra("level", level);
                startActivity(intent);
            }
        });
    }
}
