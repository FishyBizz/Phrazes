package edu.msu.cse476.phrazes.phrazes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameEndActivity extends Activity {

    private TextView winnerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        winnerText = findViewById(R.id.winnerText);
        Button backButton = findViewById(R.id.gameEnd_back);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("winningTeam")) {
            char winningTeam = intent.getCharExtra("winningTeam", 'N');
            displayWinningTeam(winningTeam);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buttonIntent = new Intent(GameEndActivity.this,
                        MainMenu.class);
                startActivity(buttonIntent);
            }
        });
    }

    private void displayWinningTeam(char winningTeam) {
        String winningText;
        if (winningTeam == 'R') {
            winningText = getString(R.string.red_wins_game);
        } else {
            winningText = getString(R.string.blue_wins_game);
        }
        winnerText.setText(winningText);
    }
}
