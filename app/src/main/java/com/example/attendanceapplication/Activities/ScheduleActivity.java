package com.example.attendanceapplication.Activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.attendanceapplication.Backgournd.ProfileFragmentUI;
import com.example.attendanceapplication.Backgournd.ScheduleActivityUI;
import com.example.attendanceapplication.EventDecorator;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.User;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private ArrayList<CalendarDay> highlightedDays;
    private ScheduleActivityUI update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        calendarView = findViewById(R.id.calendarView);
        highlightedDays = new ArrayList<>();

        update = new ScheduleActivityUI(this, calendarView, highlightedDays);
        update.execute();

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
        update.cancel(true);
    }
}