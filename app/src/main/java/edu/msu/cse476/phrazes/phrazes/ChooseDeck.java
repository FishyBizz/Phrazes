package edu.msu.cse476.phrazes.phrazes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseDeck extends AppCompatActivity {
    private Button playButton;
    private ArrayAdapter<String> adapter;
    private ListView categoryList;
    private ArrayList<String> tableNames;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_category);

        playButton = findViewById(R.id.PlayGameButton);
        categoryList = findViewById(R.id.categoryList);
        dbHelper = new DatabaseHelper(this);

        ArrayList<String> categoryNames = getCategoryNames();
        for (String categoryName : categoryNames) {
            ArrayList<String> cards = getCardsForCategory(categoryName);
            tableNames.addAll(cards);
        }

        // Create the ArrayAdapter and set it to the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoryNames);
        categoryList.setAdapter(adapter);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buttonIntent = new Intent(ChooseDeck.this, ChooseTeam.class);
                startActivity(buttonIntent);
            }
        });
    }
    private ArrayList<String> getCategoryNames() {
        ArrayList<String> categoryNames = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String tableName = cursor.getString(cursor.getColumnIndex("name"));
                // Exclude system tables and views, if necessary
                if (!tableName.equals("sqlite_sequence") && !tableName.equals("android_metadata")) {
                    categoryNames.add(tableName);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categoryNames;
    }
    private ArrayList<String> getCardsForCategory(String categoryName) {
        ArrayList<String> cards = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT content FROM " + categoryName, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String card = cursor.getString(cursor.getColumnIndex("content"));
                cards.add(card);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cards;
    }
}
