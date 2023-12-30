package com.example.attendanceapplication.Backgournd;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.attendanceapplication.Model.Employee;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.User;

public class HomeFragmentUI extends AsyncTask<String, Employee.Position, Void> {

    private static final String TAG = "HomeUI";
    private User user;
    private Context context;
    private LinearLayout ft_schedule, ft_dashboard, ft_members;

    public HomeFragmentUI(Context context, View view) {
        this.user = User.getInstance();
        this.context = context;

        this.ft_schedule = view.findViewById(R.id.ft_schedule);
        this.ft_dashboard = view.findViewById(R.id.ft_dashboard);
        this.ft_members = view.findViewById(R.id.ft_members);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (this.ft_schedule.getVisibility() == View.VISIBLE) {
            cancel(true);
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            while (user.getMyProfile() == null) {
                Thread.sleep(100);
            }
            publishProgress(user.getMyProfile().getPosition());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Employee.Position... values) {
        super.onProgressUpdate(values);

        this.ft_schedule.setVisibility(View.VISIBLE);
        if (values[0] == Employee.Position.Manager) {
            this.ft_dashboard.setVisibility(View.VISIBLE);
            this.ft_members.setVisibility(View.VISIBLE);
        }
    }

}
