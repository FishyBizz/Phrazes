package edu.msu.cse476.phrazes.phrazes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddCards extends AppCompatActivity {
    private EditText itemText;
    private Button addButton;
    private Button backButton;
    private ListView cardsListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> itemList;
    private TextView categoryNameTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_cards);

        itemText = findViewById(R.id.itemText);
        addButton = findViewById(R.id.addButton);
        backButton = findViewById(R.id.category_back);
        cardsListView = findViewById(R.id.cardsListView);
        categoryNameTextView = findViewById(R.id.CategoryName);

        // Retrieve the category name from the intent
        String categoryName = getIntent().getStringExtra("CategoryName");
        // Set the name to the TextView
        categoryNameTextView.setText(categoryName);

        itemList = new ArrayList<>(); //array of all the created cards

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        cardsListView.setAdapter(adapter);

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
}