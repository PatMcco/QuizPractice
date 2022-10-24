package com.example.quizpractice;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Questions extends AppCompatActivity {

    TextView defWindow;
    TextView pageNum;
    Button bt_ans1,bt_ans2,bt_ans3,bt_ans4;
    ArrayList<String>terms = new ArrayList<>();
    ArrayList<String>definitions = new ArrayList<>();
    Map<String,String> hash = new HashMap<>();
    ArrayList<String>usedDefs = new ArrayList<>();
    ArrayList<String>choices = new ArrayList<>();
    InputStream is1;
    InputStream is2;
    String eachDef;
    String eachTerm;
    long seed = System.nanoTime();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        defWindow = findViewById(R.id.def_View);
        pageNum = findViewById(R.id.page_Num);
        bt_ans1 = findViewById(R.id.bt_ans1);
        bt_ans2 = findViewById(R.id.bt_ans2);
        bt_ans3 = findViewById(R.id.bt_ans3);
        bt_ans4 = findViewById(R.id.bt_ans4);
        int currentPage = 1;
        pageNum.setText("Question 1");

        //read in the definitions raw file to an arraylist
        try {
            is1 = getResources().openRawResource(R.raw.definitions);
            BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
            while ((eachDef = br1.readLine()) != null) {
                // `the words in the file are separated by comma, so to get each word:
                definitions.add(eachDef);
            }
            is1.close();//close the stream
        } catch (Throwable t) {
            t.printStackTrace();

        }
        //read in the terms raw file to an arraylist
        try {
            is2 = getResources().openRawResource(R.raw.terms);
            BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
            while ((eachTerm = br2.readLine()) != null) {
                // `the words in the file are separated by comma, so to get each word:
                terms.add(eachTerm);
            }
            is2.close();//close the stream
        } catch (Throwable t) {
            t.printStackTrace();
        }

//        //shuffle both arraylists evenly, so the order is random but the pairs are the same
//        Collections.shuffle(definitions, new Random(seed));
//        Collections.shuffle(terms, new Random(seed));

        //adding both arraylists to hashmap after shuffle
        for(int i = 0; i < definitions.size(); i++){
            hash.put(definitions.get(i), terms.get(i));
        }
        //put keys into a list and randomize them by nanotime
        Set<String> keySet = hash.keySet();
        List<String> keyList = new ArrayList<>(keySet);

        int size = keyList.size();
        int randId = new Random(seed).nextInt(size);

        String randomDef = keyList.get(randId);
        String randomTerm = hash.get(randomDef);

        while(currentPage <= 10){
            String currentTerm = randomTerm;
            String currentQuestion = randomDef;//assign random definition to currentQuestion
            //check if definition is contained in the previously used definitions, if all are used, end.
            while(usedDefs.contains(currentQuestion)) {
                currentQuestion = randomDef;
                currentTerm = randomTerm;
                if(usedDefs.size() == 10){
                    break;
                }
            }
            defWindow.setText(currentQuestion);
            usedDefs.add(currentQuestion);
            while(choices.size() < 3){
                choices.add(currentTerm);
                currentTerm = randomTerm;
                if(!choices.contains(currentTerm)){
                    choices.add(currentTerm);
                }


            }
        }

    }//end onCreate



    public void choice1(View v) {
        defWindow.setText(String.format("%s", hash));
    }

    public void choice2(View v) {
        defWindow.setText("2");
    }

    public void choice3(View v) {
        defWindow.setText("3");
    }

    public void choice4(View v) {
        defWindow.setText("4");
    }
}


