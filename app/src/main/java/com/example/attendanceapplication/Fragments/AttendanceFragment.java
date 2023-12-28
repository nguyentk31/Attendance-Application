package com.example.attendanceapplication.Fragments;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.attendanceapplication.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AttendanceFragment extends Fragment {

    private CalendarView simpleCalendarView;
    private List<Long> selectedDates;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);

        simpleCalendarView = view.findViewById(R.id.calendarView);
        Button selectDatesButton = view.findViewById(R.id.selectDatesButton);

        selectedDates = new ArrayList<>();

        simpleCalendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth);
            long milliseconds = selectedDate.getTimeInMillis();

            if (!selectedDates.contains(milliseconds)) {
                selectedDates.add(milliseconds);
            }
        });

        selectDatesButton.setOnClickListener(v -> showDatePickerDialog());

        return view;
    }

    private void showDatePickerDialog() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year1, monthOfYear, dayOfMonth);
                    long milliseconds = selectedDate.getTimeInMillis();

                    if (!selectedDates.contains(milliseconds)) {
                        selectedDates.add(milliseconds);
                    }

                    // Handle selectedDates list as needed
                    // For example, display selected dates or perform further actions

                    Toast.makeText(requireContext(), "Selected Dates: " + selectedDates, Toast.LENGTH_SHORT).show();
                },
                year, month, day);

        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setSpinnersShown(true);
        datePickerDialog.show();
    }
}
