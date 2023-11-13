package com.example.attendanceapplication.Repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.attendanceapplication.Models.RoleModel;

import java.util.List;

@Dao
public interface RoleDao {
    @Query("SELECT * FROM VaiTro")
    List<RoleModel> getAll();
    @Insert
    void insertEach(RoleModel roleModel);

    @Delete
    void delete(RoleModel roleModel);

}
