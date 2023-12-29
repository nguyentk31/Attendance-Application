package com.example.attendanceapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.attendanceapplication.Adapters.NhanVienListAdapter;
import com.example.attendanceapplication.Model.NhanVien;

public class ListMembers extends AppCompatActivity {

    private ListView lvNhanVienList;
    private ArrayList<NhanVien> nhanVienList;
    private ArrayAdapter<NhanVien> nvAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_members);

        lvNhanVienList = findViewById(R.id.lvNhanVienList);
        nhanVienList = new ArrayList<NhanVien>();
        nvAdapter = new NhanVienListAdapter(this,
                R.layout.list_item_nhanvien,
                nhanVienList);

        lvNhanVienList.setAdapter(nvAdapter);
        nhanVienList.add(new NhanVien("nhanvien1@gmail.com", "password1"));
        nhanVienList.add(new NhanVien("nhanvien2@gmail.com", "password2"));
        nhanVienList.add(new NhanVien("nhanvien3@gmail.com", "password3"));
        nvAdapter.notifyDataSetChanged();
        //updateUI();

        Button btnAddNhanVien = findViewById(R.id.btnAddNhanVien);
        lvNhanVienList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NhanVien selectedNhanVien = nhanVienList.get(position);
                showNhanVienDetailDialog(selectedNhanVien, position);
                //Intent intent = new Intent(ListMembers.this, UpdateMemberActivity.class);
                //intent.putExtra("selectedNhanVien", selectedNhanVien);
                //startActivityForResult(intent, 1);
            }
        });
        btnAddNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNhanVienDialog();
            }
        });
    }

//    private void updateUI() {
//        lvNhanVienList.removeAllViews();
//
//        for (final NhanVien nhanVien : nhanVienList) {
//            View itemView = getLayoutInflater().inflate(R.layout.list_item_nhanvien, null);
//
//            TextView textViewEmail = itemView.findViewById(R.id.textViewEmail);
//            TextView textViewPassword = itemView.findViewById(R.id.textViewPassword);
//
//            textViewEmail.setText("Email: " + nhanVien.getEmail());
//            textViewPassword.setText("Password: " + nhanVien.getPassword());
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    showNhanVienDetailDialog(nhanVien);
//                }
//            });
//
//            lvNhanVienList.addView(itemView);
//        }
//    }

    private void showNhanVienDetailDialog(final NhanVien nhanVien, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông Tin Nhân Viên");

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);

        final EditText etEmail = dialogView.findViewById(R.id.etEmail);
        final EditText etPassword = dialogView.findViewById(R.id.etPassword);
        etEmail.setText(nhanVien.getEmail());
        etPassword.setText(nhanVien.getPassword());

        etEmail.setEnabled(true);
        etPassword.setEnabled(true);

        builder.setPositiveButton("Cập Nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String updatedEmail = etEmail.getText().toString();
                String updatedPassword = etPassword.getText().toString();

                nhanVien.setEmail(updatedEmail);
                nhanVien.setPassword(updatedPassword);

                nvAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void showAddNhanVienDialog() {
        // Tạo một AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm Nhân Viên");

        // Sử dụng LayoutInflater để inflate layout custom_dialog.xml
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);

        final EditText etEmail = dialogView.findViewById(R.id.etEmail);
        final EditText etPassword = dialogView.findViewById(R.id.etPassword);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Lấy giá trị từ EditText và thêm nhân viên mới vào danh sách
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                nhanVienList.add(new NhanVien(email, password));
                nvAdapter.notifyDataSetChanged();
                //updateUI();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}