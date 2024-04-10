package edu.msu.cse476.phrazes.phrazes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DownloadCategoriesActivity extends AppCompatActivity {

    private Button backButton;
    private ListView categoryListView;
    private ArrayList<String> categoryList;
    private ArrayAdapter<String> adapter;
    private DatabaseReference categoriesRef;
    private SQLiteDatabase database;
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_categories);

        backButton = findViewById(R.id.download_back);
        categoryListView = findViewById(R.id.DownloadDeckList);
        categoryList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoryList);
        categoryListView.setAdapter(adapter);
        categoriesRef = FirebaseDatabase.getInstance().getReference().child("categories");
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categoryList.clear();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    String categoryName = categorySnapshot.getKey();
                    categoryList.add(categoryName);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DownloadCategoriesActivity.this, "Failed to retrieve categories from Firebase", Toast.LENGTH_SHORT).show();
            }
        });
        categoryListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategory = categoryList.get(position);
            dbHelper.downloadCategoryData(selectedCategory, this);
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buttonIntent = new Intent(DownloadCategoriesActivity.this,
                        MainMenu.class);
                // Clear other activities on top of MainMenu
                buttonIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(buttonIntent);
            }
        });
    }


}