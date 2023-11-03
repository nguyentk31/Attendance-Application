package com.example.attendanceapplication.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TrangThai")
public class StatusModel {
    @PrimaryKey(autoGenerate = true)
    public int MaTrangThai;
    public String TenTrangThai;
}
