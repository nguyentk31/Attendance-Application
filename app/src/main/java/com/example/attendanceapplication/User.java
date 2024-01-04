package com.example.attendanceapplication;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.attendanceapplication.Adapters.EmployeeListAdapter;
import com.example.attendanceapplication.Model.Employee;
import com.example.attendanceapplication.Model.Employee.Status;
import com.example.attendanceapplication.Model.Employee.Position;
import com.example.attendanceapplication.Model.Employee.Gender;
import com.example.attendanceapplication.Model.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.squareup.picasso.Picasso;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private static User instance = null;
    // Current context
    private static Context context = null;
    // Firebase authentication id
    private static String authID;
    private final String TAG = "USER";
    // Firebase rtdb ref
    private static DatabaseReference myDBRef;
    // My profile and staffs' profile
    private Employee myProfile;
    private ArrayList<Employee> myStaffs;
    // Events
    private ArrayList<Event> events;
    // Tags available
    private ArrayList<String> tagids;
    // View of ProfileFragment
    private View profileView;
    // Adapter of ListMembersActivity
    private ArrayAdapter<Employee> staffsAdapter;
    // CalendarView of ScheduleActivity
    private MaterialCalendarView calendarView;
    // Adapter of spinner tagids
    private ArrayAdapter<String> tagidsAdapter;
    // Next uid to use
    private int maxID;
    private User() {
        maxID = 0;

        // Firebase realtime database url
        String dbURL = "https://attendance-application-42e9d-default-rtdb.asia-southeast1.firebasedatabase.app/";
        myDBRef = FirebaseDatabase.getInstance(dbURL).getReference();
        authID = FirebaseAuth.getInstance().getUid();

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    updateFCMtoken(task.getResult());
                }
            }
        });

        myDBRef.child("users/" + authID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myProfile = snapshot.getValue(Employee.class);
                // if it's manager, get staffs' profiles
                if (myProfile.getPosition().equals(Position.manager)) {
                    getStaffs();
                    getTags();
                }
                // if it's in profile fragment, then setProfileView
                if (profileView != null) {
                    setProfileView(null);
                    // if it's in schedule activity, then setCalendarView
                } else if (calendarView != null) {
                    setCalendarView(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, error.getMessage());
            }
        });

        myDBRef.child("calendar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (events == null) events = new ArrayList<>();
                else events.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Event x = snap.getValue(Event.class);
                    if (x != null && !x.getStatus().equals("requesting")) {
                        events.add(x);
                    }
                    if (calendarView != null) {
                        setCalendarView(null);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    private void getStaffs() {
        if (myStaffs != null) return;
        myStaffs = new ArrayList<>();
        myDBRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myStaffs.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Employee x = snap.getValue(Employee.class);
                    int id = Integer.parseInt(x.getId());
                    if (id > maxID) {
                        maxID = id;
                    }
                    Status status = x.getStatus();
                    if (x.getPosition().equals(Position.staff) && !(status.equals(Status.fired) || status.equals(Status.requesting) || status.equals(Status.disabling) || status.equals(Status.recovering) || status.equals(Status.firing))) {
                        myStaffs.add(x);
                    }
                }
                // if it's in listmembers activity, then notify data changed by adapter
                if (staffsAdapter != null) {
                    staffsAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    private void getTags() {
        if (tagids != null) return;
        tagids = new ArrayList<>();
        myDBRef.child("attendances").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tagids.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    tagids.add(snap.getKey());
                }
                if (tagidsAdapter != null) {
                    tagidsAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    public static User getInstance(Context ctx) {
        if(instance == null) {
            instance = new User();
        }
        context = ctx;
        return instance;
    }

    public static void ResetInstance() {
        if(instance != null) {
            updateFCMtoken(null);
            FirebaseMessaging.getInstance().deleteToken();
            instance = null;
        }
    }

    public void makeToast(String msg) {
        if (context != null) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public DatabaseReference getMyDBRef() {
        return myDBRef;
    }

    public Employee getMyProfile() {
        return myProfile;
    }

    public ArrayList<Employee> getMyStaffs() {
        return myStaffs;
    }

    public ArrayAdapter<Employee> getStaffsAdapter() {
        staffsAdapter = new EmployeeListAdapter(context, R.layout.list_item_nhanvien, myStaffs);
        return staffsAdapter;
    }

    public ArrayAdapter<String> getTagidsAdapter() {
        tagidsAdapter = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tagids);
        return tagidsAdapter;
    }


    public void resetStaffsAdapter() {
        staffsAdapter = null;
    }

    public void resetTagidsAdapter() {
        tagidsAdapter = null;
    }

    public void setCalendarView(MaterialCalendarView materialCalendarView) {
        if (materialCalendarView != null) {
            calendarView = materialCalendarView;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (myProfile.getAttendances() != null) {
                ArrayList<CalendarDay> ontimeDay = new ArrayList<>();
                ArrayList<CalendarDay> lateDay = new ArrayList<>();
                for (LocalDate x : myProfile.getAttendances().keySet()) {
                    CalendarDay i = new CalendarDay(x.getYear(), x.getMonthValue() - 1, x.getDayOfMonth());
                    if (myProfile.getAttendances().get(x).isAfter(LocalTime.of(7,30))) {
                        lateDay.add(i);
                    } else {
                        ontimeDay.add(i);
                    }
                }
                EventDecorator eventDecorator1 = new EventDecorator(R.drawable.circle_blue, context, ontimeDay);
                EventDecorator eventDecorator2 = new EventDecorator(R.drawable.circle_red, context, lateDay);
                calendarView.addDecorator(eventDecorator1);
                calendarView.addDecorator(eventDecorator2);
            }
            if (events != null) {
                ArrayList<CalendarDay> meeting = new ArrayList<>();
                ArrayList<CalendarDay> holiday = new ArrayList<>();
                for (Event x : events) {
                    LocalDate y = x.getDate();
                    CalendarDay i = new CalendarDay(y.getYear(), y.getMonthValue() - 1, y.getDayOfMonth());
                    if (x.getType().equals(Event.Type.meeting)) {
                        meeting.add(i);
                    } else {
                        holiday.add(i);
                    }
                }
                EventDecorator eventDecorator3 = new EventDecorator(R.drawable.circle_yellow, context, meeting);
                EventDecorator eventDecorator4 = new EventDecorator(R.drawable.circle_pink, context, holiday);
                calendarView.addDecorator(eventDecorator3);
                calendarView.addDecorator(eventDecorator4);
            }
        }
    }

    public void resetCalendarView() {
        calendarView = null;
    }

    public void setProfileView(View view) {
        if (view != null) {
            profileView = view;
        }
        if (myProfile != null) {
            ImageView ivAvatar;
            TextView tvName, tvID, tvBirthday, tvGender, tvPosition, tvPhone, tvMail;
            ivAvatar = profileView.findViewById(R.id.ivAvatar);
            tvName = profileView.findViewById(R.id.tvName);
            tvID = profileView.findViewById(R.id.tvID);
            tvBirthday = profileView.findViewById(R.id.tvBirthday);
            tvGender = profileView.findViewById(R.id.tvGender);
            tvPosition = profileView.findViewById(R.id.tvPosition);
            tvPhone = profileView.findViewById(R.id.tvPhone);
            tvMail = profileView.findViewById(R.id.tvMail);

            tvName.setText(myProfile.getName());
            tvID.setText("#id:" + myProfile.getId());
            tvBirthday.setText(myProfile.getBirthday());
            tvGender.setText(myProfile.getGender().name());
            tvPosition.setText(myProfile.getPosition().name());
            tvPhone.setText(myProfile.getPhone());
            tvMail.setText(myProfile.getMail());
            if (myProfile.getAvatarURL() == null) {
                if (myProfile.getGender().equals(Gender.male))
                    ivAvatar.setImageResource(R.drawable.avatar_male);
                else
                    ivAvatar.setImageResource(R.drawable.avatar_female);
            } else {
                Picasso.get().load(myProfile.getAvatarURL()).into(ivAvatar);
            }
        }
    }

    public void resetMyProfileView() {
        profileView = null;
    }

    public int getMaxID() {
        return maxID;
    }

    public ArrayList<String> getTagids() {
        return tagids;
    }

    public static void updateFCMtoken(String token) {
        Map<String, Object> fcmObject = new HashMap<>();
        fcmObject.put(authID, token);
        myDBRef.child("fcmtokens").updateChildren(fcmObject);
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
