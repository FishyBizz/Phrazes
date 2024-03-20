package edu.msu.cse476.phrazes.phrazes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    private int redScore = 0;
    private int blueScore = 0;
    private char winningTeam;
    private WordSet wordSet;
    private Timer timer;
    private int numRounds;

    private Set<String> usedWords;

    public GameActivity(int numRounds) {
        this.wordSet = new WordSet(); // Assuming WordSet has a default constructor
        this.numRounds = numRounds;

        // Additional setup code if needed
        usedWords = new HashSet<>();

        setupGameTimer();
    }

    private void setupGameTimer() {
        timer = new Timer();

        // Schedule the first round
        startNewRound();
    }

    private void startNewRound() {
        // Reset used words for a new round
        usedWords.clear();

        // Schedule a TimerTask with a random delay between 45 seconds and 1.5 minutes
        long delay = getRandomDelay();
        timer.schedule(new GameActivity.GameRoundTask(), delay);
    }

    private long getRandomDelay() {
        Random random = new Random();
        long minDelay = 45000; // 45 seconds in milliseconds
        long maxDelay = 90000; // 1.5 minutes in milliseconds
        return minDelay + random.nextInt((int) (maxDelay - minDelay + 1));
    }

    private void gameRound() {
        System.out.println("Starting a new round...");

        // Print a new word from the word list
        String newWord = getNewWord();
        System.out.println("Current word: " + newWord);

        /**
         * TEMPORARY USER INPUT - WILL REPLACE WITH BUTTON LOGIC
         */
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter when you figure out the word...");
        scanner.nextLine();

        // Switch to a NEW/UNUSED word for each user input
        if (!usedWords.contains(newWord)) {
            usedWords.add(newWord);
            System.out.println("User input received. Switching to new word...");
        }

        // Check if round over
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
        Scanner scanner = new Scanner(System.in);

        System.out.println("Round over!");
        System.out.println("Which team wins? Enter 'R' for Red team or 'B' for Blue team:");

        boolean validInput = false;
        while (!validInput) {

            /**
             * REPLACE WITH ACTUAL BUTTON INPUT
             */
            String winningTeam = scanner.nextLine();

            if (winningTeam.equals("R")) {
                incrementRedScore();
                validInput = true;
            } else if (winningTeam.equals("B")) {
                incrementBlueScore();
                validInput = true;
            } else {
                System.out.println("Error in user input, try again.");
                System.out.println("Which team wins? Enter 'R' for Red team or 'B' for Blue team:");
            }
        }

        // Check if rounds are completed
        if (--numRounds > 0) {
            startNewRound(); // Start next round if game unfinished
        } else {
            endGame(); // End the game if rounds done
        }
        scanner.close();
    }

    private void endGame() {
        determineWinningTeam();
        System.out.println("THANKS FOR PLAYING...");
    }

    private void determineWinningTeam() {
        // Your existing logic for determining the winning team
        if(redScore > blueScore)
        {
            winningTeam = 'R';
            System.out.println("RED TEAM WINS!!!");
        }
        else if(blueScore > redScore)
        {
            winningTeam = 'B';
            System.out.println("BLUE TEAM WINS!!!");
        }
        else
        {
            winningTeam = 'N';
            System.out.println("YOU BOTH LOSE!!! (THIS SHOULDN'T HAPPEN)");
        }
    }

    private void incrementRedScore() {
        redScore++;
    }

    private void incrementBlueScore() {
        blueScore++;
    }

    // Custom TimerTask class for the game round
    private class GameRoundTask extends TimerTask {
        @Override
        public void run() {
            gameRound();
        }
    }
}