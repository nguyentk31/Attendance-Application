package com.example.attendanceapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.attendanceapplication.Model.Employee;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.HashMap;
import java.util.Map;

public class ListMembersActivity extends AppCompatActivity {

    private ListView lvNhanVienList;
    private User me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_members);

        me = User.getInstance();
        lvNhanVienList = findViewById(R.id.lvNhanVienList);

        lvNhanVienList.setAdapter(me.getMySubordinatesAdapter(this));

        Button btnAddNhanVien = findViewById(R.id.btnAddNhanVien);
        lvNhanVienList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Employee selectedStaff = me.getMySubordinates().get(position);
                showNhanVienDetailDialog(selectedStaff, position);
            }
        });
        btnAddNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNhanVienDialog();
            }
        });
    }

    private void showNhanVienDetailDialog(final Employee staff, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông Tin Nhân Viên");

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);

        final EditText etEmail = dialogView.findViewById(R.id.etEmail);
        final EditText etPassword = dialogView.findViewById(R.id.etPassword);
        etEmail.setText(staff.getName());
        etPassword.setText(staff.getGender().name());

        etEmail.setEnabled(true);
        etPassword.setEnabled(true);

        builder.setPositiveButton("Cập Nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String updatedEmail = etEmail.getText().toString();
                String updatedPassword = etPassword.getText().toString();


                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/name", "nguyen");
                childUpdates.put("/gender", Employee.Gender.Male);
                childUpdates.put("/birthday", "2003-01-31");
                childUpdates.put("/position", Employee.Position.Manager);
                childUpdates.put("/status", Employee.Status.Working);

                me.getMyDBRef().child("Users/"+staff.getAuthId()).updateChildren(childUpdates);
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showAddNhanVienDialog() {
        // Tạo một AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm Nhân Viên");

        // Sử dụng LayoutInflater để inflate layout custom_dialog.xml
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);

        final EditText etEmail = dialogView.findViewById(R.id.etEmail);
        final EditText etPassword = dialogView.findViewById(R.id.etPassword);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Lấy giá trị từ EditText và thêm nhân viên mới vào danh sách
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();


                Map<String, Object> createRequest = new HashMap<>();
                createRequest.put("name", "nguyen");
                createRequest.put("gender", Employee.Gender.Male);
                createRequest.put("birthday", "2003-01-31");
                createRequest.put("position", Employee.Position.Manager);
                createRequest.put("status", Employee.Status.Working);

                me.getMyDBRef().child("Request").push().getRef().setValue(createRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            Toast.makeText(ListMembersActivity.this, "Request successful.", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(ListMembersActivity.this, "Request failed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        me.resetMySubordinatesAdapter();
    }
}