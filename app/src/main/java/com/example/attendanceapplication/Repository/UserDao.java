package com.example.attendanceapplication.Repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.attendanceapplication.Models.RoomModel;
import com.example.attendanceapplication.Models.UserModel;

import java.util.List;
@Dao
public interface UserDao {
    @Query("SELECT * FROM NhanVien")
    List<UserModel> getAll();

    @Query("SELECT * FROM NhanVien WHERE MaNhanVien = :Mnv AND MatKhau = :Mk")
    UserModel Login(int Mnv , String Mk);
    @Insert
    void insertEach(UserModel userModel);

    @Delete
    void delete(UserModel user);

    @Query("SELECT * FROM PhongBan WHERE MaPhongBan = :mapb")
    RoomModel GetRoom(int mapb);
}