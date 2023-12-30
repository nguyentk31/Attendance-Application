package com.example.attendanceapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.attendanceapplication.Adapters.EmployeeListAdapter;
import com.example.attendanceapplication.Model.Employee;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class User {

    private static User instance = null;
    private static DatabaseReference myDBRef;
    private static final String TAG = "MYDB";

    private static Context context;
    private static String myUid;
    private static Employee myProfile = null;
    private static View myProfileView = null;
    private static ArrayList<Employee> mySubordinates = null;
    private static ArrayAdapter<Employee> mySubordinatesAdapter = null;
    private static MaterialCalendarView calendarView;
    private User() {
        String dbURL = "https://attendance-application-42e9d-default-rtdb.asia-southeast1.firebasedatabase.app/";
        this.myUid = FirebaseAuth.getInstance().getUid();

        myDBRef = FirebaseDatabase.getInstance(dbURL).getReference();

        myDBRef.child("Users/" + this.myUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myProfile = snapshot.getValue(Employee.class);
                if (myProfile.getPosition() == Employee.Position.Manager)
                    setMySubordinates();

                if (myProfileView != null) {
                    ImageView ivAvatar = myProfileView.findViewById(R.id.ivAvatar);
                    TextView tvName = myProfileView.findViewById(R.id.tvName);
                    TextView tvID = myProfileView.findViewById(R.id.tvID);
                    TextView tvPosition = myProfileView.findViewById(R.id.tvPosition);

                    tvName.setText(myProfile.getName());
                    tvID.setText("#id:" + myProfile.getId());
                    tvPosition.setText(myProfile.getPosition().name());
                    if (myProfile.getAvatarURL().equals("null")) {
                        if (myProfile.getGender() == Employee.Gender.Male)
                            ivAvatar.setImageResource(R.drawable.avatar_male);
                        else
                            ivAvatar.setImageResource(R.drawable.avatar_female);
                    } else {
                        Picasso.get().load(myProfile.getAvatarURL()).into(ivAvatar);
                    }
                } else if (calendarView != null) {
                    ArrayList<CalendarDay> highlightedDays = new ArrayList<>();
                    for (LocalDate x : myProfile.getAttendances().keySet()) {
                        CalendarDay i = new CalendarDay(x.getYear(), x.getMonthValue()-1, x.getDayOfMonth());
                        highlightedDays.add(i);
                    }
                    EventDecorator eventDecorator = new EventDecorator(Color.RED, context, highlightedDays);
                    calendarView.addDecorator(eventDecorator);
                }
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

    public void setCalendarView(Context context, MaterialCalendarView materialCalendarView) {
        this.context = context;
        calendarView = materialCalendarView;
        ArrayList<CalendarDay> highlightedDays = new ArrayList<>();
        for (LocalDate x : myProfile.getAttendances().keySet()) {
            CalendarDay i = new CalendarDay(x.getYear(), x.getMonthValue()-1, x.getDayOfMonth());
            highlightedDays.add(i);
        }
        EventDecorator eventDecorator = new EventDecorator(Color.RED, this.context, highlightedDays);
        calendarView.addDecorator(eventDecorator);
    }

    public void resetCalendarView() {
        this.context = null;
        calendarView = null;
    }

    public void setMyProfileView(View view) {
        this.myProfileView = view;
        if (myProfile != null) {
            ImageView ivAvatar = view.findViewById(R.id.ivAvatar);
            TextView tvName = view.findViewById(R.id.tvName);
            TextView tvID = view.findViewById(R.id.tvID);
            TextView tvPosition = view.findViewById(R.id.tvPosition);

            tvName.setText(myProfile.getName());
            tvID.setText("#id:" + myProfile.getId());
            tvPosition.setText(myProfile.getPosition().name());
            if (myProfile.getAvatarURL().equals("null")) {
                if (myProfile.getGender() == Employee.Gender.Male)
                    ivAvatar.setImageResource(R.drawable.avatar_male);
                else
                    ivAvatar.setImageResource(R.drawable.avatar_female);
            } else {
                Picasso.get().load(myProfile.getAvatarURL()).into(ivAvatar);
            }
        }
    }

    public void resetMyProfileView() {
        this.myProfileView = null;
    }
}
