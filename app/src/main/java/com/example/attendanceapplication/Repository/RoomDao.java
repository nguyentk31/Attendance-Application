package com.example.attendanceapplication.Repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.attendanceapplication.Models.RoomModel;
import com.example.attendanceapplication.Models.UserModel;

import java.util.List;

@Dao
public interface RoomDao {
    @Query("SELECT * FROM PhongBan")
    List<RoomModel> getAll();
    @Insert
    void insertEach(RoomModel roomModel);

    @Delete
    void delete(RoomModel roomModel);


    @Query("SELECT * FROM NhanVien WHERE MaPhongBan = :mapb")
    List<UserModel> users(int mapb);
}
