package com.example.attendanceapplication.Models.ConfigureRelation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.attendanceapplication.Models.RoleModel;
import com.example.attendanceapplication.Models.UserModel;

import java.util.List;

public class RoleWithUsers {
    @Embedded
    public RoleModel role;
    @Relation(
            parentColumn = "MaVaiTro",
            entityColumn = "MaVaiTro"
    )
    public List<UserModel> users;
}
