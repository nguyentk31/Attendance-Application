package com.example.attendanceapplication.Activities;


import android.os.Bundle;
import androidx.annotation.NonNull;
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
}