package com.example.attendanceapplication.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.attendanceapplication.Model.Employee;
import com.example.attendanceapplication.R;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

public class DetailDayAdapter extends ArrayAdapter<Employee> {
    private List<Employee> employeeList;
    private LocalDate selectedDate;
    private Context context;
    public DetailDayAdapter(@NonNull Context context, int resource, @NonNull List<Employee> objects, LocalDate selectedDate) {
        super(context, resource, objects);
        this.context = context;
        this.employeeList = objects;
        this.selectedDate = selectedDate;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_datedetail, null, false);
        }
        Employee employee = getItem(position);

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvID = convertView.findViewById(R.id.tvID);
        TextView tvCheckin = convertView.findViewById(R.id.tvCheckin);
        ImageView ivAvatar = convertView.findViewById(R.id.imageViewAvatar);
        LinearLayout llParent = convertView.findViewById(R.id.llParent);

        tvName.setText(employee.getName());
        tvID.setText(employee.getId());

        if (employee.getAvatarURL() == null) {
            if (employee.getGender() == Employee.Gender.male)
                ivAvatar.setImageResource(R.drawable.avatar_male);
            else
                ivAvatar.setImageResource(R.drawable.avatar_female);
        } else {
            Picasso.get().load(employee.getAvatarURL()).into(ivAvatar);
        }

        LocalTime x = employee.getAttendances().get(selectedDate);
        tvCheckin.setText(x.toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(x.isAfter(LocalTime.of(7,30))){
                llParent.setBackgroundResource(R.color.md_theme_light_error);
            } else if(employee.getStatus() == Employee.Status.work) {
                llParent.setBackgroundResource(R.color.white);
            }
        }
        return convertView;
    }

}
