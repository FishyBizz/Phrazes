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
    private int index;
    private TextView teamchosen;
    View layout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_chosen);
        teamchosen = findViewById(R.id.TeamChosenTextView);
        layout = findViewById(R.id.TeamChoseLayout);

        random = new Random();
        index = random.nextInt(2);
        chosen = teams[index];
        if (chosen == 'r'){
            teamchosen.setText("Red team is selected to go first!");
            layout.setBackgroundColor(Color.RED);
        } else{
            teamchosen.setText("Blue team is selected to go first!");
            layout.setBackgroundColor(Color.BLUE);
        }

    }
    public void onStartCountDown(View view) {
        Intent intent = new Intent(this, CountDown.class);
        startActivity(intent);
    }
}
