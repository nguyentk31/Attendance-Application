package com.example.attendanceapplication.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "VaiTro")
public class RoleModel {
    @PrimaryKey(autoGenerate = true)
    public int MaVaiTro;

    public String TenVaiTro;
}
