package com.example.attendanceapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendanceapplication.DataAccess.AppDatabase;
import com.example.attendanceapplication.Models.UserModel;
import com.example.attendanceapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private AppDatabase db;

    private Button btnLogin;
    private EditText etUserID;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = Room.databaseBuilder(this, AppDatabase.class, "manage_employee").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        btnLogin = findViewById(R.id.btnLogin);
        etUserID = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        // Kiểm tra user đã đăng nhập chưa, nếu rồi thì sẽ chuyển qua SecondActivity
//        checkIfUserIsLogged();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Đăng nhập
                Logging();
            }
        });
    }

//    private void checkIfUserIsLogged() {
//        if () {
//            startActivity(new Intent(MainActivity.this, SecondActivity.class));
//            finish();
//        }
//    }

    private void Logging() {
        String UserID = etUserID.getText().toString();
        String matkhau = etPassword.getText().toString().trim();

        // Kiểm tra email và pw đã được nhập hay chưa
        if (UserID.equals("") || matkhau.equals("")) {
            Toast.makeText(MainActivity.this, "UserID and Password cannot be empty.", Toast.LENGTH_SHORT).show();
        } else {
            UserModel userModel = db.userDao().Login(Integer.parseInt(UserID),matkhau);
            if (userModel == null) {
                startActivity(new Intent(MainActivity.this, AddRole.class));
            }
            else {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        }
    }
}