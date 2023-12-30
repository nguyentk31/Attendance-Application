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
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.attendanceapplication.Model.Employee;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
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

    private void showNhanVienDetailDialog(final Employee person, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông Tin Nhân Viên");

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);

        final EditText etID = dialogView.findViewById(R.id.etID);
        final EditText etTagID = dialogView.findViewById(R.id.etTagID);
        final EditText etName = dialogView.findViewById(R.id.etName);
        final EditText etBirthday = dialogView.findViewById(R.id.etBirthday);
        final RadioGroup rdoGroGender = dialogView.findViewById(R.id.rdoGroGender);
        final RadioGroup rdoGroPosition = dialogView.findViewById(R.id.rdoGroPosition);
        final EditText etEmail = dialogView.findViewById(R.id.etEmail);

        //final RadioGroup rdoGroStatus = dialogView.findViewById(R.id.rdoGroStatus);
        //final EditText etPassword = dialogView.findViewById(R.id.etPassword);

        etID.setText(person.getId());
        etTagID.setText(person.getTagId());
        etName.setText(person.getName());
        etBirthday.setText(person.getBirthday().toString());
        etEmail.setText(person.getId()+"@nhom1.com");
        if(person.getPosition() == Employee.Position.Manager){
            rdoGroPosition.check(R.id.rdoManager);
        }else{
            rdoGroPosition.check(R.id.rdoStaff);
        }
        if(person.getGender()== Employee.Gender.Male){
            rdoGroGender.check(R.id.rdoMale);
        }else{
            rdoGroGender.check(R.id.rdoFemale);
        }

        etID.setEnabled(false);
        etEmail.setEnabled(false);

        builder.setPositiveButton("Cập Nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //tagID
                String tagId = etTagID.getText().toString();
                //name
                String name = etName.getText().toString();
                //gender
                Employee.Gender gender;
                if(rdoGroGender.getCheckedRadioButtonId() == R.id.rdoMale){
                    gender = Employee.Gender.Male;
                }else{
                    gender = Employee.Gender.Female;
                }
                //birthday
                String birthday = etBirthday.getText().toString();
                //position
                Employee.Position position;
                if(rdoGroPosition.getCheckedRadioButtonId() == R.id.rdoManager){
                    position = Employee.Position.Manager;
                }else{
                    position = Employee.Position.Staff;
                }
                //email
                String email = etEmail.getText().toString();


                //password
//                String password = etPassword.getText().toString();
                //status
//                Employee.Status status;
//                if(rdoGroStatus.getCheckedRadioButtonId() == R.id.rdoWorking){
//                    status = Employee.Status.Working;
//                }else{
//                    status = Employee.Status.Fired;
//                }

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/tagId", tagId);
                childUpdates.put("/name", name);
                childUpdates.put("/gender", gender);
                childUpdates.put("/birthday", birthday);
                childUpdates.put("/position", position);
                //childUpdates.put("/status", status);

                me.getMyDBRef().child("Users/"+person.getAuthId()).updateChildren(childUpdates);
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

        final EditText etID = dialogView.findViewById(R.id.etID);
        final EditText etTagID = dialogView.findViewById(R.id.etTagID);
        final EditText etName = dialogView.findViewById(R.id.etName);
        final EditText etBirthday = dialogView.findViewById(R.id.etBirthday);
        final RadioGroup rdoGroGender = dialogView.findViewById(R.id.rdoGroGender);
        final RadioGroup rdoGroPosition = dialogView.findViewById(R.id.rdoGroPosition);
        final EditText etEmail = dialogView.findViewById(R.id.etEmail);

        //final EditText etPassword = dialogView.findViewById(R.id.etPassword);
        //final RadioGroup rdoGroStatus = dialogView.findViewById(R.id.rdoGroStatus);
        etID.setEnabled(false);
        etEmail.setEnabled(false);

        ArrayList<Employee> EmployeeList = me.getMySubordinates();
        Employee lastEmployee = EmployeeList.get(EmployeeList.size()-1);
        String ID = Integer.toString(Integer.parseInt(lastEmployee.getId())+1);
        etID.setText(ID);

        String email = ID+"@nhom1.com";
        etEmail.setText(email);
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Lấy giá trị từ EditText và thêm nhân viên mới vào danh sách
                //tagId
                String tagId = etTagID.getText().toString();
                //name
                String name = etName.getText().toString();
                //gender
                Employee.Gender gender;
                if(rdoGroGender.getCheckedRadioButtonId() == R.id.rdoMale){
                    gender = Employee.Gender.Male;
                }else{
                    gender = Employee.Gender.Female;
                }
                //birthday
                String birthday = etBirthday.getText().toString();
                //position
                Employee.Position position;
                if(rdoGroPosition.getCheckedRadioButtonId() == R.id.rdoManager){
                    position = Employee.Position.Manager;
                }else{
                    position = Employee.Position.Staff;
                }
                //status
//                Employee.Status status;
//                if(rdoGroStatus.getCheckedRadioButtonId() == R.id.rdoWorking){
//                    status = Employee.Status.Working;
//                }else{
//                    status = Employee.Status.Fired;
//                }

                //password
                //String password = etPassword.getText().toString();


                Map<String, Object> createRequest = new HashMap<>();
                createRequest.put("id", ID);
                createRequest.put("tagId", tagId);
                createRequest.put("name", name);
                createRequest.put("gender", gender);
                createRequest.put("birthday", birthday);
                createRequest.put("position", position);
                //createRequest.put("status", status);

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