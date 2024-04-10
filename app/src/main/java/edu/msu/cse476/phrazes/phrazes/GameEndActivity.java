package edu.msu.cse476.phrazes.phrazes;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GameEndActivity extends Activity {

    private TextView winnerText;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_REQUEST = 2;

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

    public void onStartPicture(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
        } else {
            startCameraIntent();
        }
    }

    private void startCameraIntent() {
        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // CAMERA permission granted, start the camera intent
                startCameraIntent();
            } else {
                Toast.makeText(this, "Permission required to take a picture. Enable in Device's settings.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Picture was taken successfully, save it to the device's gallery
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                saveImageToGallery(imageBitmap);
            }
            // Picture was taken successfully, return back to the main menu
            Intent returnIntent = new Intent(GameEndActivity.this, MainMenu.class);
            startActivity(returnIntent);
        }
    }

    private void saveImageToGallery(Bitmap imageBitmap) {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        File myDir = new File(root + "/Phrazes");
        myDir.mkdirs();
        String fileName = "Phraze_" + System.currentTimeMillis() + ".jpg";
        File file = new File(myDir, fileName);
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            // Tell the media scanner about the new file so that it is immediately available to the user.
            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
            Toast.makeText(this, "Image saved to Gallery", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }
}
