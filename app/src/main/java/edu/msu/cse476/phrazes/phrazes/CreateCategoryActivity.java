package edu.msu.cse476.phrazes.phrazes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;


public class CreateCategoryActivity extends AppCompatActivity {

    private Button backButton;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_category);

        backButton = findViewById(R.id.create_back);
        createButton = findViewById(R.id.createDeckButton);

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

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buttonIntent = new Intent(CreateCategoryActivity.this,
                        AddCards.class);
                startActivity(buttonIntent);
            }
        });
    }

}