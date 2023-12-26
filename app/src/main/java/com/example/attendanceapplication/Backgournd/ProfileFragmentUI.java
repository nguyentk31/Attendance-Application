package com.example.attendanceapplication.Backgournd;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.attendanceapplication.Model.Employee;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.User;
import com.squareup.picasso.Picasso;

public class ProfileFragmentUI extends AsyncTask<String, Employee, Void> {

    private static final String TAG = "ProfileUI";
    private User user;
    private Employee preProfile;
    private Context context;
    private ImageView ivAvatar;
    private TextView tvName;
    private TextView tvID;
    private TextView tvDepartment;
    private TextView tvPosition;

    public ProfileFragmentUI(Context context, View view) {
        this.user = User.getInstance();
        this.preProfile = user.getMyProfile();
        this.context = context;

        this.ivAvatar = view.findViewById(R.id.ivAvatar);
        this.tvName = view.findViewById(R.id.tvName);
        this.tvID = view.findViewById(R.id.tvID);
        this.tvPosition = view.findViewById(R.id.tvPosition);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (preProfile != null) {
            Picasso.get().load(preProfile.getAvatarURL()).into(ivAvatar);
            tvName.setText(preProfile.getName());
            tvID.setText("#id:" + preProfile.getID());
            tvPosition.setText(preProfile.getPosition());
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            while (true) {
                Employee x = user.getMyProfile();
                if (x != preProfile) {
                    preProfile = x;
                    publishProgress(x);
                }
                Thread.sleep(100);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Employee... values) {
        super.onProgressUpdate(values);

        tvName.setText(values[0].getName());
        tvID.setText("#id:" + values[0].getID());
        tvPosition.setText(values[0].getPosition());
        Picasso.get().load(values[0].getAvatarURL()).into(ivAvatar);
    }

}
