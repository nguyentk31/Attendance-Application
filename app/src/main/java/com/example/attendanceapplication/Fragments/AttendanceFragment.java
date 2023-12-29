package com.example.attendanceapplication.Fragments;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.attendanceapplication.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AttendanceFragment extends Fragment {

    private MaterialCalendarView calendarView;
    private List<CalendarDay> highlightedDays = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attendance, container, false);
        calendarView = view.findViewById(R.id.calendarView);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 25);
        highlightedDays.add(CalendarDay.from(calendar));

        calendar.set(2023, Calendar.DECEMBER, 31);
        highlightedDays.add(CalendarDay.from(calendar));
        EventDecorator eventDecorator = new EventDecorator(Color.RED, highlightedDays);
        calendarView.addDecorator(eventDecorator);
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
        return view;
    }

    private class EventDecorator implements DayViewDecorator {
        private final int color;
        private final Drawable highlightDrawable;
        private final List<CalendarDay> dates;

        public EventDecorator(int color, List<CalendarDay> dates) {
            this.color = color;
            this.highlightDrawable = ContextCompat.getDrawable(getActivity(), R.color.md_theme_light_primary);
            this.dates = dates;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(com.prolificinteractive.materialcalendarview.DayViewFacade view) {
            view.addSpan(new DotSpan(5, color));
            view.setBackgroundDrawable(highlightDrawable);
        }
    }

}
