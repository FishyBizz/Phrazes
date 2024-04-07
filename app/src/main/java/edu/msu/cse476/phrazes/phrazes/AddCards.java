package edu.msu.cse476.phrazes.phrazes;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddCards extends AppCompatActivity {
    private EditText itemText;
    private Button addButton;
    private Button backButton;
    private Button saveButton;
    private Button uploadButton;
    private ListView cardsListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> itemList;
    private TextView categoryNameTextView;
    private SQLiteDatabase database;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_cards);

        itemText = findViewById(R.id.itemText);
        addButton = findViewById(R.id.addButton);
        backButton = findViewById(R.id.category_back);
        saveButton = findViewById(R.id.saveButton);
        uploadButton = findViewById(R.id.uploadButton);
        cardsListView = findViewById(R.id.cardsListView);
        categoryNameTextView = findViewById(R.id.CategoryName);

        // Retrieve the category name from the intent
        String categoryName = getIntent().getStringExtra("CategoryName");
        // Set the name to the TextView
        categoryNameTextView.setText(categoryName);

        itemList = new ArrayList<>(); //array of all the created cards

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        cardsListView.setAdapter(adapter);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemList.size() < 60) {
                    String newCard = itemText.getText().toString().trim();
                    if (!newCard.isEmpty()) {
                        itemList.add(newCard);
                        adapter.notifyDataSetChanged();
                        itemText.getText().clear();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "You can't add more than 60 items.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buttonIntent = new Intent(AddCards.this,
                        CreateCategoryActivity.class);
                // Clear other activities on top of MainMenu
                buttonIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(buttonIntent);
            }
        });

    }
    public void onSave(View v) {
        // Create a table with the name specified in categoryName
        DatabaseContract.setTableName(categoryNameTextView.getText().toString());
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + DatabaseContract.getTableName()
                + " (" +
                DatabaseContract.CardEntry._ID + " INTEGER PRIMARY KEY," +
                DatabaseContract.CardEntry.COLUMN_CONTENT + " TEXT)";

        database.execSQL(createTableQuery);
        for (String card : itemList) {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.CardEntry.COLUMN_CONTENT, card);
        }
        Toast.makeText(AddCards.this, "Cards saved successfully",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddCards.this, MainMenu.class);
        startActivity(intent);
    }
}
