package com.alexcrist.compactadventure.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.alexcrist.compactadventure.R;

public class LevelActivity extends AppCompatActivity implements View.OnClickListener {

    private TableLayout levelTable;

    private final static int TABLE_ROWS = 10;
    private final static int TABLE_COLUMNS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        levelTable = (TableLayout) findViewById(R.id.level_table);

        int buttonNumber = 0;

        for (int i = 0; i < TABLE_ROWS; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1));

            for (int j = 0; j < TABLE_COLUMNS; j++) {
                buttonNumber++;
                Button button = new Button(this);
                button.setText(Integer.toString(buttonNumber));
                button.setOnClickListener(this);
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

                row.addView(button);
            }

            levelTable.addView(row);
        }

    }

    @Override
    public void onClick(View view) {
        Log.i("CLICK", "CLICK");
        int level = Integer.parseInt(((Button) view).getText().toString());
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("level", level);
        startActivity(intent);
    }
}
