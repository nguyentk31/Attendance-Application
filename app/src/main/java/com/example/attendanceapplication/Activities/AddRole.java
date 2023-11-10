package com.example.attendanceapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendanceapplication.DataAccess.AppDatabase;
import com.example.attendanceapplication.Models.RoleModel;
import com.example.attendanceapplication.R;

public class AddRole extends AppCompatActivity {
    private EditText roleName ;
    private Button createRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_role);
        AppDatabase db = Room.databaseBuilder(this,
                AppDatabase.class, "manage_employeer").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        roleName = findViewById(R.id.roleName);
        createRole = findViewById(R.id.buttonCreateRole);
        String roleValue = roleName.getText().toString();
        createRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roleValue.trim() == "")
                {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập tên vai trò", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    RoleModel roleModel = new RoleModel();
                    roleModel.TenVaiTro = roleValue;
                    db.roleDao().insertEach(roleModel);
                }
            }
        });

    }
}