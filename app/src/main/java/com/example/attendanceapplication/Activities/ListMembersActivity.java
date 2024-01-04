package com.example.attendanceapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.example.attendanceapplication.Model.Employee;
import com.example.attendanceapplication.Model.Employee.Status;
import com.example.attendanceapplication.Model.Employee.Gender;
import com.example.attendanceapplication.Model.Employee.Position;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ListMembersActivity extends AppCompatActivity {

    private ListView lvNhanVienList;
    private Button btnAddEmployee;
    private Toolbar toolbar;
    private User me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_members);
        me = User.getInstance(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnAddEmployee = findViewById(R.id.btnAddNhanVien);
        lvNhanVienList = findViewById(R.id.lvNhanVienList);
        lvNhanVienList.setAdapter(me.getStaffsAdapter());
        lvNhanVienList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Employee selectedStaff = me.getMyStaffs().get(position);
                showStaffDetail(selectedStaff, position);
            }
        });
        lvNhanVienList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Employee selectedStaff = me.getMyStaffs().get(i);
                Status status = selectedStaff.getStatus().equals(Status.work) ? Status.disabling : Status.firing;
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/status", status.name());
                me.getMyDBRef().child("users/"+selectedStaff.getAuthid()).updateChildren(childUpdates);
                return true;
            }

        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddEmployeeDialog();
            }
        });
    }

    private void showStaffDetail(final Employee staff, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_update_employee, null);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final EditText etcName, etcPhone;
        final TextView tvcID, tvcMail, tvcBirthday;
        final RadioGroup rdoGroGender, rdoGroStatus;
        final Button btnCancel, btnUpdate;

        etcName = dialogView.findViewById(R.id.etcName);
        etcPhone = dialogView.findViewById(R.id.etcPhone);
        tvcID = dialogView.findViewById(R.id.tvcID);
        tvcMail = dialogView.findViewById(R.id.tvcMail);
        tvcBirthday = dialogView.findViewById(R.id.tvcBirthday);
        rdoGroGender = dialogView.findViewById(R.id.rdoGroGender);
        rdoGroStatus = dialogView.findViewById(R.id.rdoGroStatus);
        btnCancel = dialogView.findViewById(R.id.btnCancel);
        btnUpdate = dialogView.findViewById(R.id.btnUpdate);

        tvcID.setText(staff.getId());
        tvcMail.setText(staff.getMail());
        etcName.setText(staff.getName());
        if (staff.getGender().equals(Gender.female)) {
            rdoGroGender.check(R.id.rdoFemale);
        }
        if (staff.getStatus().equals(Status.disable)) {
            rdoGroStatus.check(R.id.rdoDisable);
        }

        Calendar calendar = Calendar.getInstance();
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        tvcBirthday.setText(staff.getBirthday());
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                tvcBirthday.setText(sdf.format(calendar.getTime()));
            }
        };
        tvcBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ListMembersActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etcPhone.setText(staff.getPhone());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etcName.getText().toString().trim().equals("")) {
                    etcName.setError("Name is required!");
                    etcName.requestFocus();
                } else if (etcPhone.getText().toString().trim().equals("")) {
                    etcPhone.setError("Phone number is required!");
                    etcPhone.requestFocus();
                } else {
                    //gender
                    Gender gender;
                    if(rdoGroGender.getCheckedRadioButtonId() == R.id.rdoMale){
                        gender = Gender.male;
                    }else{
                        gender = Gender.female;
                    }
                    //Status
                    Status status = null;
                    if (rdoGroStatus.getCheckedRadioButtonId() == R.id.rdoEnable) {
                        status = (staff.getStatus() == Status.work) ? Status.work : Status.recovering;
                    } else {
                        status = (staff.getStatus() == Status.disable) ? Status.disable : Status.disabling;
                    }

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("name", etcName.getText().toString());
                    childUpdates.put("gender", gender.name());
                    childUpdates.put("birthday", tvcBirthday.getText().toString());
                    childUpdates.put("phone", etcPhone.getText().toString());
                    childUpdates.put("status", status.name());
                    me.getMyDBRef().child("users/"+staff.getAuthid()).updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                me.makeToast("Update staff's information successful.");
                            } else {
                                me.makeToast("Update staff's information failed.");
                            }
                        }
                    });

                    alertDialog.cancel();
                }
            }
        });
    }

    private void showAddEmployeeDialog() {
        // Tạo một AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        // Sử dụng LayoutInflater để inflate layout custom_dialog.xml
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_add_employee, null);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                me.resetTagidsAdapter();
            }
        });

        final EditText etcName, etcPhone;
        final TextView tvcID, tvcMail, tvcBirthday;
        final RadioGroup rdoGroGender, rdoGroPosition;
        final Spinner spnTagID;
        final Button btnCancel, btnAdd;

        etcName = dialogView.findViewById(R.id.etcName);
        etcPhone = dialogView.findViewById(R.id.etcPhone);
        tvcID = dialogView.findViewById(R.id.tvcID);
        tvcMail = dialogView.findViewById(R.id.tvcMail);
        tvcBirthday = dialogView.findViewById(R.id.tvcBirthday);
        rdoGroGender = dialogView.findViewById(R.id.rdoGroGender);
        rdoGroPosition = dialogView.findViewById(R.id.rdoGroPosition);
        spnTagID = dialogView.findViewById(R.id.spnTagID);
        btnCancel = dialogView.findViewById(R.id.btnCancel);
        btnAdd = dialogView.findViewById(R.id.btnAdd);

        String id = Integer.toString(me.getMaxID()+1);
        tvcID.setText(id);
        tvcMail.setText(id+"@nhom1.com");

        Calendar calendar = Calendar.getInstance();
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        tvcBirthday.setText(sdf.format(calendar.getTime()));
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                tvcBirthday.setText(sdf.format(calendar.getTime()));
            }
        };
        tvcBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ListMembersActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        spnTagID.setAdapter(me.getTagidsAdapter());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etcName.getText().toString().trim().equals("")) {
                    etcName.setError("Name is required!");
                    etcName.requestFocus();
                } else if (etcPhone.getText().toString().trim().equals("")) {
                    etcPhone.setError("Phone number is required!");
                    etcPhone.requestFocus();
                } else {
                    //gender
                    Gender gender = (rdoGroGender.getCheckedRadioButtonId() == R.id.rdoMale) ? Gender.male : Gender.female;
                    //position
                    Position position = (rdoGroPosition.getCheckedRadioButtonId() == R.id.rdoManager) ? Position.manager : Position.staff;

                    Map<String, Object> createRequest = new HashMap<>();
                    createRequest.put("id", tvcID.getText().toString());
                    createRequest.put("name", etcName.getText().toString());
                    createRequest.put("gender", gender.name());
                    createRequest.put("birthday", tvcBirthday.getText().toString());
                    createRequest.put("position", position.name());
                    createRequest.put("phone", etcPhone.getText().toString());
                    createRequest.put("status", Status.requesting.name());
                    createRequest.put("tagid", spnTagID.getSelectedItem().toString());

                    me.getMyDBRef().child("users/"+position+tvcID.getText()).setValue(createRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                me.getMyDBRef().child("users/"+position+tvcID.getText()+"/status").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.getValue().equals(Status.work.name())) {
                                            me.makeToast("Add employee successful.");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            } else {
                                me.makeToast("Add employee failed.");
                            }
                        }
                    });
                    alertDialog.cancel();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        me.resetStaffsAdapter();
    }
}