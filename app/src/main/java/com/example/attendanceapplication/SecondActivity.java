package com.example.attendanceapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.appbar.MaterialToolbar;
import com.example.attendanceapplication.Fragments.AttendanceFragment;
import com.example.attendanceapplication.Fragments.HomeFragment;
import com.example.attendanceapplication.Fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationBarView;

public class SecondActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        frameLayout = findViewById(R.id.fragment);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        toolbar = findViewById(R.id.toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new AttendanceFragment()).commit();
        toolbar.setTitle(getString(R.string.title_home));
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home_menu) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new HomeFragment()).commit();
                    toolbar.setTitle(getString(R.string.title_home));
                    return true;
                } else if (item.getItemId() == R.id.attendance_menu) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new AttendanceFragment()).commit();
                    toolbar.setTitle(getString(R.string.title_attendance));
                    return true;
                } else if (item.getItemId() == R.id.profile_menu) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ProfileFragment()).commit();
                    toolbar.setTitle(getString(R.string.title_profile));
                    return true;
                }
                return false;
            }
        });
    }
}
