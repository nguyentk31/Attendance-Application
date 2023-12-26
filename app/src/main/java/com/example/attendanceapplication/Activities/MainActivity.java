package com.example.attendanceapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendanceapplication.R;
import com.example.attendanceapplication.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button btnLogin;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvForgotPW;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvForgotPW = findViewById(R.id.tvForgotPassword);
        progressBar = findViewById(R.id.progressBar);

        // Check login
        checkIfUserIsLogged();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Check null email, password
                if (email.equals("") || password.equals("")) {
                    etEmail.setError("Email is required!");
                    etEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError("Valid email is required!");
                    etEmail.requestFocus();
                } else {
                    btnLogin.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    // login
                    Logging(email, password);
                }
            }
        });

        tvForgotPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
            }
        });
    }

    private void checkIfUserIsLogged() {
        if (mAuth.getCurrentUser() != null) {
            User.getInstance();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
            finish();
        }
    }

    private void Logging(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User.getInstance();
                    Toast.makeText(MainActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                    finish();
                } else {
                    btnLogin.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Login Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}