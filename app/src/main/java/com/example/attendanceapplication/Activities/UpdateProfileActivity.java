package com.example.attendanceapplication.Activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.attendanceapplication.Model.Employee;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private User me;
    Toolbar toolbar;
    EditText etcName, etcPhone;
    TextView tvcID, tvcMail, tvcBirthday;
    RadioGroup rdoGroGender;
    Button btnCancel, btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update_profile);
        me = User.getInstance(this);
        Employee profile = me.getMyProfile();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etcName = findViewById(R.id.etcName);
        etcPhone = findViewById(R.id.etcPhone);
        tvcID = findViewById(R.id.tvcID);
        tvcMail = findViewById(R.id.tvcMail);
        tvcBirthday = findViewById(R.id.tvcBirthday);
        rdoGroGender = findViewById(R.id.rdoGroGender);
        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnUpdate);

        tvcID.setText(profile.getId());
        tvcMail.setText(profile.getMail());
        etcName.setText(profile.getName());
        if (profile.getGender().equals(Employee.Gender.female)) {
            rdoGroGender.check(R.id.rdoFemale);
        }

        Calendar calendar = Calendar.getInstance();
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        tvcBirthday.setText(profile.getBirthday());
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
                new DatePickerDialog(UpdateProfileActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etcPhone.setText(profile.getPhone());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                    Employee.Gender gender;
                    if(rdoGroGender.getCheckedRadioButtonId() == R.id.rdoMale){
                        gender = Employee.Gender.male;
                    }else{
                        gender = Employee.Gender.female;
                    }

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("name", etcName.getText().toString());
                    childUpdates.put("gender", gender.name());
                    childUpdates.put("birthday", tvcBirthday.getText().toString());
                    childUpdates.put("phone", etcPhone.getText().toString());
                    me.getMyDBRef().child("users/"+profile.getAuthid()).updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                me.makeToast("Update profile successful.");
                            } else {
                                me.makeToast("Update profile failed.");
                            }
                        }
                    });
                    finish();
                }
            }
        });

    }
}