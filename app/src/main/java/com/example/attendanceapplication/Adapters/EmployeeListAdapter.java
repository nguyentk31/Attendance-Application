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
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmployeeListAdapter extends ArrayAdapter<Employee> {
    private List<Employee> employeeList;
    private Context context;
    public EmployeeListAdapter(@NonNull Context context, int resource, @NonNull List<Employee> objects) {
        super(context, resource, objects);
        this.context = context;
        this.employeeList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_nhanvien, null, false);
        }
        Employee employee = getItem(position);

        TextView textViewEmail = convertView.findViewById(R.id.textViewEmail);
        TextView textViewPassword = convertView.findViewById(R.id.textViewPassword);
        ImageView ivAvatar = convertView.findViewById(R.id.imageViewAvatar);
        if(employee.getName()!=null){
            textViewEmail.setText(employee.getName());
        }
        if(employee.getGender()!=null){
            textViewPassword.setText(employee.getGender().name());
        }
        if (employee.getAvatarURL().equals("null")) {
            if (employee.getGender() == Employee.Gender.Male)
                ivAvatar.setImageResource(R.drawable.avatar_male);
            else
                ivAvatar.setImageResource(R.drawable.avatar_female);
        } else {
            Picasso.get().load(employee.getAvatarURL()).into(ivAvatar);
        }
        return convertView;
    }

}
