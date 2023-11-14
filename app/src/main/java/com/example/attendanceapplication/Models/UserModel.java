package com.example.attendanceapplication.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "NhanVien", foreignKeys = {
        @ForeignKey(entity = RoleModel.class, parentColumns = "MaVaiTro", childColumns = "MaVaiTro"),
        @ForeignKey(entity = RoomModel.class, parentColumns = "MaPhongBan", childColumns = "MaPhongBan")
})
public class UserModel {
    @PrimaryKey(autoGenerate = true)
    public int MaNhanVien;
    public String MatKhau;
    @NonNull
    public String TenNhanVien;
    public boolean GioiTinh;
    public int MaVaiTro;
    public int MaPhongBan;
}
