package com.example.attendanceapplication;

import androidx.annotation.NonNull;
import com.example.attendanceapplication.Model.Employee;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfile {

    private static MyProfile instance = null;
    private Employee myProfile;

    private MyProfile() {
        DatabaseReference profileRef = FirebaseDatabase.getInstance("https://attendance-application-42e9d-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Users/" + FirebaseAuth.getInstance().getUid());
        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Employee x = snapshot.getValue(Employee.class);
//                myProfile = new Employee(x);
//                int y = 0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static MyProfile getInstance() {
        if(instance == null) {
            instance = new MyProfile();
        }
        return instance;
    }

    public Employee getInfo() {
        return myProfile;
    }

}
