package com.example.attendanceapplication.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PhongBan")
public class RoomModel {
    @PrimaryKey(autoGenerate = true)
    public int MaPhongBan;

    public String TenPhongBan;
}
