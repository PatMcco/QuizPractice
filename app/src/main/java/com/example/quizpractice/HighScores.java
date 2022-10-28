package com.example.quizpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class HighScores extends AppCompatActivity {

    Button restart;
    EditText scoreTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        String highScore = getIntent().getStringExtra("formattedName");
        restart = findViewById(R.id.bt_restart);
        scoreTable = findViewById(R.id.highScoreList);
        scoreTable.setText(highScore);
    }

    public void restartPressed(View v){
        Intent intent = new Intent(HighScores.this, Questions.class);
        startActivity(intent);
    }
}