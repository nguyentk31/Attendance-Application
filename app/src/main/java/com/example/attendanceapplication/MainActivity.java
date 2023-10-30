package com.example.attendanceapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button btnLogin;
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        // Kiểm tra user đã đăng nhập chưa, nếu rồi thì sẽ chuyển qua SecondActivity
        checkIfUserIsLogged();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Đăng nhập
                Logging();
            }
        });
    }

    private void checkIfUserIsLogged() {
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
            finish();
        }
    }

    private void Logging() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Kiểm tra email và pw đã được nhập hay chưa
        if (email.equals("") || password.equals("")) {
            Toast.makeText(MainActivity.this, "Email and password cannot be empty.", Toast.LENGTH_SHORT).show();
        } else {
            // Đăng nhập
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, SecondActivity.class));
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Login Failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}