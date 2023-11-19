package com.example.attendanceapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.attendanceapplication.Model.Employee;
import com.example.attendanceapplication.R;

import java.util.List;

public class EmployeeListAdapter extends ArrayAdapter<Employee> {
    private List<Employee> employeeList;
    private Activity context;
    public EmployeeListAdapter(@NonNull Context context, int resource, @NonNull List<Employee> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.employee_item, null, false);
        }
        Employee employee = getItem(position);
        ImageView employeeAvatar = convertView.findViewById(R.id.employee_item_avatar);
        TextView employeeName = convertView.findViewById(R.id.employee_item_name);
        TextView employeeID = convertView.findViewById(R.id.employee_item_ID);
        TextView employeePosition = convertView.findViewById(R.id.employee_item_position);
        if(employee.getName()!= null)
            employeeName.setText(employee.getName());
        if(employee.getID()!= null)
            employeeID.setText(employee.getID());
        if(employee.getPosition()!= null)
            employeePosition.setText(employee.getPosition());
//        if(employee.getAvatar()!= 0)
//            employeeAvatar.setImageResource(employee.getAvatar());
        return convertView;
    }

}
