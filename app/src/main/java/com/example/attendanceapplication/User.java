package com.example.attendanceapplication;

import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.attendanceapplication.Model.Employee;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class User {

    private static User instance = null;
    private static String dbURL;
    private static final String TAG = "MYDB";

    private static final String Manager = "Manager";
    private static String myUid;
    private static Employee myProfile = null;
    private static ArrayList<Employee> mySubordinates = null;

    private User() {
        dbURL = "https://attendance-application-42e9d-default-rtdb.asia-southeast1.firebasedatabase.app/";
        this.myUid = FirebaseAuth.getInstance().getUid();

        DatabaseReference myDBRef = FirebaseDatabase.getInstance(dbURL).getReference();

        myDBRef.child("Users/" + this.myUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (myProfile == null) myProfile = new Employee();
                Employee x = snapshot.getValue(Employee.class);
                myProfile.setId(x.getID());
                myProfile.setName(x.getName());
                myProfile.setPosition(x.getPosition());
                myProfile.setBaseSalary(x.getBaseSalary());
                myProfile.setAvatarURL(x.getAvatarURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, error.getMessage());
            }
        });

        myDBRef.child("Attendances/" + this.myUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (myProfile == null) myProfile = new Employee();
                ArrayList<LocalDateTime> attendances = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        attendances.add(LocalDateTime.parse(snap.getKey() + 'T' + snap.getValue()));
                    }
                }
                myProfile.setAttendances(attendances);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    public static User getInstance() {
        if(instance == null) {
            instance = new User();
        }
        return instance;
    }

    public static void ResetInstance() {
        if(instance != null) {
            instance = null;
        }
    }

    public Employee getMyProfile() {
        return myProfile;
    }
}
