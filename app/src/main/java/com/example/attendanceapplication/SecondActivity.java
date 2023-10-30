package com.example.attendanceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SecondActivity extends AppCompatActivity {

    private String adminEmail;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private Button btnCreateUser;
    private Button btnLogout;
    private TextView tvMsg;
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        adminEmail = "admin@gm.com";
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        btnCreateUser = findViewById(R.id.btnCreateUser);
        btnLogout = findViewById(R.id.btnLogout);
        tvMsg = findViewById(R.id.tvMsg);
        etEmail = findViewById(R.id.etEmail1);
        etPassword = findViewById(R.id.etPassword1);

        if (user != null) {
            String mail = user.getEmail();
            tvMsg.setText(String.format("Hello %s", mail));

            // Nếu không phải tài khoản của admin thì sẽ không hiện phần đăng kí user
            if (!mail.equals(adminEmail)) {
                etEmail.setVisibility(View.GONE);
                etPassword.setVisibility(View.GONE);
                btnCreateUser.setVisibility(View.GONE);
            }
        }

        // Đăng xuất
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(SecondActivity.this, MainActivity.class));
                finish();
                Toast.makeText(SecondActivity.this, "Logout Successful.", Toast.LENGTH_SHORT).show();
            }
        });

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Kiểm tra email và pw đã được nhập hay chưa
                if (email.equals("") || password.equals("")) {
                    Toast.makeText(SecondActivity.this, "Email and password cannot be empty.", Toast.LENGTH_SHORT).show();
                } else {
                    // Đăng ký user
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                etEmail.getText().clear();
                                etPassword.getText().clear();
                                Toast.makeText(SecondActivity.this, "Create user successful.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SecondActivity.this, "Create user Failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
