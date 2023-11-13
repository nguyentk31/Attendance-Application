package com.example.attendanceapplication.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "DuyetBaoCao" ,primaryKeys = {"MaBaoCao" , "MaQuanLy" , "MaTrangThai"},
        foreignKeys = {
        @ForeignKey(entity = ReportModel.class, parentColumns = "MaBaoCao", childColumns = "MaBaoCao"),
        @ForeignKey(entity = ManagerModel.class, parentColumns = "MaQuanLy", childColumns = "MaQuanLy"),
        @ForeignKey(entity = StatusModel.class, parentColumns = "MaTrangThai", childColumns = "MaTrangThai")
})
public class AcceptReport {
    public int MaBaoCao;
    public int MaQuanLy;
    public int MaTrangThai;
}
