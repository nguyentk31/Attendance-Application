package com.example.attendanceapplication.Backgournd;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.attendanceapplication.Activities.ScheduleActivity;
import com.example.attendanceapplication.EventDecorator;
import com.example.attendanceapplication.Model.Employee;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.User;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleActivityUI extends AsyncTask<String, Integer, Void> {

    private static final String TAG = "ScheduleUI";
    private User user;
    private HashMap<LocalDate, LocalTime> myAttendances;
    private Context context;
    private MaterialCalendarView calendarView;
    private ArrayList<CalendarDay> highlightedDays;

    public ScheduleActivityUI(Context context, MaterialCalendarView calendarView, ArrayList<CalendarDay> highlightedDays) {
        this.user = User.getInstance();
        this.myAttendances = user.getMyProfile().getAttendances();
        this.context = context;
        this.calendarView = calendarView;
        this.highlightedDays = highlightedDays;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (myAttendances != null) {
            for (LocalDate x : myAttendances.keySet()) {
                CalendarDay i = new CalendarDay(x.getYear(), x.getMonthValue()-1, x.getDayOfMonth());
                highlightedDays.add(i);
            }
            EventDecorator eventDecorator = new EventDecorator(Color.RED, context, highlightedDays);
            calendarView.addDecorator(eventDecorator);
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            while (true) {
                HashMap<LocalDate, LocalTime> x = user.getMyProfile().getAttendances();
                if (x != myAttendances) {
                    myAttendances = x;
                    publishProgress(0);
                }
                Thread.sleep(100);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        highlightedDays.clear();
        for (LocalDate x : myAttendances.keySet()) {
            CalendarDay i = new CalendarDay(x.getYear(), x.getMonthValue()-1, x.getDayOfMonth());
            highlightedDays.add(i);
        }
        EventDecorator eventDecorator = new EventDecorator(Color.RED, context, highlightedDays);
        calendarView.addDecorator(eventDecorator);
    }

}
