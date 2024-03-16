package edu.msu.cse476.phrazes.phrazes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    private Button backButton;
    private Spinner roundSpinner;

    // Firebase instance
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        backButton = findViewById(R.id.settings_back);
        roundSpinner = findViewById(R.id.TimeLimits);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buttonIntent = new Intent(SettingsActivity.this,
                        MainMenu.class);
                // Clear other activities on top of MainMenu
                buttonIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(buttonIntent);
            }
        });

        // To Initialize the Rounds Spinner with the array values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.number_of_rounds, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roundSpinner.setAdapter(adapter);

        // Default Rounds Spinner Value
        String defaultValue = "3";
        int spinnerPos = adapter.getPosition(defaultValue);
        roundSpinner.setSelection(spinnerPos);

        // Logout of the Game
        Button logout = findViewById(R.id.logout_button);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign out from Firebase
                mAuth.signOut();

                // Navigate back to login screen and clear the activity stack
                Intent loginIntent = new Intent(SettingsActivity.this,
                        LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);

                // Finish SettingsActivity so user must log back in
                finish();
            }
        });

    }
}