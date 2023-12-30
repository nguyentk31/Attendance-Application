package com.example.attendanceapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.attendanceapplication.Activities.ScheduleActivity;
import com.example.attendanceapplication.Backgournd.HomeFragmentUI;
import com.example.attendanceapplication.Activities.DashboardActivity;
import com.example.attendanceapplication.Activities.ListMembersActivity;
import com.example.attendanceapplication.R;

public class HomeFragment extends Fragment {
    private LinearLayout ft_schedule, ft_dashboard, ft_members;
    private HomeFragmentUI update;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflating the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ft_schedule = view.findViewById(R.id.ft_schedule);
        ft_dashboard = view.findViewById(R.id.ft_dashboard);
        ft_members = view.findViewById(R.id.ft_members);

        update = new HomeFragmentUI(getContext(), view);
        update.execute();

        ft_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                startActivity(intent);
            }
        });
        ft_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListMembersActivity.class);
                startActivity(intent);
            }
        });
        ft_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DashboardActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}