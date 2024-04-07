package edu.msu.cse476.phrazes.phrazes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class WordsActivity extends AppCompatActivity {

    private Button backButton;
    private ListView wordsListView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        backButton = findViewById(R.id.wordsBack);
        wordsListView = findViewById(R.id.wordsListView);
        dbHelper = new DatabaseHelper(this);


        // Words for the category
        ArrayList<String> words = dbHelper.getWordsForCategory();


        // Set up the ArrayAdapter and assign it to the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, words);
        wordsListView.setAdapter(adapter);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buttonIntent = new Intent(WordsActivity.this, MainMenu.class);
                buttonIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(buttonIntent);
            }
        });

    }
}