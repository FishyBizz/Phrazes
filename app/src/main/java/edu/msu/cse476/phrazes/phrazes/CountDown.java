package edu.msu.cse476.phrazes.phrazes;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CountDown extends AppCompatActivity {
    private CountDownTimer timer;
    private TextView countdownTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_down);

        countdownTextView = findViewById(R.id.CountDownView);
        startCountDownTimer();
    }

    private void startCountDownTimer(){
        timer = new CountDownTimer(6000, 1000) { // Countdown from 6 seconds with 1 second intervals
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / 1000;
                countdownTextView.setText(getString(R.string.countdown_format, secondsLeft));
            }
            @Override
            public void onFinish() {
                countdownTextView.setText(getString(R.string.game_starting));
                Intent intent = new Intent(CountDown.this, GameActivity.class);
                startActivity(intent);
            }
        };
        timer.start(); // Start the countdown timer
    }
}

