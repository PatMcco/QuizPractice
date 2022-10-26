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
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Questions extends AppCompatActivity {

    TextView defWindow;
    TextView pageNum;
    Button bt_ans1,bt_ans2,bt_ans3,bt_ans4,bt_submit;
    ArrayList<String>terms = new ArrayList<>();
    ArrayList<String>definitions = new ArrayList<>();
    Map<String,String>hash = new HashMap<>();
    ArrayList<String>usedDefs = new ArrayList<>();
    ArrayList<String>choices = new ArrayList<>();
    ArrayList<String>questionPool = new ArrayList<>();
    InputStream is1;
    InputStream is2;
    String eachDef;
    String eachTerm;
    long seed = System.nanoTime();
    int currentPage = 0;
    Intent intent;
    int Score;




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
        bt_submit = findViewById(R.id.bt_submit);
        pageNum.setText("1");

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

        //adding both arraylists to hashmap
        for(int i = 0; i < definitions.size(); i++){
            hash.put(definitions.get(i), terms.get(i));
        }
        //adding keys to an array
        questionPool.addAll(hash.keySet());
        
        //shuffling keyset
        Collections.shuffle(questionPool, new Random(seed));
        //key arraylist ready for quiz

        //call populate function and begin quiz
        populateWindow(questionPool, hash);

    }//end onCreate

    //performs the randomizing of choices to populate the definition window and buttons
    public ArrayList<String> populateWindow (ArrayList<String> defList, Map<String, String> hash){
        ArrayList<String> usedList = new ArrayList<>();
        ArrayList<String> buttonChoices = new ArrayList<>();
        Iterator<String> myIterator = hash.values().iterator();//create iterator

        while(currentPage <= 10){
            currentPage = getPageNum();
            Random rand = new Random();
            //set up random int from 0-9
            int randomNum = rand.nextInt(10);
            //get a random definition
            String def = defList.get(randomNum);
            usedList.add(def);
            //get value from def key
            String term = hash.get(def);
            //add term to button choices to ensure answer is in choices
            buttonChoices.add(term);
            //add random other terms to list for button population
            while(buttonChoices.size() < 4){
            //gets a random term using the arraylist of definitions with a random index modifier
                String randTerm = hash.get(defList.get(randomNum));
                if((!buttonChoices.contains(randTerm)) && (!buttonChoices.contains(term))){
                buttonChoices.add(randTerm);
            }
            }
            defWindow.setText(def);
            bt_ans1.setText(buttonChoices.get(0));
            bt_ans2.setText(buttonChoices.get(1));
            bt_ans3.setText(buttonChoices.get(2));
            bt_ans4.setText(buttonChoices.get(3));
        }
        //populate buttons and def window - break function down into smaller functions
        //add validation for correct and incorrect choices
        return usedDefs;
    }
    //gets page number, increments by 1 each time
    public int getPageNum(){
        int currentPage = Integer.parseInt(pageNum.getText().toString());
        return currentPage += 1;
    }

    public int checkAnswer(String selection){
        if(defWindow.getText().toString() == hash.get(selection)){
        return Score += 1;}
        else{
            return Score;
            }

//        while(true){
//            for (String ele: arr) {
//                if ele == {
//
//                }
//            }
//        }

    }

    public void choice1(View v) {
        checkAnswer(bt_ans1.getText().toString());
    }

    public void choice2(View v) {

    }

    public void choice3(View v) {

    }

    public void choice4(View v) {

    }
}


