package com.example.attendanceapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.attendanceapplication.Model.NhanVien;
import com.example.attendanceapplication.R;

import java.util.List;

public class NhanVienListAdapter extends ArrayAdapter<NhanVien> {
    private List<NhanVien> ListNhanVien;
    private Activity context;
    public NhanVienListAdapter(@NonNull Activity context, int resource, @NonNull List<NhanVien> objects) {
        super(context, resource, objects);
        this.context = context;
        this.ListNhanVien = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_nhanvien, null, false);
        }
        NhanVien nv = getItem(position);

        TextView textViewEmail = convertView.findViewById(R.id.textViewEmail);
        TextView textViewPassword = convertView.findViewById(R.id.textViewPassword);
        if(nv.getEmail()!=null){
            textViewEmail.setText(nv.getEmail());
        }
        if(nv.getPassword()!=null){
            textViewPassword.setText(nv.getPassword());
        }
        return convertView;
    }
}
