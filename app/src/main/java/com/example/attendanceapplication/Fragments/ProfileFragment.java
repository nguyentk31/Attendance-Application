package com.example.attendanceapplication.Fragments;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.attendanceapplication.Activities.MainActivity;
import com.example.attendanceapplication.Model.Employee;
import com.example.attendanceapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {


    private ImageView ivAvatar;
    private TextView tvName;
    private TextView tvID;
    private TextView tvDepartment;
    private TextView tvPosition;
    private Button btnLogout;

    private Employee e;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ivAvatar = view.findViewById(R.id.ivAvatar);
        tvName = view.findViewById(R.id.tvName);
        tvID = view.findViewById(R.id.tvID);
        tvDepartment = view.findViewById(R.id.tvDepartment);
        tvPosition = view.findViewById(R.id.tvPosition);
        btnLogout = view.findViewById(R.id.btnLogout);

        DatabaseReference profileRef = FirebaseDatabase.getInstance("https://attendance-application-42e9d-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Users/" + FirebaseAuth.getInstance().getUid());
        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Employee myProfile = snapshot.getValue(Employee.class);
                e = new Employee(myProfile);

                Picasso.get().load(myProfile.getAvatarURL()).into(ivAvatar);
                tvName.setText(myProfile.getName());
                tvID.setText("#id:" + myProfile.getID());
                tvDepartment.setText(myProfile.getDepartment());
                tvPosition.setText(myProfile.getPosition());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        int x  = 0;



        // Đăng xuất
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                Toast.makeText(getActivity(), "Logout Successful.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}