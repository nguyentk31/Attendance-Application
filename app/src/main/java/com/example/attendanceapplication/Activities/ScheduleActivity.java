package com.example.attendanceapplication.Activities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.attendanceapplication.Model.Employee;
import com.example.attendanceapplication.Model.Event;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ScheduleActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private TextView tvNote;
    private Toolbar toolbar;
    private Button btnAddEvent;
    private User me;
    private ArrayList<String> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        me = User.getInstance(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvNote = findViewById(R.id.tvNote);
        btnAddEvent = findViewById(R.id.btnAddEvent);
        calendarView = findViewById(R.id.calendarView);
        me.setCalendarView(calendarView);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @SuppressLint("NewApi")
            @Override
            public void onDateSelected(
                    @NonNull MaterialCalendarView widget,
                    @NonNull CalendarDay date,
                    boolean selected
            ) {
                HashMap<LocalDate, LocalTime> attendances = me.getMyProfile().getAttendances();
                LocalDate selectedDate = LocalDate.of(date.getYear(), date.getMonth()+1, date.getDay());
                if (attendances.containsKey(selectedDate)) {
                    tvNote.setText("Check in at: " + attendances.get(selectedDate));
                }

                for (Event x : me.getEvents()) {
                    if (x.getDate().equals(selectedDate)) {
                        tvNote.setText(x.getEvent().name() + " from " + x.getFrom() + "\n" + x.getNote());
                    }
                }

                if (tvNote.getText().toString().trim().equals("")) {
                    tvNote.setText("Nothing");
                }

                widget.setDateSelected(date, false);
            }
        });
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(
                    @NonNull MaterialCalendarView widget,
                    @NonNull CalendarDay date
            ) {
            }
        });

        if (me.getMyProfile().getPosition().equals(Employee.Position.manager)) {
            events = new ArrayList<>();
            events.add("holiday");
            events.add("meeting");
            btnAddEvent.setVisibility(View.VISIBLE);
            btnAddEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAddEventDialog();
                }
            });
        }
    }

    private void showAddEventDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_add_event, null);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final Spinner spnEvent;
        final TextView tvDay;
        final EditText etNote;
        final Button btnAdd;

        spnEvent = dialogView.findViewById(R.id.spnEvent);
        tvDay = dialogView.findViewById(R.id.tvDay);
        etNote = dialogView.findViewById(R.id.etNote);
        btnAdd = dialogView.findViewById(R.id.btnAdd);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(ScheduleActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, events);
        spnEvent.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance();
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        tvDay.setText(sdf.format(calendar.getTime()));

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                tvDay.setText(sdf.format(calendar.getTime()));
            }
        };
        tvDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ScheduleActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                String event = spnEvent.getSelectedItem().toString();
                String day = sdf.format(calendar.getTime());
                String note = etNote.getText().toString().trim();

                Map<String, Object> createRequest = new HashMap<>();
                createRequest.put("date", day);
                createRequest.put("event", event);
                createRequest.put("note", note);
                createRequest.put("from", me.getMyProfile().getAuthid());
                createRequest.put("status", "requesting");

                me.getMyDBRef().child("calendar").push().setValue(createRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            me.makeToast("Add event successful");
                        } else {
                            me.makeToast("Add event failed");
                        }
                    }
                });

                alertDialog.cancel();
            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        me.resetCalendarView();
    }
}