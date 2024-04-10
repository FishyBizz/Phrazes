package edu.msu.cse476.phrazes.phrazes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class CreateCategoryActivity extends AppCompatActivity {

    private Button backButton;
    private Button createButton;
    private EditText categoryNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_category);

        backButton = findViewById(R.id.create_back);
        categoryNameEditText = findViewById(R.id.categoryName); // User created name

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buttonIntent = new Intent(CreateCategoryActivity.this,
                        MainMenu.class);
                // Clear other activities on top of MainMenu
                buttonIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(buttonIntent);
            }
        });

        createButton = findViewById(R.id.createDeckButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user category name
                String categoryName = categoryNameEditText.getText().toString().trim();
                categoryName = categoryName.replace(" ", "_");
                if(categoryName.isEmpty()) {
                    Toast.makeText(CreateCategoryActivity.this, "Please enter a " +
                            "category name.", Toast.LENGTH_LONG).show();
                } else {
                    // Start AddCards activity
                    Intent buttonIntent = new Intent(CreateCategoryActivity.this,
                            AddCards.class);

                    // Category name added to intent
                    buttonIntent.putExtra("CategoryName", categoryName);

                    startActivity(buttonIntent);
                }
            }
        });
    }
}