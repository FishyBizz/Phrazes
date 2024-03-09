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

public class SignUpActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private Button signUpButton;
    private EditText signUpName;
    private FirebaseAuth mAuth;
    TextView txtSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        emailText = findViewById(R.id.signUpEmail);
        passwordText = findViewById(R.id.signUpPassword);
        confirmPasswordText = findViewById(R.id.signUpConfirmPass);
        signUpButton = findViewById(R.id.btnSignUp);
        txtSignIn = findViewById(R.id.txtSignIn);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                String confirmPassword = confirmPasswordText.getText().toString().trim();

                // Check if any field is empty
                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "All fields are required.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the entered passwords match
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Proceed with the registration process
                createAccount(email, password);
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Success
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignUpActivity.this, "Registration" +
                                    "successful.", Toast.LENGTH_SHORT).show();
                            navigateToLoginActivity();
                        } else {
                            // Sign up fails
                            Toast.makeText(SignUpActivity.this, "Registration failed! " +
                                    "Please try again!", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    private void navigateToLoginActivity() {
        Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
}