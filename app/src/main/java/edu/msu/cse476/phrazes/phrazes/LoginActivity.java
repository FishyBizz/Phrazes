package edu.msu.cse476.phrazes.phrazes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;
    private Button signInButton;
    private TextView txtSignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // Initialize views
        emailText = findViewById(R.id.signInEmail);
        passwordText = findViewById(R.id.signInPassword);
        signInButton = findViewById(R.id.btnSignIn);
        txtSignUp = findViewById(R.id.txtSignUp);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //THIS WAS KEEPING THE USER LOGGED ON EVEN WHEN USER WAS DISABLED
        //PROBABLY SHOULD DELETE
        // Check if user is already signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //User is signed in
            navigateToMainMenu();
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()) {
                    signIn(email, password); // Call signIn method with entered credentials
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Email or Password.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            navigateToMainMenu();
                        } else {
                            // Sign on fails
                            Toast.makeText(LoginActivity.this, "Authentication Failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void navigateToMainMenu() {
        Intent intent = new Intent(LoginActivity.this, MainMenu.class);
        startActivity(intent);
        finish(); // Call finish to remove this activity from the activity stack.
    }
}