package com.example.attendanceapplication.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "QuanLy" , foreignKeys = {
        @ForeignKey(entity = RoleModel.class, parentColumns = "MaVaiTro", childColumns = "MaVaiTro")
})
public class ManagerModel {
    @PrimaryKey(autoGenerate = true)
    public int MaQuanLy;
    public String TenQuanLy;
    public int MaVaiTro;
}
