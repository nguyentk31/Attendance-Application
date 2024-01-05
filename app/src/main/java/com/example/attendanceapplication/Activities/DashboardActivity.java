package com.example.attendanceapplication.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.attendanceapplication.Adapters.DetailDayAdapter;
import com.example.attendanceapplication.Model.Employee;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.User;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    private BarChart barChart;
    private MaterialCalendarView calendarView;
    private TextView tvTotal;
    private LocalTime timeCheck;
    private DetailDayAdapter adapter;
    private Toolbar toolbar;
    private User me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        me = User.getInstance(this);

        barChart = findViewById(R.id.barChart);
        calendarView = findViewById(R.id.calendar);
        tvTotal = findViewById(R.id.tvTotal);
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            timeCheck = LocalTime.of(7, 30);
        }

        tvTotal.setText("Total number of staffs: " + me.getMyStaffs().size());

        setBarChart();

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate selectedDate = LocalDate.of(date.getYear(), date.getMonth()+1, date.getDay());
                    showDayDetail(selectedDate);
                }
            }
        });

    }

    private void showDayDetail(LocalDate selectedDate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_detail_day, null);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final ListView listView;
        final Button btnOK;

        listView = dialogView.findViewById(R.id.listview);
        btnOK = dialogView.findViewById(R.id.btnOK);

        ArrayList<Employee> list = new ArrayList<>();
        for (Employee x : me.getMyStaffs()) {
            if (x.getAttendances() != null && x.getAttendances().containsKey(selectedDate)) {
                list.add(x);
            }
        }

        adapter = new DetailDayAdapter(this, R.layout.list_item_datedetail, list, selectedDate);
        listView.setAdapter(adapter);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

    }

    private void setBarChart() {
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new DayAxisValueFormatter());

        BarData data = getBarData();

        xAxis.setAxisMinimum(data.getXMin());
        xAxis.setAxisMaximum(data.getXMax()+0.95f);
        leftAxis.setAxisMaximum(data.getYMax()+5);

        barChart.setData(data);
        barChart.invalidate();
    }

    private BarData getBarData() {
        int firstX = 0;
        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate now = LocalDate.now();
            LocalDate then = now.minusDays(6);
            firstX = then.getDayOfWeek().getValue()-1;
            for (int index = 0; index < 7; index++) {
                int[] c = countStaffs(then.plusDays(index));
                entries1.add(new BarEntry(firstX+index, c[0]));
                entries2.add(new BarEntry(firstX+index, c[1]));
            }
        }

        BarDataSet set1 = new BarDataSet(entries1, "Working Staffs");
        set1.setColor(R.color.teal);
        set1.setValueTextColor(R.color.black);
        set1.setValueTextSize(10f);
        set1.setValueFormatter(new DefaultValueFormatter(0));
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet set2 = new BarDataSet(entries2, "On time Staffs");
        set2.setColor(R.color.green);
        set2.setValueTextColor(R.color.black);
        set2.setValueTextSize(10f);
        set2.setValueFormatter(new DefaultValueFormatter(0));
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set1, set2);
        d.setBarWidth(barWidth);

        // make this BarData object grouped
        d.groupBars(firstX, groupSpace, barSpace); // start at x = 0

        return d;
    }

    private static class DayAxisValueFormatter extends ValueFormatter {
        private String[] mdays = new String[] {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            // Convert the float value to the corresponding day label
            return mdays[(int) value % mdays.length];
        }
    }

    private int[] countStaffs(LocalDate d) {
        int total, intime;
        total = intime = 0;

        for (Employee x : me.getMyStaffs()) {
            if (x.getAttendances() != null && x.getAttendances().containsKey(d)) {
                total++;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (x.getAttendances().get(d).isBefore(timeCheck)) {
                        intime++;
                    }
                }
            }
        }
        return new int[] {total, intime};
    }
}
