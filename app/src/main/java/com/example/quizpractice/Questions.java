package com.example.quizpractice;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class Questions extends AppCompatActivity {

    TextView defWindow;
    TextView pageNum;
    Button bt_ans1,bt_ans2,bt_ans3,bt_ans4;
    ArrayList<String>terms = new ArrayList<>();
    ArrayList<String>definitions = new ArrayList<>();
    Map<String,String>hash = new HashMap<>();
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

        //adding both arraylists to hashmap
        for(int i = 0; i < definitions.size(); i++){
            hash.put(definitions.get(i), terms.get(i));
        }

        //shuffling arraylists equally
        int size = definitions.size();
        int randId = new Random(seed).nextInt(size);

        Collections.shuffle(definitions, new Random(seed));
        Collections.shuffle(terms, new Random(seed));//arraylists ready for quiz

        //put matching term and definition in arraylist to prep first question
        String randomDef = definitions.get(randId);
        String randomTerm = hash.get(randomDef);
        List<String> correctMatch = new ArrayList<>();
        correctMatch.add(randomDef);
        correctMatch.add(randomTerm);


        while(currentPage <= 10){
            String currentQuestion = correctMatch.get(0);
            String currentTerm = correctMatch.get(1);
            //assign random definition to currentQuestion
            //check if definition is contained in the previously used definitions, if all are used, end.

            while(!usedDefs.contains(currentQuestion)) {
                currentQuestion = randomDef;
                currentTerm = randomTerm;
                if(usedDefs.size() == 10){
                    break;
                }
            }
            defWindow.setText(currentQuestion);//set current question to definition window
            usedDefs.add(currentQuestion);//add current question to pool of already used questions
        }

    }//end onCreate

    //performs the randomizing of choices to populate the definition window and buttons
    public ArrayList<String> populateWindow(ArrayList<String>defList, ArrayList<String> termList, ArrayList<String> usedList){
        ArrayList<String> buttonChoices = new ArrayList<>();
        Random rand = new Random();
        //set up random int from 0-9
        int randomNum = rand.nextInt(10);
        //get a random definition
        String def = defList.get(randomNum);
        //get value from def key
        String term = hash.get(def);
        buttonChoices.add(term);
        //add correct term and random other terms to list for button population
        while(buttonChoices.size() < 4){
            String randTerm = termList.get(randomNum);
            if((!buttonChoices.contains(randTerm)) && (!buttonChoices.contains(term))){
            buttonChoices.add(randTerm);
        }
        }
        //populate buttons and def window - break function down into smaller functions
        //add validation for correct and incorrect choices
    }


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


