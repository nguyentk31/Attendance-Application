package com.example.attendanceapplication.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
        TextView textViewStatus = convertView.findViewById(R.id.textViewStatus);
        ImageView ivAvatar = convertView.findViewById(R.id.imageViewAvatar);
        ImageView ivCall = convertView.findViewById(R.id.ivCall);
        LinearLayout llParent = convertView.findViewById(R.id.llParent);

        textViewName.setText(employee.getName());
        textViewPosition.setText(employee.getPosition().name());
        textViewStatus.setText(employee.getStatus().name());

        if (employee.getAvatarURL() == null) {
            if (employee.getGender() == Employee.Gender.male)
                ivAvatar.setImageResource(R.drawable.avatar_male);
            else
                ivAvatar.setImageResource(R.drawable.avatar_female);
        } else {
            Picasso.get().load(employee.getAvatarURL()).into(ivAvatar);
        }

        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + employee.getPhone()));
                context.startActivity(callIntent);
            }
        });

        if(employee.getStatus() == Employee.Status.disable){
            llParent.setBackgroundResource(R.color.md_theme_light_error);
        } else if(employee.getStatus() == Employee.Status.work) {
            llParent.setBackgroundResource(R.color.white);
        }
        return convertView;
    }

}
