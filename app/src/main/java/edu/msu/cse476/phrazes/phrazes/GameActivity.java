package edu.msu.cse476.phrazes.phrazes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private int redScore = 0;
    private int blueScore = 0;
    private char winningTeam;
    private WordSet wordSet;
    private Timer timer;
    private int numRounds;
    private Set<String> usedWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.wordSet = new WordSet();
        usedWords = new HashSet<>();

        SharedPreferences prefs = getSharedPreferences("AppSettings", MODE_PRIVATE);
        int savedPosition = prefs.getInt("SpinnerSelection", 1);
        String[] roundsArray = getResources().getStringArray(R.array.number_of_rounds);
        numRounds = Integer.parseInt(roundsArray[savedPosition]);

        setupGameTimer();
        winningTeam = 'N';
    }

    public void setupGameTimer() {
        timer = new Timer();
        startNewRound();
    }

    void startNewRound() {
        long delay = getRandomDelay();
        timer.schedule(new GameRoundTask(), delay);
    }

    private static long getRandomDelay() {
        Random random = new Random();
        return 45000 + random.nextInt(45000); // between 45 sec and 1.5 min
    }

    public void gameRound() {
        String newWord = getNewWord();
        if (!usedWords.contains(newWord)) {
            usedWords.add(newWord);
            TextView addCardsTitleTextView = findViewById(R.id.addCardsTitle);
            addCardsTitleTextView.setText(newWord);
        }
        if (usedWords.size() >= wordSet.getWordArray().length) {
            endRound();
        }
    }


    private String getNewWord() {
        String[] wordArray = wordSet.getWordArray();
        Random random = new Random();
        int index = random.nextInt(wordArray.length);
        return wordArray[index];
    }

    private void endRound() {
        Intent intent = new Intent(this, RoundEndActivity.class);
        intent.putExtra("redScore", redScore);
        intent.putExtra("blueScore", blueScore);
        startActivity(intent);

        if (--numRounds > 0) {
            setupGameTimer();
        } else {
            endGame();
        }
    }

    private void endGame() {
        determineWinningTeam();
        Intent intent = new Intent(GameActivity.this, GameEndActivity.class);
        intent.putExtra("winningTeam", winningTeam);
        startActivity(intent);
        finish();
    }


    private void determineWinningTeam() {
        if (redScore > blueScore) {
            winningTeam = 'R';
        } else if (blueScore > redScore) {
            winningTeam = 'B';
        } else {
            winningTeam = 'N'; // shouldn't happen with odd rounds
        }
    }

    public void incrementRedScore() {
        redScore++;
    }

    public void incrementBlueScore() {
        blueScore++;
    }

    private class GameRoundTask extends TimerTask {
        @Override
        public void run() {
            gameRound();
        }
    }
}
