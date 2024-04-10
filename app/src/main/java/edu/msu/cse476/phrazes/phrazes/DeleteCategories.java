package edu.msu.cse476.phrazes.phrazes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

public class DeleteCategories extends AppCompatActivity {
    private Button backButton;
    private ArrayAdapter<String> adapter;
    private ListView categoriesDelete;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_categories);

        backButton = findViewById(R.id.deleteBack);
        categoriesDelete = findViewById(R.id.categoriesDelete);
        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        ArrayList<String> categoryNames = getCategoryNames();

        // Create the ArrayAdapter and set it to the ListView
        adapter = new ArrayAdapter<>(this, R.layout.list_item_custom, R.id.textViewListItem,
                categoryNames);
        categoriesDelete.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buttonIntent = new Intent(DeleteCategories.this,
                        MainMenu.class);
                buttonIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(buttonIntent);
            }
        });
        categoriesDelete.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categoryNames.get(position);
                if (!selectedCategory.equals("Phazes_Default")){
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL("DROP TABLE IF EXISTS " + selectedCategory);
                    db.close();
                    Toast.makeText(DeleteCategories.this, "Decks Deleted successfully",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DeleteCategories.this, MainMenu.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(DeleteCategories.this, "Default List cannot be " +
                                    "deleted",
                            Toast.LENGTH_SHORT).show();
                }
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
                @SuppressLint("Range") String tableName = cursor.getString(cursor.getColumnIndex
                        ("name"));
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