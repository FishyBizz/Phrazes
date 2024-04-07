package edu.msu.cse476.phrazes.phrazes;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class RoundEndActivity extends Activity {

    private Button blueWinsButton;
    private Button redWinsButton;
    private GameActivity gameActivity;

    public RoundEndActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_round_end);

        blueWinsButton = findViewById(R.id.blue_won_round);
        redWinsButton = findViewById(R.id.red_wins_round);

        blueWinsButton.setOnClickListener(v -> {
            gameActivity.incrementBlueScore();
            gameActivity.setupGameTimer();
            finish();
        });

        redWinsButton.setOnClickListener(v -> {
            gameActivity.incrementRedScore();
            gameActivity.setupGameTimer();
            finish();
        });
    }
}

