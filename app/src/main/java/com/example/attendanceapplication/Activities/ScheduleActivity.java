package com.example.attendanceapplication.Activities;


import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.User;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

public class ScheduleActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private User me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        me = User.getInstance();

        calendarView = findViewById(R.id.calendarView);

        me.setCalendarView(this, calendarView);

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(
                    @NonNull MaterialCalendarView widget,
                    @NonNull CalendarDay date,
                    boolean selected
            ) {
                if (calendarView.getSelectedDates().contains(date)) {
                    // Ngày đã được highlight, hiển thị modal có 2 TextView
                    showCustomModal(date);
                } else {
                    // Ngày không được highlight, hiển thị thông báo
                    showNotification("Ngày này nghỉ hoặc không đi làm");
                }
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        me.resetCalendarView();
    }
    private void showCustomModal(CalendarDay date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông tin ngày");
        builder.setMessage("Ngày đã chọn: " + date.getDate());
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    private void showNotification(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}