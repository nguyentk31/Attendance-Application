package com.example.attendanceapplication.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "ChamCong")
public class TimeKeepingModel {
    @PrimaryKey(autoGenerate = true)
    public int MaChamCong;

    public Date ThoiGianVao;
    public Date ThoiGianRa;
    public Date ThoiGianNghi;
    public Double SoGioLam;
}
