package com.example.attendanceapplication.Fragments;
import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.attendanceapplication.Activities.ListMembersActivity;
import com.example.attendanceapplication.Activities.MainActivity;
import com.example.attendanceapplication.Activities.ResetPasswordActivity;
import com.example.attendanceapplication.Activities.UpdateProfileActivity;
import com.example.attendanceapplication.Model.Employee;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class ProfileFragment extends Fragment {
    private Button btnLogout, btnResetPW, btnChangeProfile;
    private ImageView ivUpdateAvt;
    private User me;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        me = User.getInstance(getContext());

        btnResetPW = view.findViewById(R.id.btnGotoChangePassword);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnChangeProfile = view.findViewById(R.id.btnChangeProfile);
        ivUpdateAvt = view.findViewById(R.id.ivUpdateAvt);

        me.setProfileView(view);
        ivUpdateAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 101);
            }
        });

        btnResetPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ResetPasswordActivity.class));
            }
        });
        // Đăng xuất
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.ResetInstance();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                Toast.makeText(getActivity(), "Logout Successful.", Toast.LENGTH_SHORT).show();
            }
        });

        btnChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (me.getMyProfile() != null) {
                    startActivity(new Intent(getActivity(), UpdateProfileActivity.class));
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        me.resetMyProfileView();
    }
}