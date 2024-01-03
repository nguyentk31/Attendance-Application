package com.example.attendanceapplication.Fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.attendanceapplication.Activities.ListMembersActivity;
import com.example.attendanceapplication.Activities.MainActivity;
import com.example.attendanceapplication.Activities.ResetPasswordActivity;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {
    private Button btnLogout, btnResetPW;
    private ImageView ivUpdateAvt;
    private User me;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        me = User.getInstance(getContext());

        btnResetPW = view.findViewById(R.id.btnGotoChangePassword);
        btnLogout = view.findViewById(R.id.btnLogout);
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

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101 && resultCode==RESULT_OK && data!=null && data.getData()!=null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("avatars/" + me.getMyProfile().getAuthid());
            UploadTask uploadTask = storageRef.putFile(data.getData());
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    // Continue with the task to get the download URL
                    return storageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Map<String, Object> avtUpdate = new HashMap<>();
                        avtUpdate.put("/avatarURL", task.getResult().toString());
                        me.getMyDBRef().child("users/"+me.getMyProfile().getAuthid()).updateChildren(avtUpdate);
                        me.makeToast("Update avatar successful.");
                    } else {
                        me.makeToast("Update avatar failed.");
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        me.resetMyProfileView();
    }
}