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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddCards extends AppCompatActivity {
    private EditText itemText;
    private Button addButton;
    private Button backButton;
    private ListView cardsListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> itemList;
    private TextView categoryNameTextView;
    private SQLiteDatabase database;
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    private DatabaseReference rdatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_cards);

        itemText = findViewById(R.id.itemText);
        addButton = findViewById(R.id.addButton);
        backButton = findViewById(R.id.category_back);
        Button uploadButton = findViewById(R.id.uploadButton);
        cardsListView = findViewById(R.id.cardsListView);
        categoryNameTextView = findViewById(R.id.CategoryName);
        rdatabase = FirebaseDatabase.getInstance().getReference();

        // Retrieve the category name from the intent
        String categoryName = getIntent().getStringExtra("CategoryName");
        // Set the name to the TextView
        categoryNameTextView.setText(categoryName);

        itemList = new ArrayList<>(); //array of all the created cards

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        cardsListView.setAdapter(adapter);

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
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLocally();
                saveToRealtimeDatabase();
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
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (String card : itemList) {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.CardEntry.COLUMN_CONTENT, card);
            db.insert(DatabaseContract.getTableName(), null, values);

        }
        db.close();
        Toast.makeText(AddCards.this, "Cards saved successfully",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddCards.this, MainMenu.class);
        startActivity(intent);
    }

    public void saveLocally(){
        DatabaseContract.setTableName(categoryNameTextView.getText().toString());
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + DatabaseContract.getTableName()
                + " (" +
                DatabaseContract.CardEntry._ID + " INTEGER PRIMARY KEY," +
                DatabaseContract.CardEntry.COLUMN_CONTENT + " TEXT)";

        database.execSQL(createTableQuery);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (String card : itemList) {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.CardEntry.COLUMN_CONTENT, card);
            db.insert(DatabaseContract.getTableName(), null, values);

        }
        db.close();
    }

    public void saveToRealtimeDatabase(){
        String categoryName = categoryNameTextView.getText().toString();
        DatabaseReference categoryRef = rdatabase.child("categories").child(categoryName);
        categoryRef.removeValue();
        for (int i = 0; i < itemList.size(); i++) {
            String card = itemList.get(i);
            categoryRef.child(String.valueOf(i)).setValue(card);
        }

        Toast.makeText(AddCards.this, "Cards saved to Firebase successfully",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddCards.this, MainMenu.class);
        startActivity(intent);
    }
}
