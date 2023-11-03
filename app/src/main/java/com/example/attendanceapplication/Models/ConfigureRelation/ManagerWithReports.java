package com.example.attendanceapplication.Models.ConfigureRelation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.attendanceapplication.Models.AcceptReport;
import com.example.attendanceapplication.Models.ReportModel;
import com.example.attendanceapplication.Models.StatusModel;
import com.example.attendanceapplication.Models.UserModel;

import java.util.List;

public class ManagerWithReports {
    @Embedded
    public UserModel manager;
    @Relation(
            parentColumn = "MaQuanLy",
            entityColumn = "MaQuanLy"
    )
    public List<AcceptReport> managerReporters;

    @Embedded
    public ReportModel reporter;
    @Relation(
            parentColumn = "MaBaoCao",
            entityColumn = "MaBaoCao"
    )
    public List<AcceptReport> reporters;

    @Embedded
    public StatusModel status;
    @Relation(
            parentColumn = "MaTrangThai",
            entityColumn = "MaTrangThai"
    )
    public List<AcceptReport> statusReporters;
}
