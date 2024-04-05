package edu.msu.cse476.phrazes.phrazes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ChooseTeam extends AppCompatActivity {
    private char chosen;
    private Random random;
    private char[] teams = {'r', 'b'};
    private TextView teamchosen;
    View layout;
    private static final String KEY_SELECTED_TEAM = "selected_team";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_chosen);
        teamchosen = findViewById(R.id.TeamChosenTextView);
        layout = findViewById(R.id.TeamChoseLayout);

        random = new Random();
        if (savedInstanceState != null) {
            // Restore the selected team if savedInstanceState is not null
            chosen = savedInstanceState.getChar(KEY_SELECTED_TEAM, teams[random.nextInt(2)]);
        } else {
            chosen = teams[random.nextInt(2)];
        }

        updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the selected team when the activity is about to be destroyed
        outState.putChar(KEY_SELECTED_TEAM, chosen);
        super.onSaveInstanceState(outState);
    }

    private void updateUI() {
        if (chosen == 'r') {
            teamchosen.setText(getString(R.string.red_first));
            teamchosen.setTextColor(Color.YELLOW);
            layout.setBackgroundColor(Color.RED);
        } else {
            teamchosen.setText(getString(R.string.blue_first));
            teamchosen.setTextColor(Color.YELLOW);
            layout.setBackgroundColor(Color.BLUE);
        }
    }

    public void onStartCountDown(View view) {
        Intent intent = new Intent(this, CountDown.class);
        startActivity(intent);
    }
}
