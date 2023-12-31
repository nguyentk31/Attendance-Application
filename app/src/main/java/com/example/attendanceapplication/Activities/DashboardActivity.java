package com.example.attendanceapplication.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceapplication.Model.Employee;
import com.example.attendanceapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;
    private Calendar calendar;
    private Date currentDate , endDate;
    private Date[] weekArray;
    private void updateDateRangePrevious() {
        for (int i = 0; i < 7; i++) {
            weekArray[i] = calendar.getTime();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        }
        getBarEntries(weekArray);

    }
    private void updateDateRangeNext() {
        for (int i = 0; i < 7; i++) {
            weekArray[i] = calendar.getTime();
            calendar.add(Calendar.DAY_OF_YEAR, +1);
        }
        getBarEntries(weekArray);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        calendar = Calendar.getInstance();
        weekArray = new Date[7];
        for (int i = 0; i < 7; i++) {
            weekArray[i] = calendar.getTime();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        }
        getBarEntries(weekArray);

        barChart = findViewById(R.id.idBarChart);

        Button previousButton = findViewById(R.id.btnPrevious);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDateRangePrevious();
            }
        });

        Button nextButton = findViewById(R.id.btnNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDateRangeNext();
            }
        });

        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "Nhân viên đi làm theo từng tháng");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // set the granularity to 1 to display all labels
        xAxis.setValueFormatter(new DayAxisValueFormatter()); // set custom formatter

        // setting up the Y-axis
        YAxis yAxis = barChart.getAxisLeft(); // You can also use getAxisRight() for the right Y-axis
        yAxis.setAxisMinimum(0f); // Set the minimum value for Y-axis
        TableLayout tableLayout = findViewById(R.id.tableLayout);

// Create a list of player data (you can replace this with your dynamic data)
        TableRow tableRow = new TableRow(this);

        // Create TextViews for each column and set their properties
        TextView rankTextView = createTextView("A");
        TextView playerTextView = createTextView("B");
        TextView teamTextView = createTextView("C");
        TextView pointsTextView = createTextView("D");

        // Add TextViews to the TableRow
        tableRow.addView(rankTextView);
        tableRow.addView(playerTextView);
        tableRow.addView(teamTextView);
        tableRow.addView(pointsTextView);

        // Set background and padding for the TableRow
        tableRow.setBackgroundColor(Color.parseColor("#F0F7F7"));
        tableRow.setPadding(5, 5, 5, 5);

        // Add the TableRow to the TableLayout
        tableLayout.addView(tableRow);
    }
    private static class DayAxisValueFormatter extends ValueFormatter {
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            // Convert the float value to the corresponding day label
            return "Day " + ((int) value);
        }
    }

    private void getBarEntries(Date[] days) {
        barEntriesArrayList = new ArrayList<>();
        for (int i = 1; i <= days.length; i++) {
            barEntriesArrayList.add(new BarEntry((float) i, i));
        }
    }
    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1f));
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

}
