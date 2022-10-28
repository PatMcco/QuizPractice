package com.example.quizpractice;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Objects;
import java.util.Random;

public class Questions extends AppCompatActivity {

    TextView defWindow, pageNum, score;
    Button bt_ans1,bt_ans2,bt_ans3,bt_ans4;
    ArrayList<String>terms = new ArrayList<>();
    ArrayList<String>definitions = new ArrayList<>();
    Map<String,String>hash = new HashMap<>();
    InputStream is1;
    InputStream is2;
    String eachDef;
    String eachTerm;
    long seed = System.nanoTime();
    ArrayList<String> usedList = new ArrayList<>();
    private static final String TAG = "myLog";



    //    Iterator<String> myIterator = hash.values().iterator();//create iterator
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
        score = findViewById(R.id.score);
        pageNum.setText("0");
        score.setText("0");


        //read in the definitions raw file to an arraylist
        try {
            is1 = getResources().openRawResource(R.raw.definitions);
            BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
            while ((eachDef = br1.readLine()) != null) {
                // the words in the file are separated by comma, so to get each word:
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
                // the words in the file are separated by comma, so to get each word:
                terms.add(eachTerm);
            }
            is2.close();//close the stream
        } catch (Throwable t) {
            t.printStackTrace();
        }

        //shuffling arrays
        Collections.shuffle(definitions, new Random(seed));
        Collections.shuffle(terms, new Random(seed));

        //key arraylist ready for quiz
        //adding both arraylists to hashmap
        for(int i = 0; i < definitions.size(); i++){
            hash.put(definitions.get(i), terms.get(i));
        }

        //call populate function and begin quiz
        populateWindow(definitions, hash);

    }//end onCreate

    //performs the randomizing of choices to populate the definition window and buttons
    public void populateWindow (ArrayList<String> defList, Map<String, String> hash){
        if(pageNum.getText().equals("10")){
            String finalScore = score.getText().toString();
            Intent intent = new Intent(Questions.this, NameEntry.class);
            intent.putExtra("score", finalScore);
            startActivity(intent);
            return;
        }
        ArrayList<String> buttonChoices = new ArrayList<>();
            String currentPage = pageNum.getText().toString();
            int convert = Integer.parseInt(currentPage);
            convert += 1;
            currentPage = String.valueOf(convert);
            String def = getDef();
            //get value from def key
            String rightAnswer = hash.get(def);
            //add term to button choices to ensure answer is in choices
            buttonChoices.add(rightAnswer);
            //add random other terms to list for button population
            while(buttonChoices.size() < 4){
                Random rand = new Random(System.nanoTime());
                //set up random int from 0-9
                int randomNum = rand.nextInt(10);
            //gets a random term using the arraylist of definitions with a random index modifier
                String randTerm = hash.get(defList.get(randomNum));
                if((!Objects.equals(randTerm, rightAnswer)) && (!buttonChoices.contains(randTerm))){
                    buttonChoices.add(randTerm);
                    Collections.shuffle(buttonChoices, new Random(System.nanoTime()));

            }
            }
            defWindow.setText(def);
            pageNum.setText(currentPage);
            bt_ans1.setText(buttonChoices.get(0));
            bt_ans2.setText(buttonChoices.get(1));
            bt_ans3.setText(buttonChoices.get(2));
            bt_ans4.setText(buttonChoices.get(3));
        }
        //populate buttons and def window - break function down into smaller functions
        //add validation for correct and incorrect choices


    public String getDef(){
        Random rand = new Random();
        //set up random int from 0-9
        int randomNum = rand.nextInt(10);
        String def = definitions.get(randomNum);
        if(usedList.contains(def)){
            return getDef();
        }
        else {usedList.add(def); return def;}
        }


    public boolean checkAnswer(String selection) {
        //retrieves the key from the hashmap, compares to the definition in the textview,
        // adjusts score
        String strKey = null;
        String defView  = defWindow.getText().toString();
        for(Map.Entry entry: hash.entrySet()){
            if(selection.equals(entry.getValue())){
                strKey = (String) entry.getKey();
                break; //breaking because its one to one map
            }
        }
        if (defView.equals(strKey)) {
            int newScore = Integer.parseInt(score.getText().toString());
            newScore += 1;
            String scoreUpdate = String.valueOf(newScore);
            score.setText(scoreUpdate);
            return true;
        } else {
            return false;
        }
    }

    public void choice1(View v) {
        String selection = bt_ans1.getText().toString();
        if (checkAnswer(selection)){
            try {
                bt_ans1.setText("correct");
                bt_ans1.setTextColor(Color.GREEN);
                Thread.sleep(400);
                populateWindow(definitions, hash);
                return;
            } catch (InterruptedException e) {
                Log.e("TAG", "thread interrupted", e);
            }
        }
        else
            try {
                bt_ans1.setText("x");
                bt_ans1.setTextColor(Color.RED);
                Thread.sleep(400);
                populateWindow(definitions, hash);
            } catch (InterruptedException e) {
                Log.e("TAG", "thread interrupted", e);
            }
    }

    public void choice2(View v) {
        String selection = bt_ans2.getText().toString();
        if (checkAnswer(selection)){
            bt_ans2.setText("correct");
            try {
                Thread.sleep(400);
                populateWindow(definitions, hash);
                return;
            } catch (InterruptedException e) {
                Log.e("TAG", "thread interrupted", e);
            }
        }
        else
            bt_ans2.setText("x");
            try {
                Thread.sleep(400);
                populateWindow(definitions, hash);
            } catch (InterruptedException e) {
                Log.e("TAG", "thread interrupted", e);
            }
        }

    public void choice3(View v) {
        String selection = bt_ans3.getText().toString();
        if (checkAnswer(selection)){
            bt_ans3.setText("correct");
            try {
                Thread.sleep(400);
                populateWindow(definitions, hash);
                return;
            } catch (InterruptedException e) {
                Log.e("TAG", "thread interrupted", e);
            }
        }
        else bt_ans3.setText("x");
            try {
                Thread.sleep(400);
                populateWindow(definitions, hash);
            } catch (InterruptedException e) {
                Log.e("TAG", "thread interrupted", e);
            }
        }

    public void choice4(View v) {
        String selection = bt_ans4.getText().toString();
        if (checkAnswer(selection)){
            bt_ans4.setText("correct");
            try {
                Thread.sleep(400);
                populateWindow(definitions, hash);
                return;
            } catch (InterruptedException e) {
                Log.e("TAG", "thread interrupted", e);
            }
        }
        else bt_ans4.setText("x");
            try {
                Thread.sleep(400);
                populateWindow(definitions, hash);
            } catch (InterruptedException e) {
                Log.e("TAG", "thread interrupted", e);
            }
        }
}


