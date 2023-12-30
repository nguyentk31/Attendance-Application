package com.example.attendanceapplication.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private User me;

    private EditText etOldPW, etNewPW, etConfirmNewPW;
    private Button btnReset;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();
        me = User.getInstance();

        etOldPW = findViewById(R.id.etOldPW);
        etNewPW = findViewById(R.id.etNewPW);
        etConfirmNewPW = findViewById(R.id.etConfirmPW);
        btnReset = findViewById(R.id.btnReset);
        progressBar = findViewById(R.id.progressBar);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPW = etOldPW.getText().toString().trim();
                String newPW = etNewPW.getText().toString().trim();
                String confirmPW = etConfirmNewPW.getText().toString().trim();

                if (oldPW.equals("")) {
                    etOldPW.setError("Field is required!");
                    etOldPW.requestFocus();
                } else if (newPW.equals("")) {
                    etNewPW.setError("Field is required!");
                    etNewPW.requestFocus();
                } else if (newPW.length() < 6) {
                    etNewPW.setError("Password must be at least 6 characters!");
                    etNewPW.requestFocus();
                } else if (!confirmPW.equals(newPW)) {
                    etConfirmNewPW.setError("Password confirmation is incorrect!");
                    etConfirmNewPW.requestFocus();
                } else {
                    btnReset.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                    Reseting(oldPW, newPW);
                }
            }
        });
    }

    private void Reseting(String oldPW, String newPW) {
        AuthCredential credential = EmailAuthProvider
                .getCredential(mAuth.getCurrentUser().getEmail(), oldPW);

        mAuth.getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mAuth.getCurrentUser().updatePassword(newPW).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ResetPasswordActivity.this, "Updated password successfully!", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(ResetPasswordActivity.this, "Password update failed!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, "Old password is not correct!", Toast.LENGTH_LONG).show();
                        }
                        btnReset.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}