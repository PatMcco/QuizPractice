
package com.example.quizpractice;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NameEntry extends AppCompatActivity {

    Button submitName;
    EditText nameEntry;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_entry);
        submitName = findViewById(R.id.bt_submit);
        nameEntry = findViewById(R.id.enterName);
    }

    public void submitName(View v){
        String currentScore = getIntent().getStringExtra("score");
        String nameEntered = nameEntry.getText().toString();
        Intent intent = new Intent(NameEntry.this, HighScores.class);
        String fullEntry = "        " + nameEntered + "  -  " + currentScore;
        intent.putExtra("formattedName", fullEntry);
        startActivity(intent);
    }
}