package com.example.attendanceapplication.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceapplication.Fragments.AttendanceFragment;
import com.example.attendanceapplication.Fragments.HomeFragment;
import com.example.attendanceapplication.Fragments.ProfileFragment;
import com.example.attendanceapplication.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SecondActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    LinearLayout membersLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        frameLayout = findViewById(R.id.fragment);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new HomeFragment()).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home_menu) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new HomeFragment()).commit();
                    return true;
                } else if (item.getItemId() == R.id.attendance_menu) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new AttendanceFragment()).commit();
                    return true;
                } else if (item.getItemId() == R.id.profile_menu) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ProfileFragment()).commit();
                    return true;
                }
                return false;
            }
        });
    }
}
