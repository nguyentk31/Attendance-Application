package com.example.attendanceapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.attendanceapplication.Dashboard;
import com.example.attendanceapplication.ListMembers;
import com.example.attendanceapplication.R;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflating the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView gotoListMembers = view.findViewById(R.id.goToListMember);
        TextView goToDashboard = view.findViewById(R.id.goToDashboard);
        gotoListMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListMembers.class);
                startActivity(intent);
            }
        });
        goToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Dashboard.class);
                startActivity(intent);
            }
        });
        return view;
    }
}