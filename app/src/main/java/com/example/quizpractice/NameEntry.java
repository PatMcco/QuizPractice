
package com.example.quizpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NameEntry extends AppCompatActivity {

    Button submitName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_entry);
        submitName = findViewById(R.id.bt_submit);
        submitName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NameEntry.this, HighScores.class);
                startActivity(intent);
            }
        });
    }
    public void submitName(View v){

    }
}