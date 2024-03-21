package edu.msu.cse476.phrazes.phrazes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void onStartGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void onStartDownload_Categories(View view) {
        Intent intent = new Intent(this, DownloadCategoriesActivity.class);
        startActivity(intent);
    }

    public void onStartCreateCategory(View view) {
        Intent intent = new Intent(this, CreateCategoryActivity.class);
        startActivity(intent);
    }

    public void onStartHowToPlay(View view) {
        Intent intent = new Intent(this, HowToPlayActivity.class);
        startActivity(intent);
    }

    public void onStartSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}