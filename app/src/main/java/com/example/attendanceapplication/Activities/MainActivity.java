package com.example.attendanceapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
    static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("INSERT INTO VaiTro VALUES(2,'Admin')");
            database.execSQL("INSERT INTO PhongBan VALUES(2,'Phòng Giám Đốc')");
            database.execSQL("INSERT INTO NhanVien VALUES(2,'nguyen1234' , 'Tran Khoi Nguyen',1,2,2)");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = Room.databaseBuilder(this, AppDatabase.class, "manage_employee").addMigrations(MIGRATION_5_6).fallbackToDestructiveMigration().allowMainThreadQueries().build();

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

    public void Logging() {
        String UserID = etUserID.getText().toString();
        String matkhau = etPassword.getText().toString().trim();

        // Kiểm tra email và pw đã được nhập hay chưa
        if (UserID.equals("") || matkhau.equals("")) {
            Toast.makeText(MainActivity.this, "UserID and Password cannot be empty.", Toast.LENGTH_SHORT).show();
        } else {
            UserModel userModel = db.userDao().Login(Integer.parseInt(UserID),matkhau);
            if (userModel == null) {
                Toast.makeText(MainActivity.this, "UserID and Password cannot be empty.", Toast.LENGTH_SHORT).show();
            }
            else {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        }
    }
}