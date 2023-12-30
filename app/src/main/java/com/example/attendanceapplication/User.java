package com.example.attendanceapplication;

import android.content.Context;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.attendanceapplication.Adapters.EmployeeListAdapter;
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
    private static DatabaseReference myDBRef;
    private static final String TAG = "MYDB";

    private static String myUid;
    private static Employee myProfile = null;
    private static ArrayList<Employee> mySubordinates = null;
    private static ArrayAdapter<Employee> mySubordinatesAdapter = null;

    private User() {
        String dbURL = "https://attendance-application-42e9d-default-rtdb.asia-southeast1.firebasedatabase.app/";
        this.myUid = FirebaseAuth.getInstance().getUid();

        myDBRef = FirebaseDatabase.getInstance(dbURL).getReference();

        myDBRef.child("Users/" + this.myUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (myProfile == null) myProfile = new Employee();
                myProfile = snapshot.getValue(Employee.class);
                if (myProfile.getPosition() == Employee.Position.Manager)
                    setMySubordinates();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    private void setMySubordinates() {
        if (mySubordinates != null) return;

        mySubordinates = new ArrayList<>();
        myDBRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mySubordinates.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Employee x = snap.getValue(Employee.class);
                    if (x.getPosition() == Employee.Position.Staff && x.getStatus() == Employee.Status.Working)
                        mySubordinates.add(x);
                }
                if (mySubordinatesAdapter != null)
                    mySubordinatesAdapter.notifyDataSetChanged();
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

    public ArrayList<Employee> getMySubordinates() {
        return mySubordinates;
    }

//    public void setMySubordinatesAdapter(Context context) {
//        mySubordinatesAdapter = new EmployeeListAdapter(context, R.layout.list_item_nhanvien, mySubordinates);
//    }

    public ArrayAdapter<Employee> getMySubordinatesAdapter(Context context) {
        mySubordinatesAdapter = new EmployeeListAdapter(context, R.layout.list_item_nhanvien, mySubordinates);
        return mySubordinatesAdapter;
    }

    public void resetMySubordinatesAdapter() {
        mySubordinatesAdapter = null;
    }

    public DatabaseReference getMyDBRef() {
        return myDBRef;
    }
}
