package edu.msu.cse476.phrazes.phrazes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private int redScore = 0;
    private int blueScore = 0;
    private char winningTeam;
    private static CountDownTimer gameTimer;
    private int numRounds;
    private int currRound = 0;
    private ArrayList<String> words;
    private Set<String> usedWords;
    // Timer visual textView
//    private TextView tempTimerView;
    private static final int ROUND_END_REQUEST_CODE = 1;

    private static final int ROTATION_THRESHOLD = 9;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private long lastUpdateTime = 0;
    private long timeLeftOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);


        DatabaseHelper dbHelper = new DatabaseHelper(this);
        // For testing only...
//        tempTimerView = findViewById(R.id.TempTimerHeading);

        // word list initializations
        usedWords = new HashSet<>();
        words = dbHelper.getWordsForCategory();

        // Parse the string value to an integer
        SharedPreferences prefs = getSharedPreferences("AppSettings", MODE_PRIVATE);
        int savedPosition = prefs.getInt("SpinnerSelection", 1);
        String[] roundsArray = getResources().getStringArray(R.array.number_of_rounds);
        numRounds = Integer.parseInt(roundsArray[savedPosition]);

        //accelerometer setup
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Start Game
        startGameTimer();
    }

    protected void onResume() {
        super.onResume();
        // Register the accelerometer sensor listener
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the accelerometer sensor listener
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Check if the sensor is the gyroscope
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                // Get acceleration values
                float xAccel = event.values[0];

                // Check if the device is rotated to the left or right
                if (Math.abs(xAccel) > ROTATION_THRESHOLD) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastUpdateTime > 2000) {
                        // Rotation detected, updateWord()
                        updateWord();
                        lastUpdateTime = currentTime; // Update last update time
                    }
                    // Rotation detected updateWord()
                    updateWord();
                }
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // Get acceleration values
                float yAccel = event.values[1];

                // Check if the device is rotated to the left or right
                if (Math.abs(yAccel) > ROTATION_THRESHOLD) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastUpdateTime > 2000) {
                        // Rotation detected, updateWord()
                        updateWord();
                        lastUpdateTime = currentTime; // Update last update time
                    }
                    // Rotation detected updateWord()
                    updateWord();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void startGameTimer() {

        // update round number text view
        currRound++;
        TextView roundNumberTextView = findViewById(R.id.RoundNumber);
        roundNumberTextView.setText(getString(R.string.round_number, currRound));

        if (currRound <= numRounds) {
            updateWord();
            gameTimer = new CountDownTimer(getRandomDelay(), 1000) { // 30 seconds for each round
                public void onTick(long millisUntilFinished) {
                    // For testing only...
                    timeLeftOver = millisUntilFinished;
//                    tempTimerView.setText("Time: " + millisUntilFinished / 1000);
                }
                public void onFinish() {
                    endRound();
                }
            }.start();
        } else {
            endGame();
        }
    }


    private static long getRandomDelay() {
        Random random = new Random();
        return 35000 + random.nextInt(45000); // between 35 sec and 45 sec
        // For testing only...
//        return 20000; // 20 seconds
    }

    // USED FOR testing
//    public void passButtonClicked(View view) {
//        updateWord();
//    }
//
//    public void correctButtonClicked(View view) {
//        updateWord();
//    }

    public void updateWord() {
        // Display a new word
        String newWord = getNewWord();
        TextView addCardsTitleTextView = findViewById(R.id.addCardsTitle);
        addCardsTitleTextView.setText(newWord);
    }

    private String getNewWord() {
        if(usedWords.size() == words.size()){
            usedWords.clear();
        }
        Random random = new Random();
        int index;
        String newWord;
        do {
            index = random.nextInt(words.size());
            newWord = words.get(index);
        } while (usedWords.contains(newWord));

        usedWords.add(newWord); // add the new word to usedWords
        return newWord;
    }

    private void endRound() {
        // Logic to record who won the round, could update a model or UI
        gameTimer.cancel(); // Cancel current timer if still running

        // Intent Declaration
        Intent intent = new Intent(GameActivity.this, RoundEndActivity.class);
        startActivityForResult(intent, ROUND_END_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ROUND_END_REQUEST_CODE && resultCode == RESULT_OK) {
            String winner = data.getStringExtra("winner");
            if("Blue".equals(winner)) {
                incrementBlueScore();
            }
            else if("Red".equals(winner)) {
                incrementRedScore();
            }
            startGameTimer(); // Start next round or end game if all rounds are completed
        }
    }

    private void endGame() {
        // Determine the winning team and end the game
        determineWinningTeam();
        Intent intent = new Intent(GameActivity.this, GameEndActivity.class);
        intent.putExtra("winningTeam", winningTeam);
        startActivity(intent);
        finish();
    }

    private void determineWinningTeam() {
        // Determine the winning team based on the scores
        if (redScore > blueScore) {
            winningTeam = 'R';
        } else {
            winningTeam = 'B';
        }
    }

    public void incrementRedScore() {
        redScore++;
    }

    public void incrementBlueScore() {
        blueScore++;
    }
}

