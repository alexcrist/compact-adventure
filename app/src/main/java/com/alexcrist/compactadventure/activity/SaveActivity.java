package com.alexcrist.compactadventure.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alexcrist.compactadventure.R;


public class SaveActivity extends AppCompatActivity {

    Button save1Button;
    Button save2Button;
    Button save3Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        save1Button = (Button) findViewById(R.id.save_1_button);
        save1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SaveActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}
