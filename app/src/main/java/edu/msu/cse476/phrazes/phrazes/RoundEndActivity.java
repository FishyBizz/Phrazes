package edu.msu.cse476.phrazes.phrazes;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RoundEndActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_round_end);

        Button blueWinsButton = findViewById(R.id.blue_won_round);
        Button redWinsButton = findViewById(R.id.red_wins_round);

        blueWinsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RoundEndActivity", "Blue Pressed");
                returnResult("Blue");
            }
        });

        redWinsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RoundEndActivity", "Red Pressed");
                returnResult("Red");
            }
        });
    }
    private void returnResult(String winner) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("winner", winner);
        setResult(Activity.RESULT_OK, returnIntent); // Set result before finishing
        finish(); // This will close the activity and return to GameActivity
    }
}
