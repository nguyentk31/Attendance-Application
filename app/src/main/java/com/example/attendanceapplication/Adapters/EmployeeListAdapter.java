package com.example.attendanceapplication.Adapters;

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

        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewPosition = convertView.findViewById(R.id.textViewPosition);
        TextView textViewGender = convertView.findViewById(R.id.textViewGender);
        ImageView ivAvatar = convertView.findViewById(R.id.imageViewAvatar);
        if(employee.getName()!=null){
            textViewName.setText(employee.getName());
        }
        if(employee.getPosition()!=null){
            if(employee.getPosition() == Employee.Position.Manager){
                textViewPosition.setText("Manager");
            }else{
                textViewPosition.setText("Staff");
            }
        }
        if(employee.getGender()!=null){
            if(employee.getGender() == Employee.Gender.Male){
                textViewGender.setText("Male");
            }else{
                textViewGender.setText("Female");
            }
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
