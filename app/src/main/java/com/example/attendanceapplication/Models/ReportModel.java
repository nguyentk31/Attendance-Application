package com.example.attendanceapplication.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "BaoCao" , foreignKeys = {
        @ForeignKey(entity = UserModel.class, parentColumns = "MaNhanVien", childColumns = "MaNhanVien")
})
public class ReportModel {
    @PrimaryKey(autoGenerate = true)
    public int MaBaoCao;

    public String TieuDeBaoCao;
    public String NoiDungBaoCao;

    public int MaNhanVien;
}
