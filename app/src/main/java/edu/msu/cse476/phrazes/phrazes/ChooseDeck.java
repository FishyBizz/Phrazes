package edu.msu.cse476.phrazes.phrazes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseDeck extends AppCompatActivity {

    private ArrayList<String> categoryNames;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_category);

        ListView categoryList = findViewById(R.id.categoryList);
        dbHelper = new DatabaseHelper(this);

        categoryNames = getCategoryNames();

        // Create the ArrayAdapter and set it to the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_custom, R.id.textViewListItem,
                categoryNames);
        categoryList.setAdapter(adapter);

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categoryNames.get(position);

                // Set table name
                DatabaseContract.setTableName(selectedCategory);

                // Navigate to WordsActivity
                Intent intent = new Intent(ChooseDeck.this, ChooseTeam.class);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.choose_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buttonIntent = new Intent(ChooseDeck.this,
                        MainMenu.class);
                startActivity(buttonIntent);
            }
        });
    }
    private ArrayList<String> getCategoryNames() {
        ArrayList<String> categoryNames = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'",
                null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String tableName = cursor.getString(cursor.getColumnIndex(
                        "name"));
                // Exclude system tables and views, if necessary
                if (!tableName.equals("sqlite_sequence") && !tableName.equals("android_metadata")
                        && !tableName.equals("cards")) {
                    categoryNames.add(tableName);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categoryNames;
    }
}
