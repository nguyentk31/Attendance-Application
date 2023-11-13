package com.example.attendanceapplication.Models.ConfigureRelation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.attendanceapplication.Models.ReportModel;
import com.example.attendanceapplication.Models.UserModel;

import java.util.List;

public class UserWithReports {
    @Embedded
    public UserModel user;
    @Relation(
            parentColumn = "MaNhanVien",
            entityColumn = "MaNhanVien"
    )
    public List<ReportModel> reports;
}
